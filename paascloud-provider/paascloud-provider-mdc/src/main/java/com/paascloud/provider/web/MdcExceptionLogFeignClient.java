/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionLogFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.GlobalExceptionLogDto;
import com.paascloud.provider.service.MdcExceptionLogFeignApi;
import com.paascloud.provider.service.MdcExceptionLogService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mdc product feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
public class MdcExceptionLogFeignClient extends BaseFeignClient implements MdcExceptionLogFeignApi {
	@Resource
	private MdcExceptionLogService mdcExceptionLogService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "保存日志并发送钉钉消息")
	public Wrapper saveAndSendExceptionLog(@RequestBody GlobalExceptionLogDto exceptionLogDto) {
		try {
			mdcExceptionLogService.saveAndSendExceptionLog(exceptionLogDto);
		} catch (Exception e) {
			logger.error("saveAndSendExceptionLog={}", e.getMessage(), e);
		}
		return WrapMapper.ok();
	}
}
