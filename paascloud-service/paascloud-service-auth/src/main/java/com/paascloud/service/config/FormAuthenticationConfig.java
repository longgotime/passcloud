/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：FormAuthenticationConfig.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.config;

import com.paascloud.security.core.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * 表单登录配置
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class FormAuthenticationConfig {

	private final AuthenticationSuccessHandler pcAuthenticationSuccessHandler;

	private final AuthenticationFailureHandler pcAuthenticationFailureHandler;

	/**
	 * Instantiates a new Form authentication config.
	 *
	 * @param pcAuthenticationSuccessHandler the pc authentication success handler
	 * @param pcAuthenticationFailureHandler the pc authentication failure handler
	 */
	@Autowired
	public FormAuthenticationConfig(AuthenticationSuccessHandler pcAuthenticationSuccessHandler, AuthenticationFailureHandler pcAuthenticationFailureHandler) {
		this.pcAuthenticationSuccessHandler = pcAuthenticationSuccessHandler;
		this.pcAuthenticationFailureHandler = pcAuthenticationFailureHandler;
	}

	/**
	 * 表单登录配置.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
				.loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.successHandler(pcAuthenticationSuccessHandler)
				.failureHandler(pcAuthenticationFailureHandler);
	}

}
