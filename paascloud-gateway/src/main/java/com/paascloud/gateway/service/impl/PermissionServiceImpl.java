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
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.gateway.service.PermissionService;
import com.paascloud.provider.model.service.UacRoleFeignApi;
import com.paascloud.security.core.SecurityUtils;
import com.paascloud.wrapper.Wrapper;
import com.xiaoleilu.hutool.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

	@Resource
	private PaascloudProperties paascloudProperties;
    @Resource
	private UacRoleFeignApi uacRoleFeignApi;

	@Override
	public boolean hasPermission(Authentication authentication, HttpServletRequest request) {
		String currentLoginName = SecurityUtils.getCurrentLoginName();
		Set<String> currentAuthorityList = SecurityUtils.getCurrentAuthorityUrl();
		String requestURI = request.getRequestURI();
		log.info("验证权限loginName={}, requestURI={}, hasAuthorityUrl={}", currentLoginName, requestURI, Joiner.on(GlobalConstant.Symbol.COMMA).join(currentAuthorityList));
		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
			return true;
		}
		// 超级管理员 全部都可以访问
		if (StringUtils.equals(currentLoginName, GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME)) {
			 return true;
		}

		if (PublicUtil.isEmpty(currentAuthorityList)) {
			log.warn("角色列表为空：loginName={}", currentLoginName);
			return false;
		}

        Wrapper<Set<String>> wrapper = uacRoleFeignApi.listAuthorityUrl(currentAuthorityList);

		if (wrapper.error()) {
		    return false;
        }

        for (final String authority : wrapper.getResult()) {
			// DEMO项目放过查询权限
			if (requestURI.contains("query") || requestURI.contains("get") || requestURI.contains("check") || requestURI.contains("select")) {
				return true;
			}

			List<String> urls = paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls();

			for (String url : urls) {
				if (antPathMatcher.match(url, requestURI)) {
					return true;
				}
			}
			if (antPathMatcher.match(authority, requestURI)) {
				return true;
			}
		}
		return false;
	}
}
