/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：ValidateCodeException.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code;

import org.springframework.security.core.AuthenticationException;


/**
 * The class Validate code exception.
 *
 * @author paascloud.net@gmail.com
 */
public class ValidateCodeException extends AuthenticationException {


	private static final long serialVersionUID = -7285211528095468156L;

	/**
	 * Instantiates a new Validate code exception.
	 *
	 * @param msg the msg
	 */
	public ValidateCodeException(String msg) {
		super(msg);
	}

}
