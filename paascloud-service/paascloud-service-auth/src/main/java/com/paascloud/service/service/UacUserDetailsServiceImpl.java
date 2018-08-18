/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserDetailsServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.service.service;

import com.google.common.collect.Lists;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.security.core.PcSocialUser;
import com.paascloud.security.core.SecurityUser;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac user details service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

	@Resource
	private UacAuthUserFeignApi authUserFeignApi;

	/**
	 * Load user by username user details.
	 *
	 * @param username the username
	 *
	 * @return the user details
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Wrapper<AuthUserDTO> result = authUserFeignApi.getAuthUserDTO(username);
		if (result == null) {
			throw new BadCredentialsException("用户微服务故障, 请稍候重试");
		}
		AuthUserDTO authUserDTO = result.getResult();

		List<String> authUrlList = authUserDTO.getAuthUrlList();

		List<GrantedAuthority> authorities = Lists.newArrayList();
		for (String url : authUrlList) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(url);
			authorities.add(grantedAuthority);
		}

		return new SecurityUser(authUserDTO.getUserId(), authUserDTO.getLoginName(), authUserDTO.getLoginPwd(), authUserDTO.getNickName(), authUserDTO.getGroupId(), authUserDTO.getGroupName(), authUserDTO.getStatus(), authorities);
	}

	@Override
	public SocialUserDetails loadUserByUserId(final String loginName) throws UsernameNotFoundException {
		log.info("社交登录, 根据 userId 查询社交用户 userId={}", loginName);
		Wrapper<AuthUserDTO> result = authUserFeignApi.getAuthUserDTO(loginName);
		if (result == null) {
			throw new UacBizException(ErrorCodeEnum.GL99990002);
		}
		AuthUserDTO authUserDTO = result.getResult();

		List<String> authUrlList = authUserDTO.getAuthUrlList();

		List<GrantedAuthority> authorities = Lists.newArrayList();
		for (String url : authUrlList) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(url);
			authorities.add(grantedAuthority);
		}

		return new PcSocialUser(authUserDTO.getUserId(), authUserDTO.getLoginName(), authUserDTO.getLoginPwd(), authUserDTO.getNickName(), authUserDTO.getGroupId(), authUserDTO.getGroupName(), authUserDTO.getStatus(), authorities);

	}
}
