/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthHeaderFilter.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.paascloud.JacksonUtil;
import com.paascloud.PublicUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UserTokenDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.interceptor.CoreHeaderInterceptor;
import com.paascloud.core.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The class Auth header filter.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class AuthHeaderFilter extends ZuulFilter {

	private static final String BEARER_TOKEN_TYPE = "Bearer ";
	private static final String OPTIONS = "OPTIONS";
	private static final String AUTH_PATH = "/auth";
	private static final String LOGOUT_URI = "/oauth/token";
	private static final String ALIPAY_CALL_URI = "/pay/alipayCallback";

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * Filter type string.
	 *
	 * @return the string
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * Filter order int.
	 *
	 * @return the int
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * Should filter boolean.
	 *
	 * @return the boolean
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * Run object.
	 *
	 * @return the object
	 */
	@Override
	public Object run() {
		log.info("AuthHeaderFilter - 开始鉴权...");
		RequestContext requestContext = RequestContext.getCurrentContext();
		try {
			doSomething(requestContext);
		} catch (Exception e) {
			log.error("AuthHeaderFilter - [FAIL] EXCEPTION={}", e.getMessage(), e);
			throw new BusinessException(ErrorCodeEnum.UAC10011041);
		}
		return null;
	}

	private void doSomething(RequestContext requestContext) throws ZuulException, IOException {
		HttpServletRequest request = requestContext.getRequest();
		String requestURI = request.getRequestURI();

		if (OPTIONS.equalsIgnoreCase(request.getMethod()) || requestURI.contains(AUTH_PATH) || requestURI.contains(LOGOUT_URI) || requestURI.contains(ALIPAY_CALL_URI)) {
			return;
		}
		String authHeader = RequestUtil.getAuthHeader(request);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (PublicUtil.isEmpty(authHeader)) {
			throw new ZuulException("刷新页面重试", 403, "check token fail");
		}

		if (authHeader.startsWith(BEARER_TOKEN_TYPE)) {
			requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, authHeader);
            String name = authentication.getName();

            LoginAuthDto loginAuthDto = new LoginAuthDto();
            loginAuthDto.setUserId(1L);
            loginAuthDto.setUserName("admin");
            loginAuthDto.setLoginName("admin");
            loginAuthDto.setGroupId(1L);
            loginAuthDto.setGroupName("paascloud");

            requestContext.addZuulRequestHeader(GlobalConstant.Sys.TOKEN_AUTH_DTO, JacksonUtil.toJson(loginAuthDto));

			log.info("authHeader={} ", authHeader);
			// 传递给后续微服务
			requestContext.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, authHeader);
		}
	}

}
