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

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.core.support.BaseController;
import com.paascloud.security.core.CookieUtil;
import com.paascloud.security.core.SecurityConstants;
import com.paascloud.service.security.social.weixin.connect.WeixinServiceProvider;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * The class App security controller.
 *
 * @author paascloud.net @gmail.comg
 */
@RestController
@Slf4j
public class AppSecurityController extends BaseController {

	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * 微信登录请求CODE
	 *
	 * @param response the response
	 * @throws IOException the io exception
	 */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_QRCONNECT_URL)
	public Wrapper<String> getWeChatQrconnect(HttpServletResponse response) throws IOException{
		String urlEncode = URLEncoder.encode(paascloudProperties.getSecurity().getSocial().getWeixin().getCallBackUrl(), "utf-8");
		String qrconectUrl = WeixinServiceProvider.URL_AUTHORIZE + "?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";
		String state = com.xiaoleilu.hutool.util.RandomUtil.randomUUID();
		String url = String.format(qrconectUrl, paascloudProperties.getSecurity().getSocial().getWeixin().getAppId(), urlEncode, state);

		CookieUtil.setCookie("PASSCLOUD_PAAS_SOCIAL_WXKEY", state, 10 * 60, response);

		//TODO 以后有时间 这里的 token 要在 redis 中做一个安全校验, 类似签名

		return WrapMapper.ok(url);
	}
}
