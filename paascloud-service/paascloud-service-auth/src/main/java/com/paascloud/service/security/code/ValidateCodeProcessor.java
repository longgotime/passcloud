/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：ValidateCodeProcessor.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 *
 * @author paascloud.net @gmail.com
 */
public interface ValidateCodeProcessor {

	/**
	 * 创建校验码
	 *
	 * @param request the request
	 *
	 * @throws Exception the exception
	 */
	void create(ServletWebRequest request) throws Exception;

	/**
	 * 校验验证码(验证后删除)
	 *
	 * @param servletWebRequest the servlet web request
	 */
	void validate(ServletWebRequest servletWebRequest);

	/**
	 * 校验验证码(验证后不删除)
	 *
	 * @param servletWebRequest the servlet web request
	 */
	void check(ServletWebRequest servletWebRequest);

}
