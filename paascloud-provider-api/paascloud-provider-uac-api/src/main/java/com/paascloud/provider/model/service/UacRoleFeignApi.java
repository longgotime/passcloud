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

package com.paascloud.provider.model.service;


import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.dto.role.*;
import com.paascloud.provider.model.service.hystrix.UacRoleFeignHystrix;
import com.paascloud.provider.model.vo.menu.BindAuthVo;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;


/**
 * 角色管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacRoleFeignHystrix.class)
public interface UacRoleFeignApi{

	/**
	 * 分页查询角色信息.
	 *
	 * @param role the role
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/queryRoleListWithPage")
	Wrapper<PageInfo<RoleVo>> queryUacRoleListWithPage(@ApiParam(name = "role", value = "角色信息") @RequestBody RoleDto role);

	/**
	 * 删除角色信息.
	 *
	 * @param id the id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/deleteRoleById/{id}")
	Wrapper deleteUacRoleById(@ApiParam(name = "id", value = "角色id") @PathVariable("id") Long id);

	/**
	 * 批量删除角色.
	 *
	 * @param deleteIdList the delete id list
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/batchDeleteByIdList")
	Wrapper batchDeleteByIdList(@ApiParam(name = "deleteIdList", value = "角色Id") @RequestBody List<Long> deleteIdList);

	/**
	 * 修改角色状态.
	 *
	 * @param modifyStatusDto the modify status dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/modifyRoleStatusById")
	Wrapper modifyUacRoleStatusById(@RequestBody ModifyStatusDto modifyStatusDto);


	/**
	 * 保存用户.
	 *
	 * @param role the role
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/save")
	Wrapper save(@ApiParam(name = "role", value = "角色信息") @RequestBody RoleDto role);

	/**
	 * 角色分配权限.
	 *
	 * @param roleBindActionDto the role bind action dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/bindAction")
	Wrapper bindAction(@ApiParam(name = "bindAuth", value = "权限信息") @RequestBody RoleBindActionDto roleBindActionDto);

	/**
	 * 角色分配权限.
	 *
	 * @param roleBindMenuDto the role bind menu dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/bindMenu")
	Wrapper bindMenu(@ApiParam(name = "bindAuth", value = "权限信息") @RequestBody RoleBindMenuDto roleBindMenuDto);


	/**
	 * 角色绑定用户.
	 *
	 * @param roleBindUserReqDto the role bind user req dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/bindUser")
	Wrapper bindUser(@ApiParam(name = "uacRoleBindUserReqDto", value = "角色绑定用户") @RequestBody RoleBindUserReqDto roleBindUserReqDto);

	/**
	 * 获取角色绑定用户页面数据.
	 *
	 * @param getBindUserDto the get bind user dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/getBindUser")
	Wrapper<RoleBindUserDto> getBindUser(@RequestBody GetBindUserDto getBindUserDto);


	/**
	 * 查看角色信息.
	 *
	 * @param id the id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/queryRoleInfoById/{id}")
	Wrapper<RoleVo> queryRoleInfo(@PathVariable("id") Long id);

	/**
	 * 验证角色编码是否存在.
	 *
	 * @param checkRoleCodeDto the check role code dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/role/checkRoleCode")
	@ApiOperation(httpMethod = "POST", value = "验证角色编码是否存在")
	Wrapper<Boolean> checkUacRoleCode(@ApiParam(name = "roleCode", value = "角色编码") @RequestBody CheckRoleCodeDto checkRoleCodeDto);

	/**
	 * 获取权限树
	 *
	 * @param roleId the role id
	 * @return the auth tree by role id
	 */
	@PostMapping(value = "/uac/role/getActionTreeByRoleId/{roleId}")
	Wrapper<BindAuthVo> getActionTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId);

	/**
	 * 获取菜单树.
	 *
	 * @param roleId the role id
	 * @return the menu tree by role id
	 */
	@PostMapping(value = "/uac/role/getMenuTreeByRoleId/{roleId}")
	Wrapper<BindAuthVo> getMenuTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable("roleId") Long roleId);

	/**
	 * List authority url set.
	 *
	 * @param currentAuthorityList the current authority list
	 * @return the set
	 */
	@PostMapping(value = "/uac/role/listAuthorityUrl")
	Wrapper<Set<String>> listAuthorityUrl(@RequestBody Set<String> currentAuthorityList);
}
