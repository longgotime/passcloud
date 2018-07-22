
/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：PcSmsCodeSender.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */

package com.paascloud.service.service;

import com.paascloud.base.constant.AliyunSmsConstants;
import com.paascloud.provider.model.dto.sms.SmsMessage;
import com.paascloud.provider.model.service.SmsFeignApi;
import com.paascloud.service.security.code.sms.SmsCodeSender;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 默认的短信验证码发送器
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class PcSmsCodeSender implements SmsCodeSender {
	@Resource
	private SmsFeignApi smsFeignApi;

	@Override
	public void send(String mobile, String code, String ip) {
		log.info("ip地址:{}向手机: {}发送短信验证码:{}", ip, mobile, code);
		SmsMessage smsMessage = new SmsMessage();
		smsMessage.setMobileNo(mobile);
		smsMessage.setSmsCode(code);
		smsMessage.setSmsTemplateCode(AliyunSmsConstants.SmsTempletEnum.UAC_PC_GLOBAL_TEMPLATE.getTempletCode());
		smsMessage.setIp(ip);
		smsFeignApi.sendSmsCode(smsMessage);
	}

}
