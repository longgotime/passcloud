/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.enums.LogTypeEnum;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.constant.UacApiConstant;
import com.paascloud.provider.model.domain.UacAction;
import com.paascloud.provider.model.domain.UacLog;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.log.OperationLogDto;
import com.paascloud.provider.model.dto.user.AuthUserDTO;
import com.paascloud.provider.model.dto.user.HandlerLoginDTO;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.service.UacAuthUserFeignApi;
import com.paascloud.provider.model.vo.role.RoleVo;
import com.paascloud.provider.service.*;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * The type Uac auth user feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - UacAuthUserFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacAuthUserFeignClient extends BaseFeignClient implements UacAuthUserFeignApi {

    @Resource
    private TaskExecutor taskExecutor;
    @Resource
    private UacUserService uacUserService;
    @Resource
    private UacRoleService uacRoleService;
    @Resource
    private UacUserTokenService uacUserTokenService;
    @Resource
    private UacLogService uacLogService;
    @Resource
    private UacActionService uacActionService;
    @Resource
    private SmsService smsService;
    @Resource
    private EmailService emailService;

    @Override
    public Wrapper<Boolean> checkPhoneActive(@PathVariable(name = "mobileNo") String mobileNo) {
        UacUser uacUser = new UacUser();
        uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
        uacUser.setMobileNo(mobileNo);
        int count = uacUserService.selectCount(uacUser);
        return WrapMapper.ok(count > 0);
    }

    @Override
    public Wrapper<Boolean> checkEmailActive(@RequestParam("email") String email) {
        UacUser uacUser = new UacUser();
        uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
        uacUser.setEmail(email);
        int count = uacUserService.selectCount(uacUser);
        return WrapMapper.ok(count > 0);
    }

    @Override
    public Wrapper checkValid(@RequestBody CheckValidDto checkValidDto) {
        String type = checkValidDto.getType();
        String validValue = checkValidDto.getValidValue();

        Preconditions.checkArgument(StringUtils.isNotEmpty(validValue), "参数错误");
        String message = null;
        boolean result = false;
        //开始校验
        if (UacApiConstant.Valid.LOGIN_NAME.equals(type)) {
            result = uacUserService.checkLoginName(validValue);
            if (!result) {
                message = "用户名已存在";
            }
        }
        if (UacApiConstant.Valid.EMAIL.equals(type)) {
            result = uacUserService.checkEmail(validValue);
            if (!result) {
                message = "邮箱已存在";
            }
        }

        if (UacApiConstant.Valid.MOBILE_NO.equals(type)) {
            result = uacUserService.checkMobileNo(validValue);
            if (!result) {
                message = "手机号码已存在";
            }
        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, message, result);
    }

    @Override
    public Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email) {
        logger.info("重置密码-邮箱-提交, email={}", email);
        emailService.submitResetPwdEmail(email);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<String> submitResetPwdPhone(@RequestParam("mobile") String mobile) {
        logger.info("重置密码-手机-提交, mobile={}", mobile);
        String token = smsService.submitResetPwdPhone(mobile);
        return WrapMapper.ok(token);
    }

    @Override
    public Wrapper<Boolean> checkResetSmsCode(@RequestBody ResetLoginPwdDto resetLoginPwdDto) {
        uacUserService.resetLoginPwd(resetLoginPwdDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper registerUser(@RequestBody UserRegisterDto user) {
        uacUserService.register(user);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper activeUser(@PathVariable("activeUserToken") String activeUserToken) {
        uacUserService.activeUser(activeUserToken);
        return WrapMapper.ok("激活成功");
    }

    @Override
    public Wrapper<Integer> saveLog(@RequestBody OperationLogDto operationLogDto) {
        logger.info("saveLog - 保存操作日志. operationLogDto={}", operationLogDto);
        uacLogService.saveOperationLog(operationLogDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<AuthUserDTO> getAuthUserDTO(@PathVariable("loginName") String loginName) {

        UacUser user = uacUserService.findByLoginName(loginName);
        if (user == null) {
            throw new BadCredentialsException("用户名不存在或者密码错误");
        }
        user = uacUserService.findUserInfoByUserId(user.getId());

        List<RoleVo> ownRoleList = uacRoleService.findAllRoleInfoByUserId(user.getId());

        List<String> authList = Lists.newArrayList();
        for (RoleVo roleVo : ownRoleList) {
            authList.add(roleVo.getRoleCode());
        }

        AuthUserDTO authUserDTO = new AuthUserDTO();

        authUserDTO.setUserId(user.getId());
        authUserDTO.setGroupId(user.getGroupId());
        authUserDTO.setGroupName(user.getGroupName());
        authUserDTO.setLoginName(user.getLoginName());
        authUserDTO.setNickName(user.getUserName());
        authUserDTO.setStatus(user.getStatus());
        authUserDTO.setLoginPwd(user.getLoginPwd());

        authUserDTO.setAuthUrlList(authList);
        logger.info("查询用户成功 user={}", authUserDTO);
        return WrapMapper.ok(authUserDTO);
    }

    @Override
    public Wrapper<?> handlerLoginData(@RequestBody HandlerLoginDTO handlerLoginDTO) {

        String accessToken = handlerLoginDTO.getAccessToken();
        String refreshToken = handlerLoginDTO.getRefreshToken();
        String browser = handlerLoginDTO.getBrowser();
        String os = handlerLoginDTO.getOs();
        AuthUserDTO principal = handlerLoginDTO.getPrincipal();
        String remoteAddr = handlerLoginDTO.getRemoteAddr();
        String remoteLocation = handlerLoginDTO.getRemoteLocation();
        String requestURI = handlerLoginDTO.getRequestURI();

        UacUser uacUser = new UacUser();
        Long userId = principal.getUserId();
        uacUser.setLastLoginIp(remoteAddr);
        uacUser.setId(userId);
        uacUser.setLastLoginTime(new Date());
        uacUser.setLastLoginLocation(remoteLocation);
        LoginAuthDto loginAuthDto = new LoginAuthDto(userId, principal.getLoginName(), principal.getNickName(), principal.getGroupId(), principal.getGroupName());
        // 记录token日志
        uacUserTokenService.saveUserToken(accessToken, refreshToken, loginAuthDto, os, browser, remoteAddr, remoteLocation);
        // 记录最后登录信息
        taskExecutor.execute(() -> uacUserService.updateUser(uacUser));
        // 记录操作日志

        UacLog log = new UacLog();
        log.setGroupId(principal.getGroupId());
        log.setGroupName(principal.getGroupName());
        log.setIp(remoteAddr);
        log.setLocation(remoteLocation);
        log.setOs(os);
        log.setBrowser(browser);
        log.setRequestUrl(requestURI);
        log.setLogType(LogTypeEnum.LOGIN_LOG.getType());
        log.setLogName(LogTypeEnum.LOGIN_LOG.getName());

        taskExecutor.execute(() -> uacLogService.saveLog(log, loginAuthDto));
        return WrapMapper.ok();
    }
}
