package com.jarvlis.jarojbackendjudgeservice.controller.inner;


import com.jarvlis.jarojbackendjudgeservice.judge.JudgeService;
import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import com.jarvlis.jarojbackendserviceclient.service.JudgeFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该服务用于内部调用, 不提供给前端
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题服务
     * @param questionSubmitId 题目ID
     * @return
     */
    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}
