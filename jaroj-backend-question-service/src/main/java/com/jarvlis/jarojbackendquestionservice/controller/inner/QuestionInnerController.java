package com.jarvlis.jarojbackendquestionservice.controller.inner;


import com.jarvlis.jarojbackendmodel.entity.Question;
import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import com.jarvlis.jarojbackendquestionservice.service.QuestionService;
import com.jarvlis.jarojbackendquestionservice.service.QuestionSubmitService;
import com.jarvlis.jarojbackendserviceclient.service.QuestionFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该服务用于内部调用, 不提供给前端
 */
@RestController
@RequestMapping("/inner")
@Slf4j
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public Boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }
}
