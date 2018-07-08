/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.OmcShippingDTO;
import com.paascloud.provider.model.dto.OmcShippingQuery;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * The class Omc shipping controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/shipping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OmcShippingController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface OmcShippingFeignApi {

	/**
	 * 增加收货人地址.
	 *
	 * @param shipping the shipping
	 * @return the wrapper
	 */
	@PostMapping("/addShipping")
	@ApiOperation(httpMethod = "POST", value = "增加收货人地址")
	Wrapper addShipping(OmcShippingDTO shipping);

	/**
	 * 删除收货人地址.
	 *
	 * @param shippingId the shipping id
	 * @param userId     the user id
	 * @return the wrapper
	 */
	@PostMapping("/deleteShipping/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "删除收货人地址")
	Wrapper deleteShipping(@PathVariable Long shippingId, @PathVariable Long userId);

	/**
	 * 编辑收货人地址.
	 *
	 * @param shipping the shipping
	 * @return the wrapper
	 */
	@PostMapping("/updateShipping")
	@ApiOperation(httpMethod = "POST", value = "编辑收货人地址")
	Wrapper updateShipping(OmcShippingDTO shipping);

	/**
	 * 设置默认收货地址.
	 *
	 * @param omcShippingDTO the omc shipping dto
	 * @return the default address
	 */
	@PostMapping("/setDefaultAddress")
	@ApiOperation(httpMethod = "POST", value = "设置默认收货地址")
	Wrapper setDefaultAddress(@RequestBody OmcShippingDTO omcShippingDTO);

	/**
	 * 根据Id查询收货人地址.
	 *
	 * @param shippingId the shipping id
	 * @param userId     the user id
	 * @return the wrapper
	 */
	@PostMapping("/selectShippingById/{shippingId}/{userId}")
	@ApiOperation(httpMethod = "POST", value = "根据Id查询收货人地址")
	Wrapper<OmcShippingDTO> selectShippingById(@PathVariable("shippingId") Long shippingId, @PathVariable("userId") Long userId);

	/**
	 * 分页查询当前用户收货人地址列表.
	 *
	 * @param shipping the shipping
	 * @return the wrapper
	 */
	@PostMapping("queryUserShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询当前用户收货人地址列表")
	Wrapper<PageInfo> queryUserShippingListWithPage(@RequestBody OmcShippingQuery shipping);

	/**
	 * 分页查询收货人地址列表.
	 *
	 * @param shipping the shipping
	 * @return the wrapper
	 */
	@PostMapping("queryShippingListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询收货人地址列表")
	Wrapper<PageInfo> queryShippingListWithPage(@RequestBody OmcShippingQuery shipping);

}
