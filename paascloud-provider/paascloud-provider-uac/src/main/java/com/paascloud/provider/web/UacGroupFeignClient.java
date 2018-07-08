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

package com.paascloud.provider.web;


import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.UacGroup;
import com.paascloud.provider.model.dto.group.*;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.enums.UacGroupTypeEnum;
import com.paascloud.provider.model.service.UacGroupFeignApi;
import com.paascloud.provider.model.vo.group.GroupVo;
import com.paascloud.provider.model.vo.group.GroupZtreeVo;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.service.UacGroupService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 组织管理主页面
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - UacGroupFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupFeignClient extends BaseController implements UacGroupFeignApi {

	@Resource
	private UacGroupService uacGroupService;

	/**
	 * 根据id删除组织
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper deleteGroupById(@PathVariable("id") Long id) {
		return null;
	}

	/**
	 * 根据id修改组织状态
	 *
	 * @param idStatusDto the id status dto
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper modifyGroupStatus(@RequestBody IdStatusDto idStatusDto) {
		logger.info("根据id修改组织状态 idStatusDto={}", idStatusDto);
		UacGroup uacGroup = new UacGroup();
		uacGroup.setId(idStatusDto.getId());
		LoginAuthDto loginAuthDto = idStatusDto.getLoginAuthDto();
		Integer status = idStatusDto.getStatus();
		uacGroup.setStatus(status);
		int result = uacGroupService.updateUacGroupStatusById(idStatusDto, loginAuthDto);
		if (result < 1) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "操作失败");
		} else {
			return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功");
		}
	}

	/**
	 * 获取主页面数据
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper<List<MenuVo>> getTree() {
		// FIXME
		Long userId = null; //super.getLoginAuthDto().getUserId();
		List<MenuVo> tree = uacGroupService.getGroupTreeListByUserId(userId);
		return WrapMapper.ok(tree);
	}


	/**
	 * 编辑组织
	 *
	 * @param group the group
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper editGroup(@RequestBody GroupDto group) {
		LoginAuthDto loginAuthDto = group.getLoginAuthDto();
		UacGroup uacGroup = new UacGroup();
		BeanUtils.copyProperties(group,uacGroup);
		uacGroupService.saveUacGroup(uacGroup, loginAuthDto);
		return WrapMapper.ok();
	}


	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@Override
	public Wrapper<GroupVo> getEditGroupPageInfo(@PathVariable("id") Long id) {
		UacGroup uacGroup = uacGroupService.getById(id);
		GroupVo groupVo = new GroupVo();
		BeanUtils.copyProperties(uacGroup,groupVo);
		return WrapMapper.ok(groupVo);
	}

	/**
	 * 通过组织ID查询组织树
	 *
	 * @return the group tree by id
	 */
	@Override
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById() {
		logger.info("根据当前登录人查询组织列表");
		// FIXME
		LoginAuthDto loginAuthDto = null; //super.getLoginAuthDto();
		Long groupId = loginAuthDto.getGroupId();
		UacGroup uacGroup = uacGroupService.queryById(groupId);
		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(uacGroup.getId());
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@Override
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById(@PathVariable("groupId") Long groupId) {
		logger.info("通过组织ID查询组织列表 groupId={}", groupId);
		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(groupId);
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * Check group name with edit wrapper.
	 *
	 * @param checkGroupNameDto the check group name dto
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper<Boolean> checkGroupName(@RequestBody CheckGroupNameDto checkGroupNameDto) {
		logger.info("校验组织名称唯一性 checkGroupNameDto={}", checkGroupNameDto);

		Long id = checkGroupNameDto.getGroupId();
		String groupName = checkGroupNameDto.getGroupName();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupName", groupName);

		int result = uacGroupService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}


	/**
	 * 修改时验证组织编码
	 *
	 * @param checkGroupCodeDto the check group code dto
	 *
	 * @return the wrapper
	 */
	@Override
	public Wrapper<Boolean> checkGroupCode(@RequestBody CheckGroupCodeDto checkGroupCodeDto) {
		logger.info("校验组织编码唯一性 checkGroupCodeDto={}", checkGroupCodeDto);

		Long id = checkGroupCodeDto.getGroupId();
		String groupCode = checkGroupCodeDto.getGroupCode();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupCode", groupCode);

		int result = uacGroupService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 查询组织类型
	 *
	 * @return the wrapper
	 */
	@Override
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
	@Override
	public Wrapper bindUser4Role(@RequestBody GroupBindUserReqDto groupBindUserReqDto) {
		logger.info("组织绑定用户...  groupBindUserReqDto={}", groupBindUserReqDto);
		LoginAuthDto loginAuthDto = groupBindUserReqDto.getLoginAuthDto();
		uacGroupService.bindUacUser4Group(groupBindUserReqDto, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 组织绑定用户页面数据
	 *
	 * @param groupId the group id
	 *
	 * @return the group bind user page info
	 */
	@Override
	public Wrapper<GroupBindUserDto> getGroupBindUserPageInfo(@PathVariable("groupId") Long groupId) {
		logger.info("查询组织绑定用户页面数据 groupId={}", groupId);
		// FIXME
		LoginAuthDto loginAuthDto = null; //super.getLoginAuthDto();
		Long currentUserId = loginAuthDto.getUserId();
		GroupBindUserDto bindUserDto = uacGroupService.getGroupBindUserDto(groupId, currentUserId);
		return WrapMapper.ok(bindUserDto);
	}
}