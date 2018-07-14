/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.annotation.NoNeedAccessAuthentication;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.hystrix.MdcProductFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Mdc product feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductFeignHystrix.class)
public interface MdcProductFeignApi {

    /**
     * Update product stock by id int.
     *
     * @param productDto the product dto
     * @return the int
     */
    @PostMapping(value = "/api/product/updateProductStockById")
    Wrapper<Integer> updateProductStockById(@RequestBody ProductDto productDto);

    /**
     * Gets main image.
     *
     * @param productId the product id
     * @return the main image
     */
    @PostMapping(value = "/mdc/product/getMainImage")
    Wrapper<String> getMainImage(@RequestParam("productId") Long productId);

    /**
     * 分页查询商品列表.
     *
     * @param mdcProduct the mdc product
     * @return the wrapper
     */
    @PostMapping(value = "/queryProductListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
    Wrapper<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody ProductVo mdcProduct);

    /**
     * 商品详情.
     *
     * @param id the id
     * @return the by id
     */
    @PostMapping(value = "/getById/{id}")
    @ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
    Wrapper<ProductVo> getById(@PathVariable("id") Long id);

    /**
     * Save category wrapper.
     *
     * @param mdcCategoryAddDto the mdc category add dto
     * @return the wrapper
     */
    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "编辑商品")
    Wrapper saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto);

    /**
     * Delete product by id wrapper.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/deleteProductById/{id}")
    @ApiOperation(httpMethod = "POST", value = "删除商品信息")
    Wrapper<ProductVo> deleteProductById(@PathVariable("id") Long id);

    /**
     * 查询商品详情信息.
     *
     * @param productId the product id
     *
     * @return the product detail
     */
    @PostMapping(value = "/mdc/product/getProductDetail/{productId}")
    @NoNeedAccessAuthentication
    Wrapper<ProductDetailVo> getProductDetail(@PathVariable("productId") Long productId);

    /**
     * Select by id wrapper.
     *
     * @param productId the product id
     *
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/product/selectById/{productId}")
    @NoNeedAccessAuthentication
    Wrapper<ProductDto> selectById(@PathVariable("productId") Long productId);
}
