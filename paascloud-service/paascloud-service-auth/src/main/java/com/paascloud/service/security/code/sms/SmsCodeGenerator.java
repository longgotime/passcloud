/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：SmsCodeGenerator.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code.sms;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.service.security.code.ValidateCode;
import com.paascloud.service.security.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;

/**
 * 短信验证码生成器
 *
 * @author paascloud.net @gmail.com
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * Generate validate code.
	 *
	 * @param request the request
	 *
	 * @return the validate code
	 */
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(paascloudProperties.getSecurity().getCode().getSms().getLength());
		return new ValidateCode(code, paascloudProperties.getSecurity().getCode().getSms().getExpireIn());
	}
}
