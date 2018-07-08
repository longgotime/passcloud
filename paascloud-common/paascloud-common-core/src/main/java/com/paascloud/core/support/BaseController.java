/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：BaseController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.support;

import com.alibaba.fastjson.JSONObject;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * The class Base controller.
 *
 * @author paascloud.net@gmail.com
 */
public class BaseController {
	@Autowired
	private HttpServletRequest request;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Gets login auth dto.
	 *
	 * @return the login auth dto
	 */
	protected LoginAuthDto getLoginAuthDto() {
		String authJson = request.getHeader(GlobalConstant.Sys.TOKEN_AUTH_DTO);
        LoginAuthDto loginAuthDto;
        try {
            loginAuthDto = JSONObject.parseObject(URLDecoder.decode(authJson, "UTF-8"), LoginAuthDto.class);
        } catch (UnsupportedEncodingException e) {
            logger.error("getLoginAuthDto - WEB-转换登录信息失败 ex={}", e.getMessage(), e);
            throw new BusinessException(ErrorCodeEnum.UAC10011041);
        }
        if (PublicUtil.isEmpty(loginAuthDto)) {
			throw new BusinessException(ErrorCodeEnum.UAC10011041);
		}
		return loginAuthDto;
	}
}
  