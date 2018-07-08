/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ResourceServerConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.gateway.config;

import com.paascloud.security.core.AuthorizeConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

/**
 * The class Resource server configuration.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private final OAuth2WebSecurityExpressionHandler pcSecurityExpressionHandler;
    private final PcAccessDeniedHandler pcAccessDeniedHandler;
    private final AuthorizeConfigManager authorizeConfigManager;

    @Autowired
    public ResourceServerConfiguration(OAuth2WebSecurityExpressionHandler pcSecurityExpressionHandler, PcAccessDeniedHandler pcAccessDeniedHandler, AuthorizeConfigManager authorizeConfigManager) {
        this.pcSecurityExpressionHandler = pcSecurityExpressionHandler;
        this.pcAccessDeniedHandler = pcAccessDeniedHandler;
        this.authorizeConfigManager = authorizeConfigManager;
    }

    @Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
        authorizeConfigManager.config(http.authorizeRequests());
	}

	/**
	 * Configure.
	 *
	 * @param resources the resources
	 */
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.expressionHandler(pcSecurityExpressionHandler);
        resources.accessDeniedHandler(pcAccessDeniedHandler);
    }

	/**
	 * 加密方式
	 *
	 * @return the password encoder
	 */
	@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}