package com.jarvlis.jarojbackendserviceclient.service;


import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:16:11
 */
@FeignClient(name = "jaroj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题服务
     * @param questionSubmitId 题目ID
     * @return
     */
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
