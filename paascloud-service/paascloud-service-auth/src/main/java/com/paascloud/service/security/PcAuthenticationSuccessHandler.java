/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PcAuthenticationSuccessHandler.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.security.core.CookieUtil;
import com.paascloud.service.service.OpcRpcService;
import com.paascloud.service.service.UacRpcService;
import com.paascloud.wrapper.WrapMapper;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证成功处理器.
 *
 * @author paascloud.net @gmail.com
 */
@Component("pcAuthenticationSuccessHandler")
@Slf4j
public class PcAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private ClientDetailsService clientDetailsService;
	@Resource
	private UacRpcService uacRpcService;
	@Resource
	private OpcRpcService opcRpcService;
	@Resource
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	private static final String BEARER_TOKEN_TYPE = "Basic ";

	/**
	 * On authentication success.
	 *
	 * @param request        the request
	 * @param response       the response
	 * @param authentication the authentication
	 *
	 * @throws IOException the io exception
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException {

		logger.info("登录成功");

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (header == null || !header.startsWith(BEARER_TOKEN_TYPE)) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		String[] tokens = RequestUtil.extractAndDecodeHeader(header);
		assert tokens.length == 2;

		String clientId = tokens[0];
		String clientSecret = tokens[1];

		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
		} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}

		TokenRequest tokenRequest = new TokenRequest(Maps.newHashMap(), clientId, clientDetails.getScope(), "custom");

		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

		Object principal = authentication.getPrincipal();

		final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		final String remoteAddr = RequestUtil.getRemoteAddr(request);
		// 根据IP获取位置信息
		final String remoteLocation = opcRpcService.getLocationById(remoteAddr);
		final String requestURI = request.getRequestURI();
		final String accessToken = token.getValue();
		final String refreshToken = token.getRefreshToken().getValue();
		uacRpcService.handlerLoginData(accessToken, refreshToken, principal, userAgent, remoteAddr, requestURI, remoteLocation);

		response.setContentType("application/json;charset=UTF-8");

		CookieUtil.removeCookie("PASSCLOUD_PAAS_SOCIAL_WXKEY", response);

		response.getWriter().write((objectMapper.writeValueAsString(WrapMapper.ok(token))));

	}

}
