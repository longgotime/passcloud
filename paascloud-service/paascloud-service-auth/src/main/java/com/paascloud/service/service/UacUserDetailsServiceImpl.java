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
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.security.core.SecurityUser;
import com.paascloud.wrapper.Wrapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac user details service.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacUserDetailsServiceImpl implements UserDetailsService {

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
}
