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

package com.paascloud.provider.web.rpc;


import com.paascloud.provider.model.dto.user.UserModifyPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.service.UacUserPasswordFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户密码.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacUserPasswordFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserPasswordFeignClient implements UacUserPasswordFeignApi {


	@Override
	public Wrapper<Integer> modifyUserPwd(UserModifyPwdDto userModifyPwdDto) {
		return null;
	}

	@Override
	public Wrapper registerUser(UserRegisterDto registerDto) {
		return null;
	}
}
