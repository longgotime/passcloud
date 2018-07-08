/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：SmsCodeProperties.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.config.properties;

import lombok.Data;

/**
 * The class Sms code properties.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class SmsCodeProperties {

	/**
	 * 验证码长度
	 */
	private int length = 6;
	/**
	 * 过期时间
	 */
	private int expireIn = 60;
	/**
	 * 要拦截的url，多个url用逗号隔开，ant pattern
	 */
	private String url;
	/**
	 * 每天每个手机号最大送送短信数量
	 */
	private int mobileMaxSendCount;
	/**
	 * 每天每个IP最大送送短信数量
	 */
	private int ipMaxSendCount;
	/**
	 * 每天最大送送短信数量
	 */
	private int totalMaxSendCount;


}
