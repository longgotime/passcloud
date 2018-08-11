/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：SecurityProperties.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.config.properties;

import lombok.Data;


/**
 * The class Security properties.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class SecurityProperties {

	/**
	 * 验证码配置
	 */
	private ValidateCodeProperties code = new ValidateCodeProperties();
	/**
	 * OAuth2认证服务器配置
	 */
	private OAuth2Properties oauth2 = new OAuth2Properties();

	/**
	 * 社交登录配置
	 */
	private SocialProperties social = new SocialProperties();

}

