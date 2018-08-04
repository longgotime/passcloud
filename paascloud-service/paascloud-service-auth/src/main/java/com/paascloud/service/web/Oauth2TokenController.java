/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：OauthController.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.web;

import com.google.common.base.Preconditions;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.service.UacUserLoginFeignApi;
import com.paascloud.wrapper.Wrapper;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The type Oauth 2 token controller.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@RestController
@Api(value = "WEB - OauthController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class Oauth2TokenController {

    private static final String BEARER_TOKEN_TYPE = "Basic ";

    @Resource
    private UacUserLoginFeignApi uacUserLoginFeignApi;
    @Resource
    private ClientDetailsService clientDetailsService;

    /**
     * 刷新token.
     *
     * @param request      the request
     * @param refreshToken the refresh token
     * @param accessToken  the access token
     * @return the wrapper
     * @throws IOException the io exception
     */
    @GetMapping(value = "/auth/user/refreshToken")
    @ApiOperation(httpMethod = "POST", value = "刷新token")
    public Wrapper<String> refreshToken(HttpServletRequest request, String refreshToken, String accessToken) throws IOException {

        Preconditions.checkArgument(StringUtils.isNotEmpty(accessToken), "accessToken is null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(refreshToken), "refreshToken is null");
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
