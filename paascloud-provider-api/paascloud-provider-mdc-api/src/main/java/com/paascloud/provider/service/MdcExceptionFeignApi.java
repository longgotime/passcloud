/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.MdcExceptionQueryDto;
import com.paascloud.provider.service.hystrix.MdcExceptionFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 异常管理.
 *
 * @author paascloud.net @gmail.com
 */
@Api(value = "Feign - MdcExceptionMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcExceptionFeignHystrix.class)
public interface MdcExceptionFeignApi {

    /**
     * 异常日志列表.
     *
     * @param mdcExceptionQueryDto the mdc exception query dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/exception/queryListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询日志列表")
    Wrapper queryLogListWithPage(@ApiParam(name = "mdcExceptionQueryDto", value = "异常查询条件") @RequestBody MdcExceptionQueryDto mdcExceptionQueryDto);
}
