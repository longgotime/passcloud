/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthRestController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.provider.model.dto.log.OperationLogDto;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.service.UacAuthFeignApi;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不认证的URL请求.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacAuthFeignApiHystrix implements UacAuthFeignApi {

	@Override
	public Wrapper<Boolean> checkPhoneActive(String mobileNo) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkEmailActive(String email) {
		return null;
	}

	@Override
	public Wrapper checkValid(CheckValidDto checkValidDto) {
		return null;
	}

	@Override
	public Wrapper<String> submitResetPwdEmail(String email) {
		return null;
	}

	@Override
	public Wrapper<String> submitResetPwdPhone(String mobile, HttpServletResponse response) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto) {
		return null;
	}

	@Override
	public Wrapper registerUser(UserRegisterDto user) {
		return null;
	}

	@Override
	public Wrapper activeUser(String activeUserToken) {
		return null;
	}

	@Override
	public Integer saveLog(OperationLogDto operationLogDto) {
		return null;
	}
}
