/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;


import com.paascloud.provider.model.dto.group.*;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.service.UacGroupFeignApi;
import com.paascloud.provider.model.vo.group.GroupVo;
import com.paascloud.provider.model.vo.group.GroupZtreeVo;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacGroupFeignHystrix implements UacGroupFeignApi {

	@Override
	public Wrapper deleteGroupById(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper modifyGroupStatus(IdStatusDto idStatusDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<MenuVo>> getTreeByUserId(Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper editGroup(GroupDto group) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<GroupVo> getEditGroupPageInfo(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById(Long groupId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkGroupName(CheckGroupNameDto checkGroupNameDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkGroupCode(CheckGroupCodeDto checkGroupCodeDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<Map<String, String>>> queryGroupType() {
		return WrapMapper.error();
	}

	@Override
	public Wrapper bindUser4Group(GroupBindUserReqDto groupBindUserReqDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(Long groupId, Long userId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<MenuVo>> getGroupTreeByUserId(Long userId) {
		return null;
	}
}