package com.jarvlis.jarojbackendjudgeservice.judge;


import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:16:11
 */
public interface JudgeService {
    /**
     * 判题服务
     * @param questionSubmitId 题目ID
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
