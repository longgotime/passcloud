/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserLoginFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.provider.model.dto.user.LoginRespDto;
import com.paascloud.provider.model.dto.user.LogoutDto;
import com.paascloud.provider.model.service.UacUserLoginFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录相关.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacUserLoginFeignHystrix implements UacUserLoginFeignApi {

	@Override
	public Wrapper<LoginRespDto> loginAfter(Long applicationId, String loginName) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper loginAfter(LogoutDto logoutDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<String> refreshToken(String refreshToken, String accessToken, String header, String remoteAddr, String os, String browser) {
		return WrapMapper.error();
	}


}