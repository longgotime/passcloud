/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：RestClientDetailsServiceImpl.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security;

import com.paascloud.config.properties.OAuth2ClientProperties;
import com.paascloud.config.properties.PaascloudProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * The class Rest client details service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component("restClientDetailsService")
public class RestClientDetailsServiceImpl implements ClientDetailsService {

	private ClientDetailsService clientDetailsService;

	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
		if (ArrayUtils.isNotEmpty(paascloudProperties.getSecurity().getOauth2().getClients())) {
			for (OAuth2ClientProperties client : paascloudProperties.getSecurity().getOauth2().getClients()) {
				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("refresh_token", "password", "client_credentials")
						.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
						.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
						.scopes(client.getScope());
			}
		}
		try {
			clientDetailsService = builder.build();
		} catch (Exception e) {
			log.error("init={}", e.getMessage(), e);
		}
	}

	/**
	 * Load client by client id client details.
	 *
	 * @param clientId the client id
	 *
	 * @return the client details
	 *
	 * @throws ClientRegistrationException the client registration exception
	 */
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDetailsService.loadClientByClientId(clientId);
	}
}
