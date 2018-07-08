/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：EmailCodeGenerator.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code.email;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.service.security.code.ValidateCode;
import com.paascloud.service.security.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 短信验证码生成器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component("emailValidateCodeGenerator")
public class EmailCodeGenerator implements ValidateCodeGenerator {

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
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
		String[] emails = parameterMap.get("email");
		log.info(Arrays.toString(emails));
		String code = Arrays.toString(emails);
		return new ValidateCode(code, paascloudProperties.getSecurity().getCode().getEmail().getExpireIn());
	}
}
