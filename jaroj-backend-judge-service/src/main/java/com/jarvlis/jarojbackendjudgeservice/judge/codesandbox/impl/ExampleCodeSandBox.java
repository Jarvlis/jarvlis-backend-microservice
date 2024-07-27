package com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl;


import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.jarvlis.jarojbackendmodel.codesandbox.JudgeInfo;
import com.jarvlis.jarojbackendmodel.enums.JudgeInfoMessageEnum;
import com.jarvlis.jarojbackendmodel.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:14:22
 */

/**
 *  实例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest) {
        List<String> inputLists = executeRequest.getInputLists();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputLists(inputLists);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
