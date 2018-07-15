/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcBizException.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.exceptions;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;


/**
 * The type Ptc biz exception.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class PtcBizException extends BusinessException {

	private static final long serialVersionUID = -6552248511084911254L;

	/**
	 * Instantiates a new Ptc biz exception.
	 */
	public PtcBizException() {
	}

	/**
	 * Instantiates a new Ptc biz exception.
	 *
	 * @param code      the code
	 * @param msgFormat the msg format
	 * @param args      the args
	 */
	public PtcBizException(int code, String msgFormat, Object... args) {
		super(code, msgFormat, args);
		log.info("<== PtcBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Ptc biz exception.
	 *
	 * @param code the code
	 * @param msg  the msg
	 */
	public PtcBizException(int code, String msg) {
		super(code, msg);
		log.info("<== PtcBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Ptc biz exception.
	 *
	 * @param codeEnum the code enum
	 */
	public PtcBizException(ErrorCodeEnum codeEnum) {
		super(codeEnum.code(), codeEnum.msg());
		log.info("<== PtcBizException, code:{}, message:{}", this.code, super.getMessage());
	}

	/**
	 * Instantiates a new Ptc biz exception.
	 *
	 * @param codeEnum the code enum
	 * @param args     the args
	 */
	public PtcBizException(ErrorCodeEnum codeEnum, Object... args) {
		super(codeEnum, args);
		log.info("<== PtcBizException, code:{}, message:{}", this.code, super.getMessage());
	}
}
