/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：UacRpcService.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.service;

import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.security.core.PcSocialUser;
import com.paascloud.security.core.SecurityUser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The class Uac rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class UacRpcService {
	@Resource
	private UacAuthUserFeignApi uacAuthUserFeignApi;

	public void handlerLoginData(final String accessToken, final String refreshToken, final Object principal, final UserAgent userAgent, final String remoteAddr, final String requestURI, final String remoteLocation) {
		//获取客户端操作系统
		final String os = userAgent.getOperatingSystem().getName();
		//获取客户端浏览器
		final String browser = userAgent.getBrowser().getName();

		SecurityUser securityUser;
		PcSocialUser socialUser;
		AuthUserDTO authUserDTO = new AuthUserDTO();

		if (principal instanceof SecurityUser) {
			securityUser = (SecurityUser) principal;
			authUserDTO.setUserId(securityUser.getUserId());
			authUserDTO.setLoginName(securityUser.getLoginName());
			authUserDTO.setGroupId(securityUser.getGroupId());
			authUserDTO.setGroupName(securityUser.getGroupName());
			authUserDTO.setNickName(securityUser.getNickName());

		} else if (principal instanceof PcSocialUser) {
			socialUser = (PcSocialUser) principal;
			authUserDTO.setUserId(Long.valueOf(socialUser.getUserId()));
			authUserDTO.setLoginName(socialUser.getLoginName());
			authUserDTO.setGroupId(socialUser.getGroupId());
			authUserDTO.setGroupName(socialUser.getGroupName());
			authUserDTO.setNickName(socialUser.getNickName());
		} else {
			throw new IllegalStateException("登陆数据异常");
		}
		log.info("用户【 {} 】记录登录日志", authUserDTO.getLoginName());
		uacAuthUserFeignApi.handlerLoginData(new HandlerLoginDTO(accessToken, refreshToken, authUserDTO, os, browser, remoteAddr, requestURI, remoteLocation));
	}
}
