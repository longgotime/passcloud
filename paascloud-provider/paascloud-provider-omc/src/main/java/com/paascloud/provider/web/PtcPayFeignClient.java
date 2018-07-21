/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcPayFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.AlipayDTO;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.service.PtcAlipayService;
import com.paascloud.provider.service.PtcPayFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * The class Ptc pay feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "WEB - PtcPayController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PtcPayFeignClient extends BaseFeignClient implements PtcPayFeignApi {
    @Resource
    private PtcAlipayService ptcAlipayService;


    @Override
    public Wrapper createQrCodeImage(@RequestBody OrderDto orderDto) {
        LoginAuthDto loginAuthDto = orderDto.getLoginAuthDto();
        String orderNo = orderDto.getOrderNo();
        return ptcAlipayService.pay(orderNo, loginAuthDto);
    }

    @Override
    public Wrapper aliPayCallback(@RequestBody AlipayDTO alipayDTO) {
        return ptcAlipayService.aliPayCallback(alipayDTO);
    }

}
