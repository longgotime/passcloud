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

package com.paascloud.provider.model.service;


import com.paascloud.provider.model.dto.user.UserModifyPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 用户密码.
 *
 * @author paascloud.net@gmail.com
 */
@FeignClient(value = "paascloud-provider-uac")
public interface UacUserPasswordFeignApi{

	/**
	 * 用户修改密码
	 *
	 * @param userModifyPwdDto the user modify pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyUserPwd")
	Wrapper<Integer> modifyUserPwd(@ApiParam(name = "userModifyPwdDto", value = "用户修改密码Dto") @RequestBody UserModifyPwdDto userModifyPwdDto);


	/**
	 * 注册
	 *
	 * @param registerDto the register dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/registerUser")
	Wrapper registerUser(@ApiParam(name = "registerDto", value = "用户注册Dto") @RequestBody UserRegisterDto registerDto);
}
