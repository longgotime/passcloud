/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.OmcOrder;
import com.paascloud.provider.model.dto.OmcCancelOrderDto;
import com.paascloud.provider.model.dto.OmcCreateOrderDto;
import com.paascloud.provider.model.dto.OrderDto;
import com.paascloud.provider.model.dto.OrderPageQuery;
import com.paascloud.provider.model.vo.OrderProductVo;
import com.paascloud.provider.model.vo.OrderVo;
import com.paascloud.provider.service.OmcCartService;
import com.paascloud.provider.service.OmcOrderFeignApi;
import com.paascloud.provider.service.OmcOrderService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Omc order feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderFeignClient extends BaseFeignClient implements OmcOrderFeignApi {
    @Resource
    private OmcOrderService omcOrderService;
    @Resource
    private OmcCartService omcCartService;

    @Override
    @ApiOperation(httpMethod = "POST", value = "更新订单信息")
    public Wrapper updateOrderById(@RequestBody OrderDto orderDto) {
        logger.info("updateOrderById - 更新订单信息. orderDto={}", orderDto);
        ModelMapper modelMapper = new ModelMapper();
        OmcOrder omcOrder = modelMapper.map(orderDto, OmcOrder.class);
        int updateResult = omcOrderService.update(omcOrder);
        return WrapMapper.handleResult(updateResult);

    }

    @Override
    public Wrapper<Integer> getCartCount() {
        return WrapMapper.ok(0);
    }

    @Override
    public Wrapper getOrderCartProduct(@PathVariable("userId") Long userId) {
        logger.info("getOrderCartProduct - 获取购物车商品数量");
        OrderProductVo orderCartProduct = omcCartService.getOrderCartProduct(userId);
        return WrapMapper.ok(orderCartProduct);
    }

    @Override
    public Wrapper createOrderDoc(@RequestBody OmcCreateOrderDto createOrderDto) {
        logger.info("createOrderDoc - 创建订单. createOrderDto={}", createOrderDto);
        LoginAuthDto loginAuthDto = createOrderDto.getLoginAuthDto();

        OrderVo orderDoc = omcOrderService.createOrderDoc(loginAuthDto, createOrderDto.getShippingId());
        return WrapMapper.ok(orderDoc);
    }


    @Override
    public Wrapper cancelOrderDoc(@RequestBody OmcCancelOrderDto cancelOrderDto) {
        logger.info("cancelOrderDoc - 取消订单. cancelOrderDto={}", cancelOrderDto);
        LoginAuthDto loginAuthDto = cancelOrderDto.getLoginAuthDto();
        logger.info("操作人信息. loginAuthDto={}", loginAuthDto);

        int result = omcOrderService.cancelOrderDoc(loginAuthDto, cancelOrderDto.getOrderNo());
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper queryUserOrderDetailList(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId) {
        logger.info("queryUserOrderDetailList - 查询用户订单明细. orderNo={}, userId={}", orderNo, userId);

        OrderVo orderVo = omcOrderService.getOrderDetail(userId, orderNo);
        return WrapMapper.ok(orderVo);
    }

    @Override
    public Wrapper queryUserOrderDetail(@PathVariable("orderNo") String orderNo) {
        logger.info("queryUserOrderDetail - 查询订单明细. orderNo={}", orderNo);

        OrderVo orderVo = omcOrderService.getOrderDetail(orderNo);
        return WrapMapper.ok(orderVo);
    }

    @Override
    public Wrapper queryUserOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery) {
        logger.info("queryUserOrderListWithPage - 查询用户订单集合. orderPageQuery={}", orderPageQuery);

        Long userId = orderPageQuery.getUserId();
        logger.info("操作人信息. userId={}", userId);

        PageInfo pageInfo = omcOrderService.queryUserOrderListWithPage(userId, orderPageQuery);
        return WrapMapper.ok(pageInfo);
    }

    @Override
    public Wrapper queryOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery) {
        logger.info("queryOrderListWithPage - 查询订单集合. orderPageQuery={}", orderPageQuery);
        PageInfo pageInfo = omcOrderService.queryOrderListWithPage(orderPageQuery);
        return WrapMapper.ok(pageInfo);
    }

    @Override
    public Wrapper<Boolean> queryOrderPayStatus(@PathVariable("orderNo") String orderNo, @PathVariable("userId") Long userId) {
        logger.info("queryOrderPayStatus - 查询订单状态. orderNo={}", orderNo);
        boolean result = omcOrderService.queryOrderPayStatus(userId, orderNo);
        return WrapMapper.ok(result);
    }
}
