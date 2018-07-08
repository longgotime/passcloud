/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.google.common.collect.Lists;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.enums.LogTypeEnum;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.UacAction;
import com.paascloud.provider.model.domain.UacLog;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.provider.service.UacActionService;
import com.paascloud.provider.service.UacLogService;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.provider.service.UacUserTokenService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * The class Uac action main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - UacAuthUserFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacAuthUserFeignClient extends BaseFeignClient implements UacAuthUserFeignApi {

	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private UacUserService uacUserService;
	@Resource
	private UacUserTokenService uacUserTokenService;
	@Resource
	private UacLogService uacLogService;
	@Resource
	private UacActionService uacActionService;


	@Override
	public Wrapper<AuthUserDTO> getAuthUserDTO(@PathVariable("loginName") String loginName) {

		UacUser user = uacUserService.findByLoginName(loginName);
		if (user == null) {
			throw new BadCredentialsException("用户名不存在或者密码错误");
		}
		user = uacUserService.findUserInfoByUserId(user.getId());

		List<UacAction> ownAuthList = uacActionService.getOwnActionListByUserId(user.getId());
		List<String> authList = Lists.newArrayList();
		for (UacAction action : ownAuthList) {
			authList.add(action.getUrl());
		}

		AuthUserDTO authUserDTO = new AuthUserDTO();

		authUserDTO.setUserId(user.getId());
		authUserDTO.setGroupId(user.getGroupId());
		authUserDTO.setGroupName(user.getGroupName());
		authUserDTO.setLoginName(user.getLoginName());
		authUserDTO.setNickName(user.getUserName());
		authUserDTO.setStatus(user.getStatus());
		authUserDTO.setLoginPwd(user.getLoginPwd());

		authUserDTO.setAuthUrlList(authList);
		logger.info("查询用户成功 user={}", authUserDTO);
		return WrapMapper.ok(authUserDTO);
	}

	@Override
	public void handlerLoginData(@RequestBody HandlerLoginDTO handlerLoginDTO) {

		String accessToken = handlerLoginDTO.getAccessToken();
		String refreshToken = handlerLoginDTO.getRefreshToken();
		String browser = handlerLoginDTO.getBrowser();
		String os = handlerLoginDTO.getOs();
		AuthUserDTO principal = handlerLoginDTO.getPrincipal();
		String remoteAddr = handlerLoginDTO.getRemoteAddr();
		String remoteLocation = handlerLoginDTO.getRemoteLocation();
		String requestURI = handlerLoginDTO.getRequestURI();

		UacUser uacUser = new UacUser();
		Long userId = principal.getUserId();
		uacUser.setLastLoginIp(remoteAddr);
		uacUser.setId(userId);
		uacUser.setLastLoginTime(new Date());
		uacUser.setLastLoginLocation(remoteLocation);
		LoginAuthDto loginAuthDto = new LoginAuthDto(userId, principal.getLoginName(), principal.getNickName(), principal.getGroupId(), principal.getGroupName());
		// 记录token日志
		uacUserTokenService.saveUserToken(accessToken, refreshToken, loginAuthDto, os, browser, remoteAddr, remoteLocation);
		// 记录最后登录信息
		taskExecutor.execute(() -> uacUserService.updateUser(uacUser));
		// 记录操作日志

		UacLog log = new UacLog();
		log.setGroupId(principal.getGroupId());
		log.setGroupName(principal.getGroupName());
		log.setIp(remoteAddr);
		log.setLocation(remoteLocation);
		log.setOs(os);
		log.setBrowser(browser);
		log.setRequestUrl(requestURI);
		log.setLogType(LogTypeEnum.LOGIN_LOG.getType());
		log.setLogName(LogTypeEnum.LOGIN_LOG.getName());

		taskExecutor.execute(() -> uacLogService.saveLog(log, loginAuthDto));

	}
}
