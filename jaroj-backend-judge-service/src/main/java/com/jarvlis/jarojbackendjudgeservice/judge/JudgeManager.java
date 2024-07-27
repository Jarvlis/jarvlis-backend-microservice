package com.jarvlis.jarojbackendjudgeservice.judge;


import com.jarvlis.jarojbackendjudgeservice.judge.strategy.DefaultJudgeStrategyImpl;
import com.jarvlis.jarojbackendjudgeservice.judge.strategy.JavaJudgeStrategyImpl;

import com.jarvlis.jarojbackendjudgeservice.judge.strategy.JudgeContext;
import com.jarvlis.jarojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.jarvlis.jarojbackendmodel.codesandbox.JudgeInfo;
import com.jarvlis.jarojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:21:33
 */
@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategyImpl();
        if ("java".equals(language)) {
            judgeStrategy = new JavaJudgeStrategyImpl();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
