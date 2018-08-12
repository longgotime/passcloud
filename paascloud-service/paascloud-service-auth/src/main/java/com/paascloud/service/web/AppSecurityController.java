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
import com.paascloud.PubUtils;
import com.paascloud.RandomUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.core.support.BaseController;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.dto.user.UserInfoDto;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.security.core.CookieUtil;
import com.paascloud.security.core.SecurityConstants;
import com.paascloud.service.security.social.AppSingUpUtils;
import com.paascloud.service.security.social.support.SocialUserInfo;
import com.paascloud.service.security.social.weixin.connect.WeixinServiceProvider;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * The class App security controller.
 *
 * @author paascloud.net @gmail.comg
 */
@RestController
@Slf4j
public class AppSecurityController extends BaseController {

	@Resource
	private ProviderSignInUtils providerSignInUtils;

	@Resource
	private AppSingUpUtils appSingUpUtils;
	@Resource
	private PaascloudProperties paascloudProperties;
	@Resource
	private UacUserFeignApi uacUserFeignApi;

	/**
	 * 需要注册时跳到这里，返回401和用户信息给前端
	 *
	 * @param request the request
	 * @return social user info
	 */
	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
	public Wrapper<String> getSocialUserInfo(HttpServletRequest request) {
		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		appSingUpUtils.saveConnectionData(new ServletWebRequest(request), connection.createData());

		//TODO 这里简单实现了, 直接创建一个用户

		// 本地调试 cookie 写入失败, 以下代码为调试代码, 见谅

		String name = PubUtils.uuid();
		String tel = com.xiaoleilu.hutool.util.RandomUtil.randomNumbers(11);

		SocialUserInfo socialUser = this.buildSocialUserInfo(connection);
		UserInfoDto user = new UserInfoDto();

		user.setEmail(name + "@163.com");
		user.setGroupId(1L);
		user.setGroupName("paascloud");
		user.setLastLoginIp(RequestUtil.getRemoteAddr(request));
		user.setLastLoginTime(new Date());
		user.setLoginName(name);
		user.setMobileNo(tel);
		user.setStatus(UacUserStatusEnum.ENABLE.getKey());
		user.setUserName(socialUser.getNickname());
		user.setUserCode(name);
		user.setRemark("微信授权, 自动生成");
		user.setSalt(name);


		LoginAuthDto authDto = new LoginAuthDto();
		authDto.setGroupName("paascloud");
		authDto.setGroupId(1L);
		authDto.setLoginName("admin");
		authDto.setUserName("超级管理员");
		authDto.setUserId(1L);
		user.setLoginAuthDto(authDto);


		uacUserFeignApi.saveUacUser(user);

		Wrapper<UserVo> userVoWrapper = uacUserFeignApi.queryUserInfo(name);

		String userId = String.valueOf(userVoWrapper.getResult().getId());
		appSingUpUtils.doPostSignUp(new ServletWebRequest(request), userId);

		return WrapMapper.ok("注册成功");
	}

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
		String state = Md5Util.encrypt(RandomUtil.createComplexCode(8));
		String url = String.format(qrconectUrl, paascloudProperties.getSecurity().getSocial().getWeixin().getAppId(), urlEncode, state);

		CookieUtil.setCookie("PASSCLOUD_PAAS_SOCIAL_WXKEY", state, 10 * 60, response);

		//TODO 以后有时间 这里的 token 要在 redis 中做一个安全校验, 类似签名

		return WrapMapper.ok(url);
	}


	private SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
		SocialUserInfo userInfo = new SocialUserInfo();
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}
}
