package com.jarvlis.jarojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.jarvlis.jarojbackendcommon.common.ErrorCode;
import com.jarvlis.jarojbackendcommon.exception.BusinessException;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.jarvlis.jarojbackendjudgeservice.judge.strategy.JudgeContext;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.jarvlis.jarojbackendmodel.codesandbox.JudgeInfo;
import com.jarvlis.jarojbackendmodel.dto.question.JudgeCase;
import com.jarvlis.jarojbackendmodel.entity.Question;
import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import com.jarvlis.jarojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.jarvlis.jarojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:16:13
 */
@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionFeignClient questionFeignClient;

    @Resource
    private JudgeManager judgeManager;


    @Value("${codesandbox.type}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 2) 如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3) 更改题目的提交状态为正在判题中，避免重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean updateRes = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!updateRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4) 调用代码沙箱
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        codeSandBox = new CodeSandboxProxy(codeSandBox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputLists(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputLists();
        // 5) 根据沙箱的执行情况，去设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);

        // (6) 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        if (executeCodeResponse.getStatus() != 2) {
            questionSubmitUpdate.setStatus(executeCodeResponse.getStatus());
        } else {
            questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        }
        updateRes = questionFeignClient.updateQuestionSubmitById(questionSubmitUpdate);
        if (!updateRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        return questionFeignClient.getQuestionSubmitById(questionSubmitId);
    }
}
