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


import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.provider.model.dto.log.OperationLogDto;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.service.hystrix.UacAuthUserFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

/**
 * The interface Uac user token feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacAuthUserFeignHystrix.class)
public interface UacAuthUserFeignApi {

	/**
	 * 校验手机号码.
	 *
	 * @param mobileNo the mobile no
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/checkPhoneActive/{mobileNo}")
    Wrapper<Boolean> checkPhoneActive(@PathVariable(name = "mobileNo") String mobileNo);

	/**
	 * 校验邮箱.
	 *
	 * @param email the email
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/checkEmailActive")
    Wrapper<Boolean> checkEmailActive(@RequestParam("email") String email);

	/**
	 * 校验数据.
	 *
	 * @param checkValidDto the check valid dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/checkValid")
    Wrapper checkValid(@RequestBody CheckValidDto checkValidDto);


	/**
	 * 重置密码-邮箱-提交.
	 *
	 * @param email the email
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/submitResetPwdEmail")
	@ApiOperation(httpMethod = "POST", value = "重置密码-邮箱-提交")
    Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email);

	/**
	 * 重置密码-手机-提交.
	 *
	 * @param mobile the mobile
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/submitResetPwdPhone")
	@ApiOperation(httpMethod = "POST", value = "重置密码-手机-提交")
    Wrapper<String> submitResetPwdPhone(@RequestParam("mobile") String mobile);

	/**
	 * 重置密码-最终提交.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/resetLoginPwd")
	@ApiOperation(httpMethod = "POST", value = "重置密码-最终提交")
    Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto);

	/**
	 * 注册用户.
	 *
	 * @param user the user
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/register")
    Wrapper registerUser(@RequestBody UserRegisterDto user);

	/**
	 * 激活用户.
	 *
	 * @param activeUserToken the active user token
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/activeUser/{activeUserToken}")
	@ApiOperation(httpMethod = "POST", value = "激活用户")
    Wrapper activeUser(@PathVariable("activeUserToken") String activeUserToken);

	/**
	 * 查询日志.
	 *
	 * @param operationLogDto the operation log dto
	 * @return the integer
	 */
	@PostMapping(value = "/uac/auth/saveLog")
	@ApiOperation(httpMethod = "POST", value = "查询日志")
    Wrapper<Integer> saveLog(@RequestBody OperationLogDto operationLogDto);

	/**
	 * Gets auth user dto.
	 *
	 * @param loginName the login name
	 * @return the auth user dto
	 */
	@PostMapping(value = "/uac/auth/getAuthUserDTO/{loginName}")
    Wrapper<AuthUserDTO> getAuthUserDTO(@PathVariable("loginName") String loginName);

	/**
	 * Handler login data.
	 *
	 * @param handlerLoginDTO the handler login dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/auth/handlerLoginDTO")
    Wrapper<?> handlerLoginData(@RequestBody HandlerLoginDTO handlerLoginDTO);
}
