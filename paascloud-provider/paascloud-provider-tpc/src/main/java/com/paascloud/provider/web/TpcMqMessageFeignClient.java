/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.TpcMqMessageDto;
import com.paascloud.provider.model.vo.TpcMessageVo;
import com.paascloud.provider.service.TpcMqMessageFeignApi;
import com.paascloud.provider.service.TpcMqMessageService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class Tpc mq message feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - TpcMqMessageFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqMessageFeignClient extends BaseFeignClient implements TpcMqMessageFeignApi {
    @Resource
    private TpcMqMessageService tpcMqMessageService;

    @Override
    @ApiOperation(httpMethod = "POST", value = "预存储消息")
    public Wrapper saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto) {
        logger.info("预存储消息. mqMessageDto={}", mqMessageDto);
        tpcMqMessageService.saveMessageWaitingConfirm(mqMessageDto);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "确认并发送消息")
    public Wrapper confirmAndSendMessage(@RequestParam("messageKey") String messageKey) {
        logger.info("确认并发送消息. messageKey={}", messageKey);
        tpcMqMessageService.confirmAndSendMessage(messageKey);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "存储并发送消息")
    public Wrapper saveAndSendMessage(@RequestBody TpcMqMessageDto message) {
        logger.info("存储并发送消息. mqMessageDto={}", message);
        tpcMqMessageService.saveAndSendMessage(message);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "直接发送消息")
    public Wrapper directSendMessage(@RequestBody TpcMqMessageDto messageDto) {
        logger.info("直接发送消息. mqMessageDto={}", messageDto);
        tpcMqMessageService.directSendMessage(messageDto.getMessageBody(), messageDto.getMessageTopic(), messageDto.getMessageTag(), messageDto.getMessageKey(), messageDto.getProducerGroup(), messageDto.getDelayLevel());
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "根据消息ID删除消息")
    public Wrapper deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey) {
        logger.info("根据消息ID删除消息. messageKey={}", messageKey);
        tpcMqMessageService.deleteMessageByMessageKey(messageKey);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "确认收到消息")
    public Wrapper confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey) {
        logger.info("确认收到消息. cid={}, messageKey={}", cid, messageKey);
        tpcMqMessageService.confirmReceiveMessage(cid, messageKey);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "确认消费消息")
    public Wrapper confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey) {
        logger.info("确认完成消费消息. cid={}, messageKey={}", cid, messageKey);
        tpcMqMessageService.confirmConsumedMessage(cid, messageKey);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "分页查询各中心落地消息记录")
    public Wrapper queryRecordListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
        logger.info("分页查询各中心落地消息记录. messageQueryDto={}", messageQueryDto);
        return tpcMqMessageService.queryRecordListWithPage(messageQueryDto);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "重发消息")
    public Wrapper resendMessageById(@PathVariable("messageId") Long messageId) {
        logger.info("重发消息. messageId={}", messageId);
        tpcMqMessageService.resendMessageByMessageId(messageId);
        return WrapMapper.ok();
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "分页查询可靠消息")
    public Wrapper queryReliableListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
        logger.info("分页查询可靠消息. tpcMessageQueryDto={}", messageQueryDto);
        PageHelper.startPage(messageQueryDto.getPageNum(), messageQueryDto.getPageSize());
        messageQueryDto.setOrderBy("update_time desc");
        List<TpcMessageVo> list = tpcMqMessageService.listReliableMessageVo(messageQueryDto);
        PageInfo<TpcMessageVo> pageInfo = new PageInfo<>(list);
        if (PublicUtil.isNotEmpty(list)) {
            Map<Long, TpcMessageVo> messageVoMap = this.trans2Map(list);
            List<Long> messageIdList = new ArrayList<>(messageVoMap.keySet());

            List<TpcMessageVo> mqConfirmVoList = tpcMqMessageService.listReliableMessageVo(messageIdList);
            for (TpcMessageVo vo : mqConfirmVoList) {
                Long subscribeId = vo.getId();
                if (!messageVoMap.containsKey(subscribeId)) {
                    continue;
                }
                TpcMessageVo tpcMessageVo = messageVoMap.get(subscribeId);
                tpcMessageVo.setMqConfirmVoList(vo.getMqConfirmVoList());
            }
            pageInfo.setList(new ArrayList<>(messageVoMap.values()));
        }
        return WrapMapper.ok(pageInfo);
    }

    private Map<Long, TpcMessageVo> trans2Map(List<TpcMessageVo> tpcMessageVoList) {
        Map<Long, TpcMessageVo> resultMap = new TreeMap<>((o1, o2) -> {
            o1 = o1 == null ? 0 : o1;
            o2 = o2 == null ? 0 : o2;
            return o2.compareTo(o1);
        });
        for (TpcMessageVo vo : tpcMessageVoList) {
            resultMap.put(vo.getId(), vo);
        }
        return resultMap;
    }
}
