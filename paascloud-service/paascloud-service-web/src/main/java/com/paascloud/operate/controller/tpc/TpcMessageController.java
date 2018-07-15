/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMessageController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.tpc;

import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.service.TpcMqMessageFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 异常管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - TpcMessageController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMessageController extends BaseController {
    @Resource
    private TpcMqMessageFeignApi tpcMqMessageFeignApi;

    /**
     * 异常日志列表.
     *
     * @param messageQueryDto the message query dto
     * @return the wrapper
     */
    @PostMapping(value = "/queryRecordListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询各中心落地消息记录")
    public Wrapper queryRecordListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
        logger.info("分页查询各中心落地消息记录. messageQueryDto={}", messageQueryDto);
        return tpcMqMessageFeignApi.queryRecordListWithPage(messageQueryDto);
    }

    /**
     * Resend message by id wrapper.
     *
     * @param messageId the message id
     * @return the wrapper
     */
    @PostMapping(value = "/resendMessageById/{messageId}")
    @ApiOperation(httpMethod = "POST", value = "重发消息")
    public Wrapper resendMessageById(@PathVariable Long messageId) {
        logger.info("重发消息. messageId={}", messageId);
        return tpcMqMessageFeignApi.resendMessageById(messageId);
    }

    /**
     * Query reliable list with page wrapper.
     *
     * @param messageQueryDto the message query dto
     * @return the wrapper
     */
    @PostMapping(value = "/queryReliableListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询可靠消息")
    public Wrapper queryReliableListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
        logger.info("分页查询可靠消息. tpcMessageQueryDto={}", messageQueryDto);

        return tpcMqMessageFeignApi.queryReliableListWithPage(messageQueryDto);
    }
}