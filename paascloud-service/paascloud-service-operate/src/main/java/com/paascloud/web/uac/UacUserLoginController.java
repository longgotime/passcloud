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

package com.paascloud.web.uac;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.service.UacUserLoginFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * 登录相关.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserLoginController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserLoginController extends BaseController{


	@Resource
	private UacUserLoginFeignApi uacUserLoginFeignApi;


	/**
	 * 登录成功获取菜单信息和用户信息.
	 *
	 * @param applicationId the application id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/loginAfter/{applicationId}")
	@ApiOperation(httpMethod = "POST", value = "登录成功获取用户菜单")
	public Wrapper<LoginRespDto> loginAfter(@PathVariable Long applicationId) {
		logger.info("登录成功获取用户菜单. applicationId={}", applicationId);
		return uacUserLoginFeignApi.loginAfter(applicationId);
	}


	/**
	 * 登出.
	 *
	 * @param accessToken the access token
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/logout")
	@ApiOperation(httpMethod = "POST", value = "登出")
	public Wrapper loginAfter(String accessToken) {
		return uacUserLoginFeignApi.loginAfter(accessToken);
	}

	/**
	 * 刷新token.
	 *
	 * @param request      the request
	 * @param refreshToken the refresh token
	 * @param accessToken  the access token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/auth/user/refreshToken")
	@ApiOperation(httpMethod = "POST", value = "刷新token")
	public Wrapper<String> refreshToken(HttpServletRequest request, String refreshToken, String accessToken) {
		return uacUserLoginFeignApi.refreshToken(request, refreshToken, accessToken);
	}
}