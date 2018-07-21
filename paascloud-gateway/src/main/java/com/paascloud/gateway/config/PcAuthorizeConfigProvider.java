/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PcAuthorizeConfigProvider.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.gateway.config;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.security.core.AuthorizeConfigProvider;
import com.paascloud.security.core.SecurityConstants;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Order(Integer.MIN_VALUE)
public class PcAuthorizeConfigProvider implements AuthorizeConfigProvider {
	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * Config boolean.
	 *
	 * @param config the config
	 *
	 * @return the boolean
	 */
	@Override
	public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {

		paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls().forEach(url -> config.antMatchers(url).permitAll());
		return false;
	}

}
