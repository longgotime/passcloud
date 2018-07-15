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

package com.paascloud.operate.controller.uac;


import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.annotation.ValidateAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.dto.role.*;
import com.paascloud.provider.model.service.UacRoleFeignApi;
import com.paascloud.provider.model.vo.menu.BindAuthVo;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 角色管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacRoleController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacRoleController extends BaseController{

    @Resource
    private UacRoleFeignApi uacRoleFeignApi;


    /**
     * 分页查询角色信息.
     *
     * @param role the role
     *
     * @return the wrapper
     */
    @PostMapping(value = "/queryRoleListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询角色列表")
    public Wrapper<PageInfo<RoleVo>> queryUacRoleListWithPage(@RequestBody RoleDto role) {
        logger.info("查询角色列表roleQuery={}", role);
        return uacRoleFeignApi.queryUacRoleListWithPage(role);
    }

    /**
     * 删除角色信息.
     *
     * @param id the id
     *
     * @return the wrapper
     */
    @LogAnnotation
    @PostMapping(value = "/deleteRoleById/{id}")
    @ApiOperation(httpMethod = "POST", value = "删除角色")
    public Wrapper deleteUacRoleById(@PathVariable Long id) {
        return uacRoleFeignApi.deleteUacRoleById(id);
    }

    /**
     * 批量删除角色.
     *
     * @param deleteIdList the delete id list
     *
     * @return the wrapper
     */
    @LogAnnotation
    @PostMapping(value = "/batchDeleteByIdList")
    @ApiOperation(httpMethod = "POST", value = "批量删除角色")
    public Wrapper batchDeleteByIdList(@RequestBody List<Long> deleteIdList) {
        logger.info("批量删除角色 idList={}", deleteIdList);
        return uacRoleFeignApi.batchDeleteByIdList(deleteIdList);
    }

    /**
     * 修改角色状态.
     *
     * @param modifyStatusDto the modify status dto
     *
     * @return the wrapper
     */
    @LogAnnotation
    @PostMapping(value = "/modifyRoleStatusById")
    @ApiOperation(httpMethod = "POST", value = "根据角色Id修改角色状态")
    public Wrapper modifyUacRoleStatusById(@RequestBody ModifyStatusDto modifyStatusDto) {
        logger.info("根据角色Id修改角色状态 modifyStatusDto={}", modifyStatusDto);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        modifyStatusDto.setLoginAuthDto(loginAuthDto);
        return uacRoleFeignApi.modifyUacRoleStatusById(modifyStatusDto);
    }

    /**
     * 保存用户.
     *
     * @param role the role
     *
     * @return the wrapper
     */
    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "新增角色")
    @ValidateAnnotation
    @LogAnnotation
    public Wrapper save(@RequestBody RoleDto role) {
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        role.setLoginAuthDto(loginAuthDto);
        return uacRoleFeignApi.save(role);
    }


    /**
     * 角色分配权限.
     *
     * @param roleBindActionDto the role bind action dto
     *
     * @return the wrapper
     */
    @PostMapping(value = "/bindAction")
    @ApiOperation(httpMethod = "POST", value = "角色分配权限")
    @LogAnnotation
    public Wrapper bindAction(@RequestBody RoleBindActionDto roleBindActionDto) {
        logger.info("角色分配权限. roleBindActionDto= {}", roleBindActionDto);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        roleBindActionDto.setLoginAuthDto(loginAuthDto);
        return uacRoleFeignApi.bindAction(roleBindActionDto);
    }

    /**
     * 角色分配权限.
     *
     * @param roleBindMenuDto the role bind menu dto
     *
     * @return the wrapper
     */
    @PostMapping(value = "/bindMenu")
    @ApiOperation(httpMethod = "POST", value = "角色分配权限")
    @LogAnnotation
    public Wrapper bindMenu(@RequestBody RoleBindMenuDto roleBindMenuDto) {
        logger.info("角色分配权限. roleBindMenuDto= {}", roleBindMenuDto);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        roleBindMenuDto.setLoginAuthDto(loginAuthDto);
        return uacRoleFeignApi.bindMenu(roleBindMenuDto);
    }

    /**
     * 角色绑定用户.
     *
     * @param roleBindUserReqDto the role bind user req dto
     *
     * @return the wrapper
     */
    @LogAnnotation
    @PostMapping(value = "/bindUser")
    @ApiOperation(httpMethod = "POST", value = "角色绑定用户")
    public Wrapper bindUser(@RequestBody RoleBindUserReqDto roleBindUserReqDto) {
        logger.info("roleBindUser={}", roleBindUserReqDto);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        roleBindUserReqDto.setLoginAuthDto(loginAuthDto);
        return uacRoleFeignApi.bindUser(roleBindUserReqDto);
    }
    /**
     * 获取角色绑定用户页面数据.
     *
     * @param roleId the role id
     *
     * @return the wrapper
     */
    @PostMapping(value = "/getBindUser/{roleId}")
    @ApiOperation(httpMethod = "POST", value = "获取角色绑定用户页面数据")
    public Wrapper<RoleBindUserDto> getBindUser(@PathVariable Long roleId) {
        logger.info("获取角色绑定用户页面数据. roleId={}", roleId);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        GetBindUserDto getBindUserDto = new GetBindUserDto(roleId, loginAuthDto);
        return uacRoleFeignApi.getBindUser(getBindUserDto);
    }
    /**
     * 查看角色信息.
     *
     * @param id the id
     *
     * @return the wrapper
     */
    @PostMapping(value = "/queryRoleInfoById/{id}")
    @ApiOperation(httpMethod = "POST", value = "查看角色信息")
    public Wrapper<RoleVo> queryRoleInfo(@PathVariable Long id) {
        return uacRoleFeignApi.queryRoleInfo(id);
    }

    /**
     * 验证角色编码是否存在.
     *
     * @param checkRoleCodeDto the check role code dto
     *
     * @return the wrapper
     */
    @PostMapping(value = "/checkRoleCode")
    @ApiOperation(httpMethod = "POST", value = "验证角色编码是否存在")
    public Wrapper<Boolean> checkUacRoleCode(@RequestBody CheckRoleCodeDto checkRoleCodeDto) {
        logger.info("校验角色编码唯一性 checkRoleCodeDto={}", checkRoleCodeDto);
        return uacRoleFeignApi.checkUacRoleCode(checkRoleCodeDto);
    }

    /**
     * 获取权限树
     *
     * @param roleId the role id
     *
     * @return the auth tree by role id
     */
    @PostMapping(value = "/getActionTreeByRoleId/{roleId}")
    @ApiOperation(httpMethod = "POST", value = "获取权限树")
    public Wrapper<BindAuthVo> getActionTreeByRoleId(@PathVariable Long roleId) {
        logger.info("roleId={}", roleId);
        return uacRoleFeignApi.getActionTreeByRoleId(roleId);
    }

    /**
     * 获取菜单树.
     *
     * @param roleId the role id
     *
     * @return the menu tree by role id
     */
    @PostMapping(value = "/getMenuTreeByRoleId/{roleId}")
    @ApiOperation(httpMethod = "POST", value = "获取菜单树")
    public Wrapper<BindAuthVo> getMenuTreeByRoleId(@PathVariable Long roleId) {
        logger.info("roleId={}", roleId);
        return uacRoleFeignApi.getMenuTreeByRoleId(roleId);
    }
}
