/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.IdDTO;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.service.hystrix.UacUserFeignHystrix;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.role.UserBindRoleVo;
import com.paascloud.provider.model.vo.user.UacLogVO;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacUserFeignHystrix.class)
public interface UacUserFeignApi {

    /**
     * 查询角色列表.
     *
     * @param uacUser the uac user
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/queryListWithPage")
    Wrapper<PageInfo> queryUserListWithPage(@ApiParam(name = "role", value = "角色信息") @RequestBody QueryUserDTO uacUser);

    /**
     * 新增/修改用户
     *
     * @param user the user
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/save")
    Wrapper<Integer> saveUacUser(@ApiParam(name = "user", value = "新增用户Dto") @RequestBody SaveUserDTO user);


    /**
     * 根据Id修改用户状态.
     *
     * @param modifyUserStatusDto the modify user status dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/modifyUserStatusById")
    Wrapper<Integer> modifyUserStatusById(@ApiParam(name = "modifyUserStatusDto", value = "用户禁用/启用Dto") @RequestBody ModifyUserStatusDto modifyUserStatusDto);

    /**
     * 通过Id删除用户.
     *
     * @param userId the user id
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/deleteUserById/{userId}")
    Wrapper<Integer> deleteUserById(@ApiParam(name = "userId", value = "用户ID") @PathVariable("id") Long userId);

    /**
     * 获取用户绑定角色页面数据.
     *
     * @param userId the user id
     * @return the bind role
     */
    @PostMapping(value = "/uac/user/getBindRole/{userId}")
    Wrapper<UserBindRoleVo> getBindRole(@ApiParam(name = "userId", value = "角色id") @PathVariable("userId") Long userId);

    /**
     * 用户绑定角色.
     *
     * @param bindUserRolesDto the bind user roles dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/bindRole")
    Wrapper<Integer> bindUserRoles(@ApiParam(name = "bindUserRolesDto", value = "用户绑定角色Dto") @RequestBody BindUserRolesDto bindUserRolesDto);

    /**
     * 查询用户常用功能数据.
     *
     * @param userId the user id
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/queryUserMenuDtoData/{userId}")
    Wrapper<List<UserMenuDto>> queryUserMenuDtoData(@PathVariable("userId") Long userId);

    /**
     * 绑定用户常用菜单.
     *
     * @param bindUserMenusDto the bind user menus dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/bindUserMenus")
    Wrapper<Integer> bindUserMenus(@ApiParam(name = "bindUserMenusDto", value = "绑定用户常用菜单Dto") @RequestBody BindUserMenusDto bindUserMenusDto);

    /**
     * 根据用户Id查询用户信息.
     *
     * @param userId the user id
     * @return the uac user by id
     */
    @PostMapping(value = "/uac/user/getUacUserById/{userId}")
    Wrapper<UserVo> getUacUserById(@PathVariable("userId") Long userId);

    /**
     * 根据用户Id重置密码.
     *
     * @param idDTO the id dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/resetLoginPwd")
    @ApiOperation(httpMethod = "POST", value = "根据用户Id重置密码")
    Wrapper<UserVo> resetLoginPwd(@RequestBody IdDTO idDTO);

    /**
     * 根据userId查询用户详细信息（连表查询）.
     *
     * @param loginName the login name
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/queryUserInfo/{loginName}")
    @ApiOperation(httpMethod = "POST", value = "根据userId查询用户详细信息")
    Wrapper<UserVo> queryUserInfo(@PathVariable("loginName") String loginName);


    /**
     * 校验登录名唯一性.
     *
     * @param checkLoginNameDto the check login name dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/checkLoginName")
    Wrapper<Boolean> checkLoginName(@ApiParam(name = "loginName", value = "登录名") @RequestBody CheckLoginNameDto checkLoginNameDto);

    /**
     * 校验登录名唯一性.
     *
     * @param checkEmailDto the check email dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/checkEmail")
    Wrapper<Boolean> checkEmail(@RequestBody CheckEmailDto checkEmailDto);


    /**
     * 校验真实姓名唯一性.
     *
     * @param checkUserNameDto the check user name dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/checkUserName")
    Wrapper<Boolean> checkUserName(@ApiParam(name = "checkUserNameDto", value = "校验真实姓名唯一性Dto") @RequestBody CheckUserNameDto checkUserNameDto);


    /**
     * 校验用户电话号码唯一性.
     *
     * @param checkUserPhoneDto the check user phone dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/checkUserPhone")
    Wrapper<Boolean> checkUserPhone(@ApiParam(name = "checkUserPhoneDto", value = "校验用户电话号码唯一性Dto") @RequestBody CheckUserPhoneDto checkUserPhoneDto);


    /**
     * 校验新密码是否与原始密码相同.
     *
     * @param checkNewPasswordDto 修改密码实体
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/checkNewPassword")
    Wrapper<Boolean> checkNewPassword(@ApiParam(name = "checkNewPasswordDto", value = "校验新密码是否与原始密码相同Dto") @RequestBody CheckNewPasswordDto checkNewPasswordDto);

    /**
     * 修改用户邮箱
     *
     * @param email        the email
     * @param emailCode    the email code
     * @param loginAuthDto the login auth dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/modifyUserEmail")
    Wrapper<Integer> modifyUserEmail(@RequestParam("email") String email, @RequestParam("emailCode") String emailCode, @RequestParam("emailCode") LoginAuthDto loginAuthDto);

    /**
     * 获取已有权限树
     *
     * @param userId the user id
     * @return the auth tree by role id
     */
    @PostMapping(value = "/uac/user/getOwnAuthTree/{userId}")
    Wrapper<List<MenuVo>> getOwnAuthTree(@PathVariable("userId") Long userId);

    /**
     * 用户修改密码
     *
     * @param userModifyPwdDto the user modify pwd dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/modifyUserPwd")
    Wrapper<Integer> modifyUserPwd(@ApiParam(name = "userModifyPwdDto", value = "用户修改密码Dto") @RequestBody UserModifyPwdDto userModifyPwdDto);


    /**
     * 注册
     *
     * @param registerDto the register dto
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/registerUser")
    Wrapper registerUser(@ApiParam(name = "registerDto", value = "用户注册Dto") @RequestBody UserRegisterDto registerDto);

    /**
     * 查询用户操作日志.
     *
     * @param pageNum   the page num
     * @param pageSize  the page size
     * @param loginName the login name
     * @return the wrapper
     */
    @PostMapping(value = "/uac/user/queryUserLogListWithPage")
    Wrapper<PageInfo<UacLogVO>> queryUserLogListWithPage(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "loginName") String loginName);
}