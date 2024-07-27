package com.jarvlis.jarojbackendjudgeservice.judge.codesandbox;


import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * Author:Jarvlis
 * Date:2023-12-26
 * Time:17:27
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * @param executeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeRequest);
}
