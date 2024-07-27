package com.jarvlis.jarojbackendserviceclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jarvlis.jarojbackendmodel.dto.question.QuestionQueryRequest;
import com.jarvlis.jarojbackendmodel.entity.Question;
import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import com.jarvlis.jarojbackendmodel.vo.QuestionVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
* @author 十肆
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-10-03 10:39:45
*/
@FeignClient(name = "jaroj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {

    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    public Boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);
}
