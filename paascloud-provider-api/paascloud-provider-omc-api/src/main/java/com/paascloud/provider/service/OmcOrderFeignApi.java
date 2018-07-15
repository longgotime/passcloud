/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.OmcCancelOrderDto;
import com.paascloud.provider.model.dto.OmcCreateOrderDto;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.OrderPageQuery;
import com.paascloud.provider.service.hystrix.OmcOrderFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Omc order feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-omc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderFeignHystrix.class)
public interface OmcOrderFeignApi {
    /**
     * Update order by id wrapper.
     *
     * @param order the order
     * @return the wrapper
     */
    @PostMapping(value = "/omc/order/updateOrderById")
	Wrapper updateOrderById(@RequestBody OrderDto order);

    /**
     * 获取购物车数量.
     *
     * @return the cart count
     */
    @PostMapping(value = "/omc/mdc/order/getCartCount")
	Wrapper<Integer> getCartCount();

    /**
     * 获取购物车商品数量.
     *
     * @param userId the user id
     * @return the order cart product
     */
    @PostMapping("/omc/order/getOrderCartProduct/{userId}")
	@ApiOperation(httpMethod = "POST", value = "获取购物车商品数量")
	Wrapper getOrderCartProduct(@PathVariable("userId") Long userId);

    /**
     * 创建订单.
     *
     * @param omcCreateOrderDto the omc create order dto
     * @return the wrapper
     */
    @PostMapping("/omc/order/createOrderDoc")
	@ApiOperation(httpMethod = "POST", value = "创建订单")
	Wrapper createOrderDoc(@RequestBody OmcCreateOrderDto omcCreateOrderDto);


    /**
     * 取消订单.
     *
     * @param cancelOrderDto the cancel order dto
     * @return the wrapper
     */
    @PostMapping("/omc/order/cancelOrderDoc")
	@ApiOperation(httpMethod = "POST", value = "取消订单")
	Wrapper cancelOrderDoc(@RequestBody OmcCancelOrderDto cancelOrderDto);

    /**
     * 查询订单详情.
     *
     * @param orderNo the order no
     * @param userId  the user id
     * @return the wrapper
     */
    @PostMapping("/omc/order/queryUserOrderDetailList/{orderNo}/{userId}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	Wrapper queryUserOrderDetailList(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId);

    /**
     * Query user order detail wrapper.
     *
     * @param orderNo the order no
     * @return the wrapper
     */
    @PostMapping("/omc/order/queryUserOrderDetail/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	Wrapper queryUserOrderDetail(@PathVariable("orderNo") String orderNo);

    /**
     * Query user order list with page wrapper.
     *
     * @param orderPageQuery the order page query
     * @return the wrapper
     */
    @PostMapping("/omc/order/queryUserOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	Wrapper queryUserOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery);

    /**
     * Query order list with page wrapper.
     *
     * @param orderPageQuery the order page query
     * @return the wrapper
     */
    @PostMapping("/omc/order/queryOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	Wrapper queryOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery);

    /**
     * 查询订单状态.
     *
     * @param orderNo the order no
     * @param userId  the user id
     * @return the wrapper
     */
    @PostMapping("/omc/order/queryOrderPayStatus/{orderNo}/{userId}")
	@ApiOperation(httpMethod = "POST", value = "查询订单状态")
	Wrapper<Boolean> queryOrderPayStatus(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId);
}
