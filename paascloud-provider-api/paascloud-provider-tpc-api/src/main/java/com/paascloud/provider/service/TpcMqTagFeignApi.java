/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTagFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqTagQuery;
import com.paascloud.provider.model.vo.TpcMqTagVo;
import com.paascloud.provider.service.hystrix.TpcMqTagFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * The class Tpc mq tag feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-tpc", configuration = OAuth2FeignAutoConfiguration.class, fallback = TpcMqTagFeignHystrix.class)
@Api(value = "WEB - TpcMqTagController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface TpcMqTagFeignApi {

    /**
     * 查询MQ Tag列表.
     *
     * @param tpcMqTag the tpc mq tag
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/tag/queryTagListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询MQ-Tag列表")
    Wrapper<PageInfo<TpcMqTagVo>> queryTagListWithPage(@ApiParam(name = "tag", value = "角色信息") @RequestBody TpcMqTagQuery tpcMqTag);

    /**
     * 修改tag状态.
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/tag/modifyStatusById")
    @ApiOperation(httpMethod = "POST", value = "修改MQ-Tag状态")
    Wrapper modifyProducerStatusById(@ApiParam(value = "修改tag状态") @RequestBody UpdateStatusDto updateStatusDto);

    /**
     * 根据Tag ID删除TAG.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/tpc/tag/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID删除TAG")
    Wrapper deleteTagById(@ApiParam(value = "Tag ID") @PathVariable("id") Long id);
}
