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

package com.paascloud.operate.controller.uac;


import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.group.*;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.enums.UacGroupTypeEnum;
import com.paascloud.provider.model.service.UacGroupFeignApi;
import com.paascloud.provider.model.vo.group.GroupVo;
import com.paascloud.provider.model.vo.group.GroupZtreeVo;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupController extends BaseController{

	@Resource
	private UacGroupFeignApi uacGroupFeignApi;

	/**
	 * 根据id删除组织
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id删除组织")
	public Wrapper deleteGroupById(@PathVariable Long id) {
		return uacGroupFeignApi.deleteGroupById(id);
	}

	/**
	 * 根据id修改组织状态
	 *
	 * @param idStatusDto the id status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据id修改组织状态")
	public Wrapper modifyGroupStatus(@RequestBody IdStatusDto idStatusDto) {
		logger.info("根据id修改组织状态 idStatusDto={}", idStatusDto);
		idStatusDto.setLoginAuthDto(getLoginAuthDto());
		return uacGroupFeignApi.modifyGroupStatus(idStatusDto);
	}

	/**
	 * 获取主页面数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<List<MenuVo>> getTree() {
        return uacGroupFeignApi.getGroupTreeByUserId(getLoginAuthDto().getUserId());
	}

	/**
	 * 编辑组织
	 *
	 * @param group the group
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "修改组织信息")
	public Wrapper editGroup(@RequestBody GroupDto group) {
		group.setLoginAuthDto(getLoginAuthDto());

		return uacGroupFeignApi.editGroup(group);
	}

	/**
	 * 获取编辑页面数据
	 *
	 * @param id the id
	 *
	 * @return the edit group page info
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "获取编辑页面数据")
	public Wrapper<GroupVo> getEditGroupPageInfo(@PathVariable Long id) {
		return uacGroupFeignApi.getEditGroupPageInfo(id);
	}


	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTree")
	@ApiOperation(httpMethod = "POST", value = "根据当前登录人查询组织列表")
	public Wrapper<List<GroupZtreeVo>> getGroupTree() {
		logger.info("根据当前登录人查询组织列表");
		return uacGroupFeignApi.getGroupTreeById(getLoginAuthDto().getGroupId());
	}

	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTreeById")
	@ApiOperation(httpMethod = "POST", value = "根据当前登录人查询组织列表")
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById(@PathVariable Long groupId) {
		logger.info("通过组织ID查询组织列表 groupId={}", groupId);
		return uacGroupFeignApi.getGroupTreeById(groupId);
	}


	/**
	 * Check group name with edit wrapper.
	 *
	 * @param checkGroupNameDto the check group name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupName")
	@ApiOperation(httpMethod = "POST", value = "校验组织名称唯一性")
	public Wrapper<Boolean> checkGroupName(@RequestBody CheckGroupNameDto checkGroupNameDto) {
		logger.info("校验组织名称唯一性 checkGroupNameDto={}", checkGroupNameDto);
		return uacGroupFeignApi.checkGroupName(checkGroupNameDto);
	}

	/**
	 * 修改时验证组织编码
	 *
	 * @param checkGroupCodeDto the check group code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupCode")
	@ApiOperation(httpMethod = "POST", value = "修改校验组织编码唯一性")
	public Wrapper<Boolean> checkGroupCode(@RequestBody CheckGroupCodeDto checkGroupCodeDto) {
		logger.info("校验组织编码唯一性 checkGroupCodeDto={}", checkGroupCodeDto);
		return uacGroupFeignApi.checkGroupCode(checkGroupCodeDto);
	}


	/**
	 * 查询组织类型
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "queryGroupType")
	@ApiOperation(httpMethod = "POST", value = "查询组织类型")
	public Wrapper<List<Map<String, String>>> queryGroupType() {
		List<Map<String, String>> groupTypeList = UacGroupTypeEnum.getMap2List();
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, groupTypeList);
	}

	/**
	 * 组织绑定用户
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindUser")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "组织绑定用户")
	public Wrapper bindUser4Role(@RequestBody GroupBindUserReqDto groupBindUserReqDto) {
		logger.info("组织绑定用户...  groupBindUserReqDto={}", groupBindUserReqDto);
		groupBindUserReqDto.setLoginAuthDto(getLoginAuthDto());

		return uacGroupFeignApi.bindUser4Group(groupBindUserReqDto);
	}



	/**
	 * 组织绑定用户页面数据
	 *
	 * @param groupId the group id
	 *
	 * @return the group bind user page info
	 */
	@PostMapping(value = "/getBindUser/{groupId}")
	@ApiOperation(httpMethod = "POST", value = "获取组织绑定用户页面数据")
	public Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(@PathVariable Long groupId) {
		logger.info("查询组织绑定用户页面数据 groupId={}", groupId);
		return uacGroupFeignApi.getGroupBindUserPageInfo(groupId, getLoginAuthDto().getUserId());
	}
}