/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PcAuthenticationFailureHandler.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paascloud.wrapper.WrapMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author paascloud.net @gmail.com
 */
@Component("pcAuthenticationFailureHandler")
public class PcAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Resource
	private ObjectMapper objectMapper;

	/**
	 * On authentication failure.
	 *
	 * @param request   the request
	 * @param response  the response
	 * @param exception the exception
	 *
	 * @throws IOException      the io exception
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                    AuthenticationException exception) throws IOException, ServletException {

		logger.info("登录失败");

		//FIXME 记录失败次数 和原因 ip等信息 5次登录失败,锁定用户, 不允许再次登录

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(WrapMapper.error(exception.getMessage())));

	}

}
