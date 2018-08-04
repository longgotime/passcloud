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

import com.google.common.base.Preconditions;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.service.UacUserLoginFeignApi;
import com.paascloud.security.core.SecurityResult;
import com.paascloud.wrapper.Wrapper;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 生成校验码的请求处理器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@RestController
@Api(value = "API - TpcMqMessageFeignClient")
public class ValidateCodeController {

    private static final String BEARER_TOKEN_TYPE = "Basic ";

    @Resource
    private UacUserLoginFeignApi uacUserLoginFeignApi;
    @Resource
    private ClientDetailsService clientDetailsService;

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

    /**
     * 刷新token.
     *
     * @param request      the request
     * @param refreshToken the refresh token
     * @param accessToken  the access token
     * @return the wrapper
     */
    @GetMapping(value = "/auth/user/refreshToken")
    @ApiOperation(httpMethod = "POST", value = "刷新token")
    public Wrapper<String> refreshToken(HttpServletRequest request, String refreshToken, String accessToken) throws IOException {

        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(accessToken), "accessToken is null");
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(refreshToken), "refreshToken is null");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }
        String[] tokens = RequestUtil.extractAndDecodeHeader(header);
        assert tokens.length == 2;

        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        } else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        final String remoteAddr = RequestUtil.getRemoteAddr(request);

        //获取客户端操作系统
        final String os = userAgent.getOperatingSystem().getName();
        //获取客户端浏览器
        final String browser = userAgent.getBrowser().getName();

        return uacUserLoginFeignApi.refreshToken(refreshToken, accessToken, header, remoteAddr, os, browser);
    }
}
