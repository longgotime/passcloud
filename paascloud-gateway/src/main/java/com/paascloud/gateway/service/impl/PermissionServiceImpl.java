/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PermissionServiceImpl.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.gateway.service.impl;

import com.google.common.base.Joiner;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.gateway.service.PermissionService;
import com.paascloud.security.core.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * The class Uac permission service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component("permissionService")
public class PermissionServiceImpl implements PermissionService {
	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public boolean hasPermission(Authentication authentication, HttpServletRequest request) {
		String currentLoginName = SecurityUtils.getCurrentLoginName();
		Set<String> currentAuthorityUrl = SecurityUtils.getCurrentAuthorityUrl();
		String requestURI = request.getRequestURI();
		log.info("验证权限loginName={}, requestURI={}, hasAuthorityUrl={}", currentLoginName, requestURI, Joiner.on(GlobalConstant.Symbol.COMMA).join(currentAuthorityUrl));
		// 超级管理员 全部都可以访问
		if (StringUtils.equals(currentLoginName, GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME)) {
			return true;
		}

		for (final String authority : currentAuthorityUrl) {
			// DEMO项目放过查询权限
			if (requestURI.contains("query") || requestURI.contains("get") || requestURI.contains("check") || requestURI.contains("select")) {
				return true;
			}
			if (antPathMatcher.match(authority, requestURI)) {
				return true;
			}
		}
		return true;
	}
}
