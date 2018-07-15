/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqConsumerQuery;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;
import com.paascloud.provider.service.hystrix.TpcMqConsumerFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 消费者管理.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-tpc", configuration = OAuth2FeignAutoConfiguration.class, fallback = TpcMqConsumerFeignHystrix.class)
@Api(value = "WEB - TpcMqConsumerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TpcMqConsumerFeignApi {

    /**
     * 查询Mq消费者列表.
     *
     * @param tpcMqConsumer the tpc mq consumer
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/consumer/queryConsumerVoListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询Mq消费者列表")
    Wrapper<List<TpcMqConsumerVo>> queryConsumerVoList(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumer);

    /**
     * 查询订阅者列表.
     *
     * @param tpcMqConsumer the tpc mq consumer
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/consumer/querySubscribeListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询订阅者列表")
    Wrapper<PageInfo<TpcMqSubscribeVo>> querySubscribeListWithPage(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumer);

    /**
     * 更改消费者状态.
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/consumer/modifyStatusById")
    @ApiOperation(httpMethod = "POST", value = "更改消费者状态")
    Wrapper modifyConsumerStatusById(@ApiParam(value = "更改消费者状态") @RequestBody UpdateStatusDto updateStatusDto);

    /**
     * 根据消费者ID删除消费者.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/consumer/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据消费者ID删除消费者")
    Wrapper deleteConsumerById(@PathVariable("id") Long id);
}
