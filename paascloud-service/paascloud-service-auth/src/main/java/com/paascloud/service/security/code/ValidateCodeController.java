/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：ValidateCodeController.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code;

import com.paascloud.security.core.SecurityResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 生成校验码的请求处理器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@RestController
@Api(value = "WEB - ValidateCodeController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ValidateCodeController {

    @Resource
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request  the request
     * @param response the response
     * @param type     the type
     * @throws Exception the exception
     */
    @PostMapping("/auth/code/{type}")
    @ApiOperation(httpMethod = "POST", value = "创建验证码")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }

    /**
     * 校验验证码.
     *
     * @param request  the request
     * @param response the response
     * @param type     the type
     * @return the object
     */
    @GetMapping("/auth/code/{type}")
    @ApiOperation(httpMethod = "GET", value = "校验验证码")
    public Object checkCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) {
        SecurityResult result = new SecurityResult(SecurityResult.SUCCESS_CODE, "校验成功", true);
        try {
            validateCodeProcessorHolder.findValidateCodeProcessor(type).check(new ServletWebRequest(request, response));
        } catch (ValidateCodeException e) {
            result = SecurityResult.error(e.getMessage(), false);
        } catch (Exception e) {
            log.error("getAccessToken={}", e.getMessage(), e);
            result = SecurityResult.error("验证码错误", false);
        }
        return result;
    }
}
