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

package com.paascloud.provider.web;

import com.google.common.base.Preconditions;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UserTokenDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.dto.user.LogoutDto;
import com.paascloud.provider.model.enums.UacUserTokenStatusEnum;
import com.paascloud.provider.model.service.UacUserLoginFeignApi;
import com.paascloud.provider.service.UacLoginService;
import com.paascloud.provider.service.UacUserTokenService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 登录相关.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacUserLoginFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserLoginFeignClient extends BaseFeignClient implements UacUserLoginFeignApi {
    @Resource
    private UacLoginService uacLoginService;

    @Resource
    private UacUserTokenService uacUserTokenService;

    private static final String BEARER_TOKEN_TYPE = "Basic ";

    @Override
    public Wrapper<LoginRespDto> loginAfter(@PathVariable("applicationId") Long applicationId, @PathVariable("loginName") String loginName) {

        logger.info("登录成功获取用户菜单. applicationId={}, loginName={}", applicationId, loginName);
        LoginRespDto result = uacLoginService.loginAfter(applicationId, loginName);
        return WrapMapper.ok(result);
    }

    @Override
    public Wrapper loginAfter(@RequestBody LogoutDto logoutDto) {

        String accessToken = logoutDto.getAccessToken();
        LoginAuthDto loginAuthDto = logoutDto.getLoginAuthDto();

        if (!StringUtils.isEmpty(accessToken)) {
            // 修改用户在线状态
            UserTokenDto userTokenDto = uacUserTokenService.getByAccessToken(accessToken);
            userTokenDto.setStatus(UacUserTokenStatusEnum.OFF_LINE.getStatus());
			uacUserTokenService.updateUacUserToken(userTokenDto, loginAuthDto);
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<String> refreshToken(@RequestParam(value = "refreshToken") String refreshToken,
                                        @RequestParam(value = "accessToken") String accessToken,
                                        @RequestParam(value = "header") String header,
                                        @RequestParam(value = "remoteAddr") String remoteAddr,
                                        @RequestParam(value = "os") String os,
                                        @RequestParam(value = "browser") String browser) {
        String token;
        try {
            Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(accessToken), "accessToken is null");
            Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotEmpty(refreshToken), "refreshToken is null");

            token = uacUserTokenService.refreshToken(accessToken, refreshToken, header, remoteAddr, os, browser);
        } catch (Exception e) {
            logger.error("refreshToken={}", e.getMessage(), e);
            return WrapMapper.error();
        }
        return WrapMapper.ok(token);
    }
}