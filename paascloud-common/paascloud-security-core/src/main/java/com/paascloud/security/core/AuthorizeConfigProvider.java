/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthorizeConfigProvider.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.security.core;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 授权配置提供器，各个模块和业务系统可以通过实现此接口向系统添加授权配置。
 *
 * @author paascloud.net @gmail.com
 */
public interface AuthorizeConfigProvider {

	/**
	 * Config boolean.
	 *
	 * @param config the config
	 *
	 * @return 返回的boolean表示配置中是否有针对anyRequest的配置 。在整个授权配置中， 应该有且仅有一个针对anyRequest的配置，如果所有的实现都没有针对anyRequest的配置， 系统会自动增加一个anyRequest().authenticated()的配置。如果有多个针对anyRequest 的配置，则会抛出异常。
	 */
	boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
