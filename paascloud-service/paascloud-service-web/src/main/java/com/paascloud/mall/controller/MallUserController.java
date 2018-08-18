/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallUserController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.mall.controller;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.user.SaveUserDTO;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mall user controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallUserController extends BaseController {
	@Resource
	private UacUserFeignApi uacUserFeignApi;

	/**
	 * 更新用户信息.
	 *
	 * @param user the user
	 * @return the wrapper
	 */
	@PostMapping(value = "/updateInformation")
	@ApiOperation(httpMethod = "POST", value = "更新用户信息")
	public Wrapper updateInformation(@RequestBody SaveUserDTO user) {
		logger.info("updateInformation - 更新用户基本信息 user={}", user);
		return uacUserFeignApi.saveUacUser(user);
	}

	/**
	 * 获取用户信息.
	 *
	 * @return the information
	 */
	@PostMapping(value = "/getInformation")
	@ApiOperation(httpMethod = "POST", value = "获取用户信息")
	public Wrapper<UserVo> getInformation() {
		Long userId = getLoginAuthDto().getUserId();
		logger.info("queryUserInfo - 查询用户基本信息 userId={}", userId);

		return uacUserFeignApi.getUacUserById(userId);
	}
}
