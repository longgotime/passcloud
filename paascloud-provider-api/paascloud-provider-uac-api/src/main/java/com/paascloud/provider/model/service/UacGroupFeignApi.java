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

package com.paascloud.provider.model.service;


import com.paascloud.provider.model.dto.group.*;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.service.hystrix.UacGroupFeignHystrix;
import com.paascloud.provider.model.vo.group.GroupVo;
import com.paascloud.provider.model.vo.group.GroupZtreeVo;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacGroupFeignHystrix.class)
public interface UacGroupFeignApi{

	/**
	 * 根据id删除组织
	 *
	 * @param id the id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/deleteById/{id}")
	Wrapper deleteGroupById(@ApiParam(name = "id", value = "组织id") @PathVariable("id") Long id);

	/**
	 * 根据id修改组织状态
	 *
	 * @param idStatusDto the id status dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/modifyStatus")
	Wrapper modifyGroupStatus(@ApiParam(name = "modifyGroupStatus", value = "修改状态") @RequestBody IdStatusDto idStatusDto);


	/**
	 * 获取主页面数据
	 *
	 * @param userId the user id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/getTree/{userId}")
	Wrapper<List<MenuVo>> getTreeByUserId(@PathVariable("userId") Long userId);

	/**
	 * 编辑组织
	 *
	 * @param group the group
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/save")
	Wrapper editGroup(@ApiParam(name = "group", value = "组织信息") @RequestBody GroupDto group);


	/**
	 * 获取编辑页面数据
	 *
	 * @param id the id
	 * @return the edit group page info
	 */
	@PostMapping(value = "/uac/group/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "获取编辑页面数据")
	Wrapper<GroupVo> getEditGroupPageInfo(@ApiParam(name = "id", value = "组织id") @PathVariable("id") Long id);

	/**
	 * 通过组织ID查询组织树
	 *
	 * @param groupId the group id
	 * @return the group tree by id
	 */
	@PostMapping(value = "/uac/group/getGroupTree/{groupId}")
	Wrapper<List<GroupZtreeVo>> getGroupTreeById(@ApiParam(name = "groupId", value = "通过组织ID查询组织列表") @PathVariable("groupId") Long groupId);

	/**
	 * Check group name with edit wrapper.
	 *
	 * @param checkGroupNameDto the check group name dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/checkGroupName")
	Wrapper<Boolean> checkGroupName(@ApiParam(name = "checkGroupName", value = "组织名称") @RequestBody CheckGroupNameDto checkGroupNameDto);

	/**
	 * 修改时验证组织编码
	 *
	 * @param checkGroupCodeDto the check group code dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/checkGroupCode")
	Wrapper<Boolean> checkGroupCode(@ApiParam(name = "checkGroupCode", value = "组织相关信息") @RequestBody CheckGroupCodeDto checkGroupCodeDto);

	/**
	 * 查询组织类型
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/queryGroupType")
	Wrapper<List<Map<String, String>>> queryGroupType();

	/**
	 * 组织绑定用户
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/group/bindUser")
	Wrapper bindUser4Group(@ApiParam(name = "uacGroupBindUserReqDto", value = "组织绑定用户") @RequestBody GroupBindUserReqDto groupBindUserReqDto);

	/**
	 * 组织绑定用户页面数据
	 *
	 * @param groupId the group id
	 * @param userId  the user id
	 * @return the group bind user page info
	 */
	@PostMapping(value = "/uac/group/getBindUser/{groupId}/{userId}")
	Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(@ApiParam(name = "groupId", value = "组织id") @PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId);

	/**
	 * Gets group tree by user id.
	 *
	 * @param userId the user id
	 * @return the group tree by user id
	 */
	@PostMapping(value = "/uac/group/getGroupTreeByUserId/{userId}")
	Wrapper<List<MenuVo>> getGroupTreeByUserId(@PathVariable("userId") Long userId);
}