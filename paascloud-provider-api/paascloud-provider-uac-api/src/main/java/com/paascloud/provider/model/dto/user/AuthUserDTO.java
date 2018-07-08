/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthUserDTO.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * The class Auth user dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserDTO implements Serializable {
	private static final long serialVersionUID = -404960546624024001L;

	private Long userId;

	private String nickName;

	private String loginName;

	private String loginPwd;

	private String status;

	private Long groupId;

	private String groupName;

	List<String> authUrlList;
}
