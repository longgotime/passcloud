/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;


import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.dto.role.*;
import com.paascloud.provider.model.service.UacRoleFeignApi;
import com.paascloud.provider.model.vo.menu.BindAuthVo;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


/**
 * 角色管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacRoleFeignHystrix implements UacRoleFeignApi {

	@Override
	public Wrapper<PageInfo<RoleVo>> queryUacRoleListWithPage(RoleDto role) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper deleteUacRoleById(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper batchDeleteByIdList(List<Long> deleteIdList) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper modifyUacRoleStatusById(ModifyStatusDto modifyStatusDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper save(RoleDto role) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper bindAction(RoleBindActionDto roleBindActionDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper bindMenu(RoleBindMenuDto roleBindMenuDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper bindUser(RoleBindUserReqDto roleBindUserReqDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<RoleBindUserDto> getBindUser(GetBindUserDto getBindUserDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<RoleVo> queryRoleInfo(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkUacRoleCode(CheckRoleCodeDto checkRoleCodeDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<BindAuthVo> getActionTreeByRoleId(Long roleId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<BindAuthVo> getMenuTreeByRoleId(Long roleId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Set<String>> listAuthorityUrl(Set<String> currentAuthorityList) {
		return WrapMapper.error();
	}
}
