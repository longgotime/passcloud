/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallOrderController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.mall.controller;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.service.OmcOrderFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 购物车管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallCartController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallOrderController extends BaseController {

	@Resource
	private OmcOrderFeignApi omcOrderFeignApi;


	/**
	 * 获取购物车商品数量.
	 *
	 * @return the order cart product
	 */
	@PostMapping("/getOrderCartProduct")
	@ApiOperation(httpMethod = "POST", value = "获取购物车商品数量")
	public Wrapper getOrderCartProduct() {
		logger.info("getOrderCartProduct - 获取购物车商品数量");
		return omcOrderFeignApi.getOrderCartProduct(getLoginAuthDto().getUserId());
	}
}
