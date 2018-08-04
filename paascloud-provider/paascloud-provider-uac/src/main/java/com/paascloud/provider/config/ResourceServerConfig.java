/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ResourceServerConfig.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.config;

import com.paascloud.config.properties.PaascloudProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * The class Resource server config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Resource
	private PaascloudProperties paascloudProperties;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
		paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls().forEach(url ->registry.antMatchers(url).permitAll());

		registry.anyRequest().authenticated()
				.and()
				.csrf().disable();

		http.headers().frameOptions().disable();
	}
}
