/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;


import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.dto.role.*;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.provider.model.service.UacRoleFeignApi;
import com.paascloud.provider.model.vo.menu.BindAuthVo;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 角色管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacRoleFeignApiHystrix implements UacRoleFeignApi {

	@Override
	public Wrapper<PageInfo<RoleVo>> queryUacRoleListWithPage(RoleDto role) {
		return null;
	}

	@Override
	public Wrapper deleteUacRoleById(Long id) {
		return null;
	}

	@Override
	public Wrapper batchDeleteByIdList(List<Long> deleteIdList) {
		return null;
	}

	@Override
	public Wrapper modifyUacRoleStatusById(ModifyStatusDto modifyStatusDto) {
		return null;
	}

	@Override
	public Wrapper save(RoleDto role) {
		return null;
	}

	@Override
	public Wrapper bindAction(RoleBindActionDto roleBindActionDto) {
		return null;
	}

	@Override
	public Wrapper bindMenu(RoleBindMenuDto roleBindMenuDto) {
		return null;
	}

	@Override
	public Wrapper bindUser(RoleBindUserReqDto roleBindUserReqDto) {
		return null;
	}

	@Override
	public Wrapper<RoleBindUserDto> getBindUser(Long roleId) {
		return null;
	}

	@Override
	public Wrapper<RoleVo> queryRoleInfo(Long id) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkUacRoleCode(CheckRoleCodeDto checkRoleCodeDto) {
		return null;
	}

	@Override
	public Wrapper<BindAuthVo> getActionTreeByRoleId(Long roleId) {
		return null;
	}

	@Override
	public Wrapper<BindAuthVo> getMenuTreeByRoleId(Long roleId) {
		return null;
	}
}
