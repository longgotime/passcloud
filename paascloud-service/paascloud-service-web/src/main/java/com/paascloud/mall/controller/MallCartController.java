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

package com.paascloud.mall.controller;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.CartListQuery;
import com.paascloud.provider.model.service.MallCartFeignApi;
import com.paascloud.provider.model.vo.CartVo;
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
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallCartController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallCartController extends BaseController {

	@Resource
	private MallCartFeignApi mallCartFeignApi;



	/**
	 * 登录成功合并购物车.
	 *
	 * @param cartListQuery the cart list query
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "mergeUserCart")
	@ApiOperation(httpMethod = "POST", value = "登录成功合并购物车")
	public Wrapper<CartVo> mergeUserCart(@RequestBody CartListQuery cartListQuery) {
		return mallCartFeignApi.mergeUserCart(cartListQuery);
	}

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
	public Wrapper addProduct(@PathVariable Long productId, @PathVariable Integer count) {
		return mallCartFeignApi.addProduct(productId, count);
	}


	/**
	 * 购物车更新商品.
	 *
	 * @param productId the product id
	 * @param count     the count
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车更新商品")
	@PostMapping("updateProduct/{productId}/{count}")
	public Wrapper updateProduct(@PathVariable Long productId, @PathVariable Integer count) {
		return mallCartFeignApi.updateProduct(productId, count);
	}

	/**
	 * 购物车删除商品.
	 *
	 * @param productIds the product ids
	 *
	 * @return the wrapper
	 */
	@ApiOperation(httpMethod = "POST", value = "购物车删除商品")
	@PostMapping("deleteProduct/{productIds}")
	public Wrapper deleteProduct(@PathVariable String productIds) {
		return mallCartFeignApi.deleteProduct(productIds);
	}


	/**
	 * 购物车全选商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectAllProduct")
	@ApiOperation(httpMethod = "POST", value = "购物车全选商品")
	public Wrapper selectAll() {
//		LoginAuthDto loginAuthDto = getLoginAuthDto();
////		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.selectAll();
	}

	/**
	 * 购物车反选全部商品.
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectAllProduct")
	@ApiOperation(httpMethod = "POST", value = "购物车反选全部商品")
	public Wrapper unSelectAll() {
//		LoginAuthDto loginAuthDto = getLoginAuthDto();
//		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.unSelectAll();
	}


	/**
	 * 选中商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("selectProduct/{productId}")
	@ApiOperation(httpMethod = "POST", value = "选中商品")
	public Wrapper select(@PathVariable Long productId) {
//		LoginAuthDto loginAuthDto = getLoginAuthDto();
//		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.select(productId);
	}

	/**
	 * 反选商品.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping("unSelectProduct/{productId}")
	@ApiOperation(httpMethod = "POST", value = "反选商品")
	public Wrapper unSelect(@PathVariable Long productId) {
//		LoginAuthDto loginAuthDto = getLoginAuthDto();
//		Long userId = loginAuthDto.getUserId();
		return mallCartFeignApi.unSelect(productId);
	}
}
