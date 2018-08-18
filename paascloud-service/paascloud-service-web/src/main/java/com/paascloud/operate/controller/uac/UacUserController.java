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

package com.paascloud.operate.controller.uac;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.IdDTO;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.annotation.ValidateAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.log.UacLogQueryDTO;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.role.UserBindRoleVo;
import com.paascloud.provider.model.vo.user.UacLogVO;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


/**
 * 用户管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserController extends BaseController{

	@Resource
	private UacUserFeignApi uacUserFeignApi;

	/**
	 * 查询角色列表.
	 *
	 * @param uacUser the uac user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	public Wrapper<PageInfo> queryUserListWithPage(@RequestBody QueryUserDTO uacUser) {
		return uacUserFeignApi.queryUserListWithPage(uacUser);
	}

	/**
	 * 新增用户
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增用户")
	public Wrapper<Integer> saveUacUser(@RequestBody SaveUserDTO user) {
		logger.info(" 新增用户 user={}", user);
		user.setLoginAuthDto(getLoginAuthDto());

		return uacUserFeignApi.saveUacUser(user);
	}


	/**
	 * 根据Id修改用户状态.
	 *
	 * @param modifyUserStatusDto the modify user status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyUserStatusById")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "根据Id修改用户状态")
	public Wrapper<Integer> modifyUserStatusById(@RequestBody ModifyUserStatusDto modifyUserStatusDto) {
		logger.info(" 根据Id修改用户状态 modifyUserStatusDto={}", modifyUserStatusDto);
		modifyUserStatusDto.setLoginAuthDto(getLoginAuthDto());

		return uacUserFeignApi.modifyUserStatusById(modifyUserStatusDto);
	}

	/**
	 * 通过Id删除用户.
	 *
	 * @param userId the user id
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/deleteUserById/{userId}")
	@ApiOperation(httpMethod = "POST", value = "通过Id删除用户")
	public Wrapper<Integer> deleteUserById(@PathVariable Long userId) {
		logger.info(" 通过Id删除用户 userId={}", userId);
        return uacUserFeignApi.deleteUserById(userId);
	}

	/**
	 * 获取用户绑定角色页面数据.
	 *
	 * @param userId the user id
	 *
	 * @return the bind role
	 */
	@PostMapping(value = "/getBindRole/{userId}")
	@ApiOperation(httpMethod = "POST", value = "获取用户绑定角色页面数据")
	public Wrapper<UserBindRoleVo> getBindRole(@PathVariable Long userId) {
		logger.info("获取用户绑定角色页面数据. userId={}", userId);

		Long currentUserId = getLoginAuthDto().getUserId();
		if (Objects.equals(userId, currentUserId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

        return uacUserFeignApi.getBindRole(userId);
	}

	/**
	 * 用户绑定角色.
	 *
	 * @param bindUserRolesDto the bind user roles dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/bindRole")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "用户绑定角色")
	public Wrapper<Integer> bindUserRoles(@RequestBody BindUserRolesDto bindUserRolesDto) {
		logger.info("用户绑定角色 bindUserRolesDto={}", bindUserRolesDto);
		bindUserRolesDto.setLoginAuthDto(getLoginAuthDto());

		return uacUserFeignApi.bindUserRoles(bindUserRolesDto);
	}

	/**
	 * 查询用户常用功能数据.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserMenuDtoData")
	@ApiOperation(httpMethod = "POST", value = "查询用户常用功能数据")
	public Wrapper<List<UserMenuDto>> queryUserMenuDtoData() {
		logger.info("查询用户常用功能数据");
        return uacUserFeignApi.queryUserMenuDtoData(getLoginAuthDto().getUserId());
	}

	/**
	 * 绑定用户常用菜单.
	 *
	 * @param bindUserMenusDto the bind user menus dto
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/bindUserMenus")
	@ApiOperation(httpMethod = "POST", value = "绑定用户常用菜单")
	public Wrapper<Integer> bindUserMenus(@RequestBody BindUserMenusDto bindUserMenusDto) {
		logger.info("绑定用户常用菜单");
		bindUserMenusDto.setLoginAuthDto(getLoginAuthDto());

		return uacUserFeignApi.bindUserMenus(bindUserMenusDto);
	}

	/**
	 * 根据用户Id查询用户信息.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user by id
	 */
	@PostMapping(value = "/getUacUserById/{userId}")
	@ApiOperation(httpMethod = "POST", value = "根据用户Id查询用户信息")
	public Wrapper<UserVo> getUacUserById(@PathVariable Long userId) {
		logger.info("getUacUserById - 根据用户Id查询用户信息. userId={}", userId);
        return uacUserFeignApi.getUacUserById(userId);
	}

	/**
	 * 根据用户Id重置密码.
	 *
	 * @param userId the user id
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/resetLoginPwd/{userId}")
	@ApiOperation(httpMethod = "POST", value = "根据用户Id重置密码")
	public Wrapper<UserVo> resetLoginPwd(@PathVariable Long userId) {
		logger.info("resetLoginPwd - 根据用户Id重置密码. userId={}", userId);
		IdDTO idDTO = new IdDTO(userId, getLoginAuthDto());
		return uacUserFeignApi.resetLoginPwd(idDTO);
	}

	/**
	 * 根据userId查询用户详细信息（连表查询）.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserInfo/{loginName}")
	@ApiOperation(httpMethod = "POST", value = "根据userId查询用户详细信息")
	public Wrapper<UserVo> queryUserInfo(@PathVariable String loginName) {
		logger.info("根据userId查询用户详细信息");
        return uacUserFeignApi.queryUserInfo(loginName);
	}

	/**
	 * 校验登录名唯一性.
	 *
	 * @param checkLoginNameDto the check login name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkLoginName")
	@ApiOperation(httpMethod = "POST", value = "校验登录名唯一性")
	public Wrapper<Boolean> checkLoginName(@RequestBody CheckLoginNameDto checkLoginNameDto) {
		logger.info("校验登录名唯一性 checkLoginNameDto={}", checkLoginNameDto);
        return uacUserFeignApi.checkLoginName(checkLoginNameDto);
	}

	/**
	 * 校验登录名唯一性.
	 *
	 * @param checkEmailDto the check email dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmail")
	@ApiOperation(httpMethod = "POST", value = "校验登录名唯一性")
	public Wrapper<Boolean> checkEmail(@RequestBody CheckEmailDto checkEmailDto) {
		logger.info("校验邮箱唯一性 checkEmailDto={}", checkEmailDto);
        return uacUserFeignApi.checkEmail(checkEmailDto);
	}

	/**
	 * 校验真实姓名唯一性.
	 *
	 * @param checkUserNameDto the check user name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUserName")
	@ApiOperation(httpMethod = "POST", value = "校验真实姓名唯一性")
	public Wrapper<Boolean> checkUserName(@RequestBody CheckUserNameDto checkUserNameDto) {
		logger.info(" 校验真实姓名唯一性 checkUserNameDto={}", checkUserNameDto);
        return uacUserFeignApi.checkUserName(checkUserNameDto);
	}

	/**
	 * 校验用户电话号码唯一性.
	 *
	 * @param checkUserPhoneDto the check user phone dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUserPhone")
	@ApiOperation(httpMethod = "POST", value = "校验用户电话号码唯一性")
	public Wrapper<Boolean> checkUserPhone(@RequestBody CheckUserPhoneDto checkUserPhoneDto) {
		logger.info(" 校验用户电话号码唯一性 checkUserPhoneDto={}", checkUserPhoneDto);
        return uacUserFeignApi.checkUserPhone(checkUserPhoneDto);
	}

	/**
	 * 校验新密码是否与原始密码相同.
	 *
	 * @param checkNewPasswordDto 修改密码实体
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkNewPassword")
	@ApiOperation(httpMethod = "POST", value = "校验新密码是否与原始密码相同")
	public Wrapper<Boolean> checkNewPassword(@RequestBody CheckNewPasswordDto checkNewPasswordDto) {
		logger.info(" 校验新密码是否与原始密码相同 checkNewPasswordDto={}", checkNewPasswordDto);
        return uacUserFeignApi.checkNewPassword(checkNewPasswordDto);
	}

	/**
	 * 修改用户邮箱
	 *
	 * @param email     the email
	 * @param emailCode the email code
	 *
	 * @return the wrapper
	 */
	@LogAnnotation
	@PostMapping(value = "/modifyUserEmail/{email}/{emailCode}")
	@ApiOperation(httpMethod = "POST", value = "修改用户信息")
	public Wrapper<Integer> modifyUserEmail(@PathVariable String email, @PathVariable String emailCode) {
		logger.info(" 修改用户信息 email={}, emailCode={}", email, emailCode);
		return uacUserFeignApi.modifyUserEmail(email, emailCode, getLoginAuthDto());
	}


	/**
	 * 获取已有权限树
	 *
	 * @return the auth tree by role id
	 */
	@PostMapping(value = "/getOwnAuthTree")
	@ApiOperation(httpMethod = "POST", value = "获取权限树")
	public Wrapper<List<MenuVo>> getOwnAuthTree() {
        return uacUserFeignApi.getOwnAuthTree(getLoginAuthDto().getUserId());
	}


	/**
	 * 用户修改密码
	 *
	 * @param userModifyPwdDto the user modify pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyUserPwd")
	@LogAnnotation
	@ApiOperation(httpMethod = "POST", value = "用户修改密码")
	public Wrapper<Integer> modifyUserPwd(@RequestBody UserModifyPwdDto userModifyPwdDto) {
		logger.info("==》vue用户修改密码, userModifyPwdDto={}", userModifyPwdDto);
		userModifyPwdDto.setLoginAuthDto(getLoginAuthDto());

		return uacUserFeignApi.modifyUserPwd(userModifyPwdDto);
	}

	/**
	 * 注册
	 *
	 * @param registerDto the register dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/registerUser")
	@ApiOperation(httpMethod = "POST", value = "注册新用户")
	public Wrapper registerUser(@RequestBody UserRegisterDto registerDto) {
		logger.info("vue注册开始。注册参数：{}", registerDto);
		return uacUserFeignApi.registerUser(registerDto);
	}

	/**
	 * 分页查询用户操作日志列表.
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryUserLogListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询用户操作日志列表")
    @ValidateAnnotation
	public Wrapper<PageInfo<UacLogVO>> queryUserLogListWithPage(@RequestBody @Valid UacLogQueryDTO uacLogQueryDTO, BindingResult bindingResult) {

        String loginName = uacLogQueryDTO.getLoginName();

        Integer pageNum = uacLogQueryDTO.getPageNum();
		Integer pageSize = uacLogQueryDTO.getPageSize();

		return uacUserFeignApi.queryUserLogListWithPage(pageNum, pageSize, loginName);
	}
}
