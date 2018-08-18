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

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.Md5Util;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.IdDTO;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.service.UacUserFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.provider.model.vo.role.UserBindRoleVo;
import com.paascloud.provider.model.vo.user.UacLogVO;
import com.paascloud.provider.model.vo.user.UserVo;
import com.paascloud.provider.service.UacRoleService;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;


/**
 * 用户管理主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacUserFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacUserFeignClient extends BaseFeignClient implements UacUserFeignApi {

    @Resource
    private UacUserService uacUserService;

    @Resource
    private UacRoleService uacRoleService;

    @Override
    public Wrapper<PageInfo> queryUserListWithPage(@RequestBody QueryUserDTO uacUser) {
        logger.info("查询用户列表uacUser={}", uacUser);
        UacUser user = new UacUser();
        BeanUtils.copyProperties(uacUser, user);
        PageInfo pageInfo = uacUserService.queryUserListWithPage(user);
        return WrapMapper.ok(pageInfo);
    }

    @Override
    public Wrapper<Integer> saveUacUser(@RequestBody SaveUserDTO user) {
        logger.info(" 新增用户 user={}", user);
        LoginAuthDto loginAuthDto = user.getLoginAuthDto();
        UacUser uacUser = new UacUser();
        BeanUtils.copyProperties(user, uacUser);
        uacUserService.saveUacUser(uacUser, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<Integer> modifyUserStatusById(@RequestBody ModifyUserStatusDto modifyUserStatusDto) {
        logger.info(" 根据Id修改用户状态 modifyUserStatusDto={}", modifyUserStatusDto);
        LoginAuthDto loginAuthDto = modifyUserStatusDto.getLoginAuthDto();
        UacUser uacUser = new UacUser();
        uacUser.setId(modifyUserStatusDto.getUserId());
        uacUser.setStatus(modifyUserStatusDto.getStatus());

        int result = uacUserService.modifyUserStatusById(uacUser, loginAuthDto);
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper<Integer> deleteUserById(@PathVariable("userId") Long userId) {
        logger.info(" 通过Id删除用户 userId={}", userId);
        int result = uacUserService.deleteUserById(userId);
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper<UserBindRoleVo> getBindRole(@PathVariable("userId") Long userId) {
        logger.info("获取用户绑定角色页面数据. userId={}", userId);

        UserBindRoleVo bindUserDto = uacUserService.getUserBindRoleDto(userId);
        return WrapMapper.ok(bindUserDto);
    }

    @Override
    public Wrapper<Integer> bindUserRoles(@RequestBody BindUserRolesDto bindUserRolesDto) {
        logger.info("用户绑定角色 bindUserRolesDto={}", bindUserRolesDto);
        LoginAuthDto loginAuthDto = bindUserRolesDto.getLoginAuthDto();
        uacUserService.bindUserRoles(bindUserRolesDto, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<List<UserMenuDto>> queryUserMenuDtoData(@PathVariable("userId") Long userId) {
        logger.info("查询用户常用功能数据");
        List<UserMenuDto> userMenuDtoList = uacUserService.queryUserMenuDtoData(userId);
        return WrapMapper.ok(userMenuDtoList);
    }

    @Override
    public Wrapper<Integer> bindUserMenus(@RequestBody BindUserMenusDto bindUserMenusDto) {
        logger.info("绑定用户常用菜单");
        List<Long> menuIdList = bindUserMenusDto.getMenuIdList();
        logger.info("menuIdList = {}", menuIdList);

        int result = uacUserService.bindUserMenus(menuIdList, bindUserMenusDto.getLoginAuthDto());

        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper<UserVo> getUacUserById(@PathVariable("userId") Long userId) {
        logger.info("getUacUserById - 根据用户Id查询用户信息. userId={}", userId);
        UacUser uacUser = uacUserService.queryByUserId(userId);
        logger.info("getUacUserById - 根据用户Id查询用户信息. [OK] uacUser={}", uacUser);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(uacUser, userVo);
        return WrapMapper.ok(userVo);
    }

    @Override
    public Wrapper<UserVo> resetLoginPwd(@RequestBody IdDTO idDTO) {
        logger.info("resetLoginPwd - 根据用户Id重置密码. idDTO={}", idDTO);
        uacUserService.resetLoginPwd(idDTO.getId(), idDTO.getLoginAuthDto());
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<UserVo> queryUserInfo(@PathVariable("loginName") String loginName) {
        logger.info("根据userId查询用户详细信息");
        UserVo userVo = new UserVo();
        UacUser uacUser = uacUserService.findByLoginName(loginName);
        uacUser = uacUserService.findUserInfoByUserId(uacUser.getId());
        List<RoleVo> roleList = uacRoleService.findAllRoleInfoByUserId(uacUser.getId());
        List<MenuVo> authTree = uacRoleService.getOwnAuthTree(uacUser.getId());
        BeanUtils.copyProperties(uacUser, userVo);
        if (PublicUtil.isNotEmpty(roleList)) {
            userVo.setRoles(new HashSet<>(roleList));
        }
        userVo.setAuthTree(authTree);
        return WrapMapper.ok(userVo);
    }

    @Override
    public Wrapper<Boolean> checkLoginName(@RequestBody CheckLoginNameDto checkLoginNameDto) {
        logger.info("校验登录名唯一性 checkLoginNameDto={}", checkLoginNameDto);

        Long id = checkLoginNameDto.getUserId();
        String loginName = checkLoginNameDto.getLoginName();

        Example example = new Example(UacUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("loginName", loginName);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }

        int result = uacUserService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkEmail(@RequestBody CheckEmailDto checkEmailDto) {
        logger.info("校验邮箱唯一性 checkEmailDto={}", checkEmailDto);

        Long id = checkEmailDto.getUserId();
        String email = checkEmailDto.getEmail();

        Example example = new Example(UacUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", email);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }

        int result = uacUserService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkUserName(@RequestBody CheckUserNameDto checkUserNameDto) {
        logger.info(" 校验真实姓名唯一性 checkUserNameDto={}", checkUserNameDto);
        Long id = checkUserNameDto.getUserId();
        String name = checkUserNameDto.getUserName();

        Example example = new Example(UacUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userName", name);
        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }

        int result = uacUserService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkUserPhone(@RequestBody CheckUserPhoneDto checkUserPhoneDto) {
        logger.info(" 校验用户电话号码唯一性 checkUserPhoneDto={}", checkUserPhoneDto);
        Long id = checkUserPhoneDto.getUserId();
        String mobileNo = checkUserPhoneDto.getMobileNo();
        Example example = new Example(UacUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobileNo", mobileNo);

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }

        int result = uacUserService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkNewPassword(@RequestBody CheckNewPasswordDto checkNewPasswordDto) {
        logger.info(" 校验新密码是否与原始密码相同 checkNewPasswordDto={}", checkNewPasswordDto);
        String loginName = checkNewPasswordDto.getLoginName();
        String newPassword = checkNewPasswordDto.getNewPassword();
        UacUser uacUser = new UacUser();
        uacUser.setLoginName(loginName);
        int result = 0;
        UacUser user = uacUserService.findByLoginName(loginName);
        if (user == null) {
            logger.error("找不到用户. loginName={}", loginName);
        } else {
            uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
            result = uacUserService.selectCount(uacUser);
        }
        return WrapMapper.ok(result < 1);
    }


    @Override
    public Wrapper<Integer> modifyUserEmail(@RequestParam("email") String email, @RequestParam("emailCode") String emailCode, @RequestParam("emailCode") LoginAuthDto loginAuthDto) {
        logger.info(" 修改用户信息 email={}, emailCode={}", email, emailCode);
        uacUserService.modifyUserEmail(email, emailCode, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<List<MenuVo>> getOwnAuthTree(@PathVariable("userId") Long userId) {
        List<MenuVo> tree = uacRoleService.getOwnAuthTree(userId);
        return WrapMapper.ok(tree);
    }

    @Override
    public Wrapper<Integer> modifyUserPwd(@RequestBody UserModifyPwdDto userModifyPwdDto) {
        logger.info("==》vue用户修改密码, userModifyPwdDto={}", userModifyPwdDto);

        logger.info("旧密码 oldPassword = {}", userModifyPwdDto.getOldPassword());
        logger.info("新密码 newPassword = {}", userModifyPwdDto.getNewPassword());
        logger.info("登录名 loginName = {}", userModifyPwdDto.getLoginName());

        LoginAuthDto loginAuthDto = userModifyPwdDto.getLoginAuthDto();

        int result = uacUserService.userModifyPwd(userModifyPwdDto, loginAuthDto);
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper registerUser(@RequestBody UserRegisterDto registerDto) {
        logger.info("vue注册开始。注册参数：{}", registerDto);
        uacUserService.register(registerDto);
        return WrapMapper.ok("注册成功");
    }

    @Override
    public Wrapper<PageInfo<UacLogVO>> queryUserLogListWithPage(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "loginName") String loginName) {
        PageHelper.startPage(pageNum, pageSize);

        UacUser uacUser = uacUserService.findByLoginName(loginName);
        
        if (uacUser == null) {
            return null;
        }

        List<UacLogVO> list = uacUserService.queryUserLogListWithUserId(uacUser.getId());
        PageInfo<UacLogVO> pageInfo = new PageInfo<>(list);
        return WrapMapper.ok(pageInfo);
    }
}
