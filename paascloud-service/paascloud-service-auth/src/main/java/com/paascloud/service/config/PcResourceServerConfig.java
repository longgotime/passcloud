/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PcResourceServerConfig.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.config;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.service.security.code.ValidateCodeSecurityConfig;
import com.paascloud.service.security.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableResourceServer
public class PcResourceServerConfig extends ResourceServerConfigurerAdapter {
	private final SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

	private final ValidateCodeSecurityConfig validateCodeSecurityConfig;

	private final FormAuthenticationConfig formAuthenticationConfig;

    private final PaascloudProperties paascloudProperties;

    @Autowired
    public PcResourceServerConfig(SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, ValidateCodeSecurityConfig validateCodeSecurityConfig, FormAuthenticationConfig formAuthenticationConfig, PaascloudProperties paascloudProperties) {
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.formAuthenticationConfig = formAuthenticationConfig;
        this.paascloudProperties = paascloudProperties;
    }

    /**
	 * Configure.
	 *
	 * @param http the http
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		formAuthenticationConfig.configure(http);

		http.apply(validateCodeSecurityConfig)
				.and()
				.apply(smsCodeAuthenticationSecurityConfig);

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls().forEach(url ->registry.antMatchers(url).permitAll());

        registry.anyRequest().authenticated()
                .and()
                .csrf().disable();

        http.headers().frameOptions().disable();
	}
}