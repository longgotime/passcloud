/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：HandlerLoginDTO.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The class Handler login dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerLoginDTO implements Serializable {
	private static final long serialVersionUID = -4425127652925781007L;
	/**
	 * accessToken
	 */
	String accessToken;
	/**
	 * refreshToken
	 */
	String refreshToken;
	/**
	 * 用户信息
	 */
	AuthUserDTO principal;
	/**
	 * 客户端操作系统
	 */
	String os;
	/**
	 * 获取客户端浏览器
	 */
	String browser;
	/**
	 * 访问者IP
	 */
	String remoteAddr;
	/**
	 * 请求路径
	 */
	String requestURI;
	/**
	 * 远程地址
	 */
	String remoteLocation;
}
