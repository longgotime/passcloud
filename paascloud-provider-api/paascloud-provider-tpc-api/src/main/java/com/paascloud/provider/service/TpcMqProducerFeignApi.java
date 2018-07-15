/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqProducerQuery;
import com.paascloud.provider.model.vo.TpcMqProducerVo;
import com.paascloud.provider.model.vo.TpcMqPublishVo;
import com.paascloud.provider.service.hystrix.TpcMqProducerFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 生产者管理.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-tpc", configuration = OAuth2FeignAutoConfiguration.class, fallback = TpcMqProducerFeignHystrix.class)
@Api(value = "WEB - TpcMqProducerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TpcMqProducerFeignApi {

    /**
     * 查询生产者列表.
     *
     * @param tpcMqProducer the tpc mq producer
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/producer/queryProducerVoListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询生产者列表")
    Wrapper<List<TpcMqProducerVo>> queryProducerList(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducerQuery tpcMqProducer);

    /**
     * 查询发布者列表.
     *
     * @param tpcMqProducer the tpc mq producer
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/producer/queryPublishListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询发布者列表")
    Wrapper<PageInfo<TpcMqPublishVo>> queryPublishListWithPage(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducerQuery tpcMqProducer);

    /**
     * 修改生产者状态.
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/producer/modifyStatusById")
    @ApiOperation(httpMethod = "POST", value = "修改生产者状态")
    Wrapper modifyProducerStatusById(@ApiParam(value = "修改producer状态") @RequestBody UpdateStatusDto updateStatusDto);

    /**
     * 根据生产者ID删除生产者.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/producer/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据生产者ID删除生产者")
    Wrapper deleteProducerById(@PathVariable("id") Long id);
}
