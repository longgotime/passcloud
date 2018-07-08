/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：ValidateCodeType.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security.code;


import com.paascloud.security.core.SecurityConstants;

/**
 * 校验码类型
 *
 * @author paascloud.net @gmail.com
 */
public enum ValidateCodeType {

	/**
	 * 短信验证码
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},
	/**
	 * 图片验证码
	 */
	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	},
	/**
	 * 邮箱验证码
	 */
	EMAIL {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_EMAIL;
		}
	};

	/**
	 * 校验时从请求中获取的参数的名字
	 *
	 * @return param name on validate
	 */
	public abstract String getParamNameOnValidate();

}
