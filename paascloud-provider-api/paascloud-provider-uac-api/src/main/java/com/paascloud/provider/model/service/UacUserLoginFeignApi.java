/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserLoginController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service;

import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.dto.user.LogoutDto;
import com.paascloud.provider.model.service.hystrix.UacUserLoginFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录相关.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacUserLoginFeignHystrix.class)
public interface UacUserLoginFeignApi {


    /**
     * 登录成功获取菜单信息和用户信息.
     *
     * @param applicationId the application id
     * @param loginName     the login name
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/loginAfter/{applicationId}/{loginName}")
    @ApiOperation(httpMethod = "POST", value = "登录成功获取用户菜单")
    Wrapper<LoginRespDto> loginAfter(@PathVariable("applicationId") Long applicationId, @PathVariable("loginName") String loginName);

    /**
     * 登出.
     *
     * @param logoutDto the logout dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/logout")
    @ApiOperation(httpMethod = "POST", value = "登出")
    Wrapper loginAfter(@RequestBody LogoutDto logoutDto);

    /**
     * 刷新token.
     *
     * @param refreshToken the refresh token
     * @param accessToken  the access token
     * @param header       the header
     * @param remoteAddr   the remote addr
     * @param os           the os
     * @param browser      the browser
     * @return the wrapper
     */
    @PostMapping(value = "/uac/auth/user/refreshToken")
    @ApiOperation(httpMethod = "POST", value = "刷新token")
    Wrapper<String> refreshToken(@RequestParam(value = "refreshToken") String refreshToken,
                                 @RequestParam(value = "accessToken") String accessToken,
                                 @RequestParam(value = "header") String header,
                                 @RequestParam(value = "remoteAddr") String remoteAddr,
                                 @RequestParam(value = "os") String os,
                                 @RequestParam(value = "browser") String browser);

}