package com.jarvlis.jarojbackendjudgeservice.judge.strategy;


import com.jarvlis.jarojbackendmodel.codesandbox.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    JudgeInfo doJudge(JudgeContext judgeContext);
}
