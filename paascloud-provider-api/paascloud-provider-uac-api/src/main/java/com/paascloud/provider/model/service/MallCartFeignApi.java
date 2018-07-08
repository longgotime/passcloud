/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallCartController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service;

import com.paascloud.provider.model.dto.CartListQuery;
import com.paascloud.provider.model.service.hystrix.MallCartFeignApiHystrix;
import com.paascloud.provider.model.vo.CartVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 购物车管理.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac" , configuration = OAuth2FeignAutoConfiguration.class, fallback = MallCartFeignApiHystrix.class)
public interface MallCartFeignApi{

	/**
	 * 登录成功合并购物车.
	 *
	 * @param cartListQuery the cart list query
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "mergeUserCart")
	Wrapper<CartVo> mergeUserCart(@RequestBody CartListQuery cartListQuery);

	/**
	 * 购物车添加商品.
	 *
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@PostMapping("addProduct/{productId}/{count}")
	@ApiOperation(httpMethod = "POST", value = "购物车添加商品")
	Wrapper addProduct(@PathVariable Long productId, @PathVariable Integer count);


	/**
	 * 购物车更新商品.
	 *
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车更新商品")
	Wrapper updateProduct(@PathVariable Long productId, @PathVariable Integer count);

	/**
	 * 购物车删除商品.
	 *
	 * @param productIds the product ids
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车删除商品")
	Wrapper deleteProduct(@PathVariable String productIds);


	/**
	 * 购物车全选商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectAllProduct")
	Wrapper selectAll();

	/**
	 * 购物车反选全部商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectAllProduct")
	Wrapper unSelectAll();


	/**
	 * 选中商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectProduct/{productId}")
	Wrapper select(@PathVariable Long productId);

	/**
	 * 反选商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectProduct/{productId}")
	Wrapper unSelect(@PathVariable Long productId);
}
