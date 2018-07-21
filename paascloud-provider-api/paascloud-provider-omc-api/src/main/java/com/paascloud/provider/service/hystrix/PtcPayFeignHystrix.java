/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PtcPayFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.AlipayDTO;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.service.PtcPayFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * The class Omc cart feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class PtcPayFeignHystrix implements PtcPayFeignApi {
	@Override
	public Wrapper createQrCodeImage(OrderDto orderDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper aliPayCallback(AlipayDTO alipayDTO) {
		return WrapMapper.error();
	}
}
