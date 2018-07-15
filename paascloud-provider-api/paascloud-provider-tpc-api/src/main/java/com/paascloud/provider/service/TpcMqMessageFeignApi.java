/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.provider.model.dto.TpcMqMessageDto;
import com.paascloud.provider.service.hystrix.TpcMqMessageFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * The interface Tpc mq message feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-tpc", configuration = OAuth2FeignAutoConfiguration.class, fallback = TpcMqMessageFeignHystrix.class)
public interface TpcMqMessageFeignApi {

	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/saveMessageWaitingConfirm")
	Wrapper saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/confirmAndSendMessage")
	Wrapper confirmAndSendMessage(@RequestParam("messageKey") String messageKey);

	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/saveAndSendMessage")
	Wrapper saveAndSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 直接发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/directSendMessage")
	Wrapper directSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 根据messageKey删除消息记录.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/deleteMessageByMessageKey")
	Wrapper deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey);

	/**
	 * Confirm receive message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/confirmReceiveMessage")
	Wrapper confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);

	/**
	 * Save and confirm finish message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/saveAndConfirmFinishMessage")
	Wrapper confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);

	/**
	 * 异常日志列表.
	 *
	 * @param messageQueryDto the message query dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/message/queryRecordListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询各中心落地消息记录")
	Wrapper queryRecordListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto);

	/**
	 * Resend message by id wrapper.
	 *
	 * @param messageId the message id
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/message/resendMessageById/{messageId}")
	@ApiOperation(httpMethod = "POST", value = "重发消息")
	Wrapper resendMessageById(@PathVariable("messageId") Long messageId);

	/**
	 * Query reliable list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/tpc/message/queryReliableListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询可靠消息")
	Wrapper queryReliableListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto);
}
