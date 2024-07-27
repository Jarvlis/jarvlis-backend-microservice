package com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl;

import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用网上现成的代码沙箱）
 */
public class ThirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
