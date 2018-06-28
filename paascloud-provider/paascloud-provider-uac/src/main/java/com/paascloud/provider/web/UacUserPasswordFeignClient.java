/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserPasswordController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.user.UserModifyPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.service.UacUserPasswordFeignApi;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户密码.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacUserPasswordFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserPasswordFeignClient extends BaseController implements UacUserPasswordFeignApi {

	@Resource
	private UacUserService uacUserService;

	@Override
	public Wrapper<Integer> modifyUserPwd(UserModifyPwdDto userModifyPwdDto) {
		logger.info("==》vue用户修改密码, userModifyPwdDto={}", userModifyPwdDto);

		logger.info("旧密码 oldPassword = {}", userModifyPwdDto.getOldPassword());
		logger.info("新密码 newPassword = {}", userModifyPwdDto.getNewPassword());
		logger.info("登录名 loginName = {}", userModifyPwdDto.getLoginName());

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		int result = uacUserService.userModifyPwd(userModifyPwdDto, loginAuthDto);
		return handleResult(result);
	}

	@Override
	public Wrapper registerUser(UserRegisterDto registerDto) {
		logger.info("vue注册开始。注册参数：{}", registerDto);
		uacUserService.register(registerDto);
		return WrapMapper.ok("注册成功");
	}
}
