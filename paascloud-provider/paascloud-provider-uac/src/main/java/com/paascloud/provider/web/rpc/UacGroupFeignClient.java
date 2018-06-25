/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;


import com.paascloud.provider.model.dto.group.*;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.service.UacGroupFeignApi;
import com.paascloud.provider.model.vo.group.GroupVo;
import com.paascloud.provider.model.vo.group.GroupZtreeVo;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacGroupFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupFeignClient implements UacGroupFeignApi {


	@Override
	public Wrapper deleteGroupById(Long id) {
		return null;
	}

	@Override
	public Wrapper modifyGroupStatus(IdStatusDto idStatusDto) {
		return null;
	}

	@Override
	public Wrapper<List<MenuVo>> getTree() {
		return null;
	}

	@Override
	public Wrapper editGroup(GroupDto group) {
		return null;
	}

	@Override
	public Wrapper<GroupVo> getEditGroupPageInfo(Long id) {
		return null;
	}

	@Override
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById() {
		return null;
	}

	@Override
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById(Long groupId) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkGroupName(CheckGroupNameDto checkGroupNameDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkGroupCode(CheckGroupCodeDto checkGroupCodeDto) {
		return null;
	}

	@Override
	public Wrapper<List<Map<String, String>>> queryGroupType() {
		return null;
	}

	@Override
	public Wrapper bindUser4Role(GroupBindUserReqDto groupBindUserReqDto) {
		return null;
	}

	@Override
	public Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(Long groupId) {
		return null;
	}
}