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

package com.paascloud.provider.model.service;

import com.paascloud.provider.model.dto.user.UserInfoDto;
import com.paascloud.provider.model.service.hystrix.MallUserFeignApiHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The class Mall user controller.
 *
 * @author paascloud.net@gmail.com
 */
@FeignClient(value = "paascloud-provider-uac" , configuration = OAuth2FeignAutoConfiguration.class, fallback = MallUserFeignApiHystrix.class)
public interface MallUserFeignApi {

	/**
	 * 更新用户信息.
	 *
	 * @param userInfoDto the user info dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/updateInformation")
	Wrapper<UserInfoDto> updateInformation(@RequestBody UserInfoDto userInfoDto);

	/**
	 * 获取用户信息.
	 *
	 * @return the information
	 */
	@PostMapping(value = "/getInformation")
	Wrapper<UserInfoDto> getInformation();
}
