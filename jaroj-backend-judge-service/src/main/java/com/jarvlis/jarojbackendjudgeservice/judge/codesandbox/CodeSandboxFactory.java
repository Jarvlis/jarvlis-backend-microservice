package com.jarvlis.jarojbackendjudgeservice.judge.codesandbox;


import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl.ExampleCodeSandBox;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl.RemoteCodeSandBox;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandBox;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:14:42
 */
public class CodeSandboxFactory {

    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandBox();
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdparty":
                return new ThirdPartyCodeSandBox();
            default:
                return new ExampleCodeSandBox();
        }
    }
}
