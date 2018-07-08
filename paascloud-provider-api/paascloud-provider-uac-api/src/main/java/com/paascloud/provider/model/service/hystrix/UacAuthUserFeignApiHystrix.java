/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacAuthUserFeignApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Uac auth user feign api hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacAuthUserFeignApiHystrix implements UacAuthUserFeignApi {
	/**
	 * Gets auth user dto.
	 *
	 * @param loginName the login name
	 *
	 * @return the auth user dto
	 */
	@Override
	public Wrapper<AuthUserDTO> getAuthUserDTO(final String loginName) {
		return null;
	}

	@Override
	public void handlerLoginData(final HandlerLoginDTO handlerLoginDTO) {

	}
}
