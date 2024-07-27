package com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;

import com.jarvlis.jarojbackendcommon.common.ErrorCode;
import com.jarvlis.jarojbackendcommon.exception.BusinessException;
import com.jarvlis.jarojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.jarvlis.jarojbackendmodel.codesandbox.ExecuteCodeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Author:Jarvlis
 * Date:2024-01-11
 * Time:14:22
 */

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
@Service
public class RemoteCodeSandBox implements CodeSandBox {

    @Value("${codesandbox.address}")
    private String address;

    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_KEY = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost("http://localhost:8090/executeCode")
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_KEY)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "远程沙箱错误, 错误信息: "+ responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
