/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.role.UserBindRoleVo;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 用户管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacUserFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserFeignClient implements UacUserFeignApi {


	@Override
	public Wrapper<PageInfo> queryUserListWithPage(UserInfoDto uacUser) {
		return null;
	}

	@Override
	public Wrapper<Integer> addUacUser(UserInfoDto user) {
		return null;
	}

	@Override
	public Wrapper<Integer> modifyUserStatusById(ModifyUserStatusDto modifyUserStatusDto) {
		return null;
	}

	@Override
	public Wrapper<Integer> deleteUserById(Long userId) {
		return null;
	}

	@Override
	public Wrapper<UserBindRoleVo> getBindRole(Long userId) {
		return null;
	}

	@Override
	public Wrapper<Integer> bindUserRoles(BindUserRolesDto bindUserRolesDto) {
		return null;
	}

	@Override
	public Wrapper<List<UserMenuDto>> queryUserMenuDtoData() {
		return null;
	}

	@Override
	public Wrapper<Integer> bindUserMenus(BindUserMenusDto bindUserMenusDto) {
		return null;
	}

	@Override
	public Wrapper<UserVo> getUacUserById(Long userId) {
		return null;
	}

	@Override
	public Wrapper<UserVo> resetLoginPwd(Long userId) {
		return null;
	}

	@Override
	public Wrapper<UserVo> queryUserInfo(String loginName) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkLoginName(CheckLoginNameDto checkLoginNameDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkEmail(CheckEmailDto checkEmailDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkUserName(CheckUserNameDto checkUserNameDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkUserPhone(CheckUserPhoneDto checkUserPhoneDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkNewPassword(CheckNewPasswordDto checkNewPasswordDto) {
		return null;
	}

	@Override
	public Wrapper<Integer> modifyUserEmail(String email, String emailCode) {
		return null;
	}

	@Override
	public Wrapper<List<MenuVo>> getOwnAuthTree() {
		return null;
	}
}
