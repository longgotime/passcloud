/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.uac;

import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.common.config.WebMvcConfig;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.log.OperationLogDto;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.security.core.CookieUtil;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The class Uac action main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacAuthUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacAuthUserController extends BaseController {


	@Resource
	private UacAuthUserFeignApi uacAuthUserFeignApi;


	/**
	 * 校验手机号码.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkPhoneActive/{mobileNo}")
	@ApiOperation(httpMethod = "POST", value = "校验手机号码")
	public Wrapper<Boolean> checkPhoneActive(@PathVariable String mobileNo) {
		return uacAuthUserFeignApi.checkPhoneActive(mobileNo);
	}

	/**
	 * 校验邮箱.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmailActive/{email:.+}")
	@ApiOperation(httpMethod = "POST", value = "校验邮箱")
	public Wrapper<Boolean> checkEmailActive(@PathVariable String email) {
		return uacAuthUserFeignApi.checkEmailActive(email);
	}

	/**
	 * 校验数据.
	 *
	 * @param checkValidDto the check valid dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkValid")
	@ApiOperation(httpMethod = "POST", value = "校验数据")
	public Wrapper checkValid(@RequestBody CheckValidDto checkValidDto) {
		return uacAuthUserFeignApi.checkValid(checkValidDto);
	}

	/**
	 * 重置密码-邮箱-提交.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdEmail")
	@ApiOperation(httpMethod = "POST", value = "重置密码-邮箱-提交")
	public Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email) {
		logger.info("重置密码-邮箱-提交, email={}", email);
		return uacAuthUserFeignApi.submitResetPwdEmail(email);
	}


	/**
	 * 重置密码-手机-提交.
	 *
	 * @param mobile   the mobile
	 * @param response the response
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdPhone")
	@ApiOperation(httpMethod = "POST", value = "重置密码-手机-提交")
	public Wrapper<String> submitResetPwdPhone(String mobile, HttpServletResponse response) {
		logger.info("重置密码-手机-提交, mobile={}", mobile);
		Wrapper<String> result = uacAuthUserFeignApi.submitResetPwdPhone(mobile);
		if (result.success()) {
			CookieUtil.setCookie("PASSCLOUD_PAAS_resetPwdKey", result.getResult(), 10 * 60, response);
		}

		return result;
	}

	/**
	 * 重置密码-最终提交.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resetLoginPwd")
	@ApiOperation(httpMethod = "POST", value = "重置密码-最终提交")
	public Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto) {
		return uacAuthUserFeignApi.checkResetSmsCode(resetLoginPwdDto);
	}

	/**
	 * 注册用户.
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/register")
	@ApiOperation(httpMethod = "POST", value = "注册用户")
	public Wrapper registerUser(UserRegisterDto user) {
		return uacAuthUserFeignApi.registerUser(user);
	}

	/**
	 * 激活用户.
	 *
	 * @param activeUserToken the active user token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/activeUser/{activeUserToken}")
	@ApiOperation(httpMethod = "POST", value = "激活用户")
	public Wrapper activeUser(@PathVariable String activeUserToken) {
		return uacAuthUserFeignApi.activeUser(activeUserToken);
	}

	/**
	 * 查询日志.
	 *
	 * @param operationLogDto the operation log dto
	 *
	 * @return the integer
	 */
	@PostMapping(value = "/saveLog")
	@ApiOperation(httpMethod = "POST", value = "查询日志")
	public Wrapper<Integer> saveLog(@RequestBody OperationLogDto operationLogDto) {
		logger.info("saveLog - 保存操作日志. operationLogDto={}", operationLogDto);
		operationLogDto.setLoginAuthDto(getLoginAuthDto());

		return uacAuthUserFeignApi.saveLog(operationLogDto);
	}
}
