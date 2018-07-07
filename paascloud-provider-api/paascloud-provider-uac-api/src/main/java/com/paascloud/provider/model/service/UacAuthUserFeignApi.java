/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：ForgetCheckAnswerDto.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service;


import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.service.hystrix.UacAuthUserFeignApiHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Uac user token feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacAuthUserFeignApiHystrix.class)
public interface UacAuthUserFeignApi {

	/**
	 * Gets auth user dto.
	 *
	 * @param loginName the login name
	 *
	 * @return the auth user dto
	 */
	@PostMapping(value = "/uac/auth/getAuthUserDTO/{loginName}")
	Wrapper<AuthUserDTO> getAuthUserDTO(@PathVariable("loginName") String loginName);

	/**
	 * Handler login data.
	 *
	 * @param handlerLoginDTO the handler login dto
	 */
	@PostMapping(value = "/uac/auth/handlerLoginDTO")
	void handlerLoginData(@RequestBody HandlerLoginDTO handlerLoginDTO);

}
