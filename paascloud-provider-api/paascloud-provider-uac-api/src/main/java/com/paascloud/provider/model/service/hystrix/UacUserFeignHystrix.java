/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.IdDTO;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.role.UserBindRoleVo;
import com.paascloud.provider.model.vo.user.UacLogVO;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Uac user feign api hystrix.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 /7/1 上午11:22
 */
@Component
public class UacUserFeignHystrix implements UacUserFeignApi {


	@Override
	public Wrapper<PageInfo> queryUserListWithPage(QueryUserDTO uacUser) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> saveUacUser(SaveUserDTO user) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> modifyUserStatusById(ModifyUserStatusDto modifyUserStatusDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> deleteUserById(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<UserBindRoleVo> getBindRole(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> bindUserRoles(BindUserRolesDto bindUserRolesDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<UserMenuDto>> queryUserMenuDtoData(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> bindUserMenus(BindUserMenusDto bindUserMenusDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<UserVo> getUacUserById(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<UserVo> resetLoginPwd(IdDTO idDTO) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<UserVo> queryUserInfo(String loginName) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkLoginName(CheckLoginNameDto checkLoginNameDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkEmail(CheckEmailDto checkEmailDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkUserName(CheckUserNameDto checkUserNameDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkUserPhone(CheckUserPhoneDto checkUserPhoneDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkNewPassword(CheckNewPasswordDto checkNewPasswordDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> modifyUserEmail(String email, String emailCode, LoginAuthDto loginAuthDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<MenuVo>> getOwnAuthTree(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Integer> modifyUserPwd(UserModifyPwdDto userModifyPwdDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper registerUser(UserRegisterDto registerDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<PageInfo<UacLogVO>> queryUserLogListWithPage(Integer pageNum, Integer pageSize, String loginName) {
		return WrapMapper.error();
	}
}
