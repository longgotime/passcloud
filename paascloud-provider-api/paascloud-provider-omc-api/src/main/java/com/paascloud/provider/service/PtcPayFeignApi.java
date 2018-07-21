/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcPayController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.AlipayDTO;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.service.hystrix.OmcShippingFeignHystrix;
import com.paascloud.provider.service.hystrix.PtcPayFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * The class Ptc pay feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Api(value = "WEB - PtcPayController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = PtcPayFeignHystrix.class)
public interface PtcPayFeignApi {
    /**
     * 生成付款二维码.
     *
     * @param orderDto the order dto
     * @return the wrapper
     */
    @PostMapping("/omc/pay/createQrCodeImage")
    @ApiOperation(httpMethod = "POST", value = "生成付款二维码")
    Wrapper createQrCodeImage(@RequestBody OrderDto orderDto);

    /**
     * 支付宝回调信息.
     *
     * @param alipayDTO the alipay dto
     * @return the wrapper
     */
    @PostMapping("/omc/pay/alipayCallback")
    @ApiOperation(httpMethod = "POST", value = "支付宝回调信息")
    Wrapper aliPayCallback(@RequestBody AlipayDTO alipayDTO);

}
