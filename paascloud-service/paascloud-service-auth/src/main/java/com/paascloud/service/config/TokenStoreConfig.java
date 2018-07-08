 /*
  * Copyright (c) 2018. paascloud.net All Rights Reserved.
  * 项目名称：paascloud快速搭建企业级分布式微服务平台
  *  类名称：TokenStoreConfig.java
  *  创建人：刘兆明
  *  联系方式：paascloud.net@gmail.com
  *  开源地址: https://github.com/paascloud
  *  博客地址: http://blog.paascloud.net
  *  项目官网: http://paascloud.net
  */

 package com.paascloud.service.config;

 import com.paascloud.config.properties.PaascloudProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;


 /**
  * The class Token store config.
  *
  * @author paascloud.net @gmail.com
  */
 @Configuration
 public class TokenStoreConfig {
	 @Resource
	 private PaascloudProperties paascloudProperties;

	 /**
	  * Jwt token store token store.
	  *
	  * @return the token store
	  */
	 @Bean
	 public TokenStore jwtTokenStore() {
		 return new JwtTokenStore(jwtAccessTokenConverter());
	 }

	 /**
	  * Jwt access token converter jwt access token converter.
	  *
	  * @return the jwt access token converter
	  */
	 @Bean
	 public JwtAccessTokenConverter jwtAccessTokenConverter() {
		 JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		 converter.setSigningKey(paascloudProperties.getSecurity().getOauth2().getJwtSigningKey());
		 return converter;
	 }

	 /**
	  * Jwt token enhancer token enhancer.
	  *
	  * @return the token enhancer
	  */
	 @Bean
	 @ConditionalOnBean(TokenEnhancer.class)
	 public TokenEnhancer jwtTokenEnhancer() {
		 return new TokenJwtEnhancer();
	 }
 }
