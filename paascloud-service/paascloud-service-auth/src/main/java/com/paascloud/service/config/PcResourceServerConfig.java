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
import com.paascloud.service.security.authentication.openid.OpenIdAuthenticationSecurityConfig;
import com.paascloud.service.security.code.ValidateCodeSecurityConfig;
import com.paascloud.service.security.mobile.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;

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

	private final SpringSocialConfigurer pcSocialSecurityConfig;

	private final OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;

    @Autowired
    public PcResourceServerConfig(SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig, ValidateCodeSecurityConfig validateCodeSecurityConfig, FormAuthenticationConfig formAuthenticationConfig, PaascloudProperties paascloudProperties, final SpringSocialConfigurer pcSocialSecurityConfig, final OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig) {
        this.smsCodeAuthenticationSecurityConfig = smsCodeAuthenticationSecurityConfig;
        this.validateCodeSecurityConfig = validateCodeSecurityConfig;
        this.formAuthenticationConfig = formAuthenticationConfig;
        this.paascloudProperties = paascloudProperties;
	    this.pcSocialSecurityConfig = pcSocialSecurityConfig;
	    this.openIdAuthenticationSecurityConfig = openIdAuthenticationSecurityConfig;
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
				.apply(smsCodeAuthenticationSecurityConfig)
				.and()
				.apply(pcSocialSecurityConfig)
				.and()
				.apply(openIdAuthenticationSecurityConfig);

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls().forEach(url ->registry.antMatchers(url).permitAll());

        registry.anyRequest().authenticated()
                .and()
                .csrf().disable();

        http.headers().frameOptions().disable();
	}
}