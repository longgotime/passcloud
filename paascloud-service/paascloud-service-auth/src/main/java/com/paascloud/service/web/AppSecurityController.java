/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AppSecurityController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.service.web;

import com.paascloud.Md5Util;
import com.paascloud.RandomUtil;
import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.security.core.SecurityConstants;
import com.paascloud.service.security.social.AppSingUpUtils;
import com.paascloud.service.security.social.support.SocialUserInfo;
import com.paascloud.service.security.social.weixin.connect.WeixinServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * The class App security controller.
 *
 * @author paascloud.net@gmail.comg
 */
@RestController
@Slf4j
public class AppSecurityController extends BaseSocialController {

	@Resource
	private ProviderSignInUtils providerSignInUtils;

	@Resource
	private AppSingUpUtils appSingUpUtils;
	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * 需要注册时跳到这里，返回401和用户信息给前端
	 *
	 * @param request the request
	 *
	 * @return social user info
	 */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());
		return buildSocialUserInfo(connection);
	}

    /**
     * 微信登录请求CODE
     * @param response the response
     * @throws IOException
     */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_QRCONNECT_URL)
	public void getWeChatQrconnect(HttpServletResponse response) throws IOException{
		String urlEncode = URLEncoder.encode(paascloudProperties.getSecurity().getSocial().getWeixin().getCallBackUrl(), "utf-8");
		String qrconectUrl = WeixinServiceProvider.URL_AUTHORIZE + "?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
		String state = Md5Util.encrypt(RandomUtil.createComplexCode(8));
		String url = String.format(qrconectUrl, paascloudProperties.getSecurity().getSocial().getWeixin().getAppId(), urlEncode, state);

		response.sendRedirect(url);
	}
}
