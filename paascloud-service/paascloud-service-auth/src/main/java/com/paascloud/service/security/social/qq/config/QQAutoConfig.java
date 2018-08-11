
/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：QQAutoConfig.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.service.security.social.qq.config;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.config.properties.QQProperties;
import com.paascloud.service.security.social.qq.connet.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;


/**
 * The class Qq auto config.
 *
 * @author paascloud.net@gmail.com
 */
@Configuration
@ConditionalOnProperty(prefix = "paascloud.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

	private final PaascloudProperties paascloudProperties;

	@Autowired
	public QQAutoConfig(PaascloudProperties paascloudProperties) {
		this.paascloudProperties = paascloudProperties;
	}

	/**
	 * Create connection factory connection factory.
	 *
	 * @return the connection factory
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		QQProperties qqConfig = paascloudProperties.getSecurity().getSocial().getQq();
		return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
	}

}
