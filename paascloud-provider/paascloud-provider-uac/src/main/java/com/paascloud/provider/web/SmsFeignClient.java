/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：SmsFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.sms.SmsMessage;
import com.paascloud.provider.model.service.SmsFeignApi;
import com.paascloud.provider.service.SmsService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * The class Email controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
public class SmsFeignClient extends BaseFeignClient implements SmsFeignApi {

	@Resource
	private SmsService smsService;

	@Override
	public Wrapper sendSmsCode(@RequestBody SmsMessage smsMessage) {
		smsService.sendSmsCode(smsMessage, smsMessage.getIp());
		return WrapMapper.ok();
	}
}
