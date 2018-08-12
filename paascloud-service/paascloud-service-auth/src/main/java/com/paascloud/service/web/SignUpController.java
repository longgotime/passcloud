/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：SignUpController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.service.web;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SignUpController extends BaseController{

	@Resource
	private UacUserFeignApi uacUserFeignApi;

	/**
	 * 注册
	 *
	 * @param registerDto the register dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/user/registerUser")
	@ApiOperation(httpMethod = "POST", value = "注册新用户")
	public Wrapper registerUser(@RequestBody UserRegisterDto registerDto) {
		logger.info("vue注册开始。注册参数：{}", registerDto);
        return uacUserFeignApi.registerUser(registerDto);
	}
}
