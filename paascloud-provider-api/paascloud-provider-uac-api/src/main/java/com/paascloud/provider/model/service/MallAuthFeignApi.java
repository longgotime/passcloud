/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallAuthRestController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.model.service.hystrix.MallAuthFeignApiHystrix;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * The class Mall auth rest controller.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac" , configuration = OAuth2FeignAutoConfiguration.class, fallback = MallAuthFeignApiHystrix.class)
public interface MallAuthFeignApi{


	/**
	 * 查询商品列表.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product category dto by pid
	 */
	@PostMapping(value = "/product/queryProductList")
	Wrapper<PageInfo> queryProductList(@RequestBody ProductReqDto productReqDto);

	/**
	 * 查询商品详情信息.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/product/queryProductDetail/{productId}")
	Wrapper<ProductDetailVo> queryProductDetail(@PathVariable Long productId);

	/**
	 * 查询分类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category dto by pid
	 */
	@GetMapping(value = "/category/getProductCategoryDtoByPid/{pid}")
	Wrapper<List<ProductCategoryDto>> getProductCategoryDtoByPid(@PathVariable Long pid);
}
