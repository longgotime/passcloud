/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.annotation.NoNeedAccessAuthentication;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.MdcCategoryCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditCategoryDto;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.hystrix.MdcProductCategoryFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * The interface Mdc product category feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductCategoryFeignHystrix.class)
public interface MdcProductCategoryFeignApi {

    /**
     * 检测数据分类名称是否已存在.
     *
     * @param categoryCheckNameDto the category check name dto
     * @return the wrapper
     */
    @PostMapping(value = "/api/mdc/category/checkCategoryName")
    @ApiOperation(httpMethod = "POST", value = "检测数据分类名称是否已存在")
    Wrapper<Boolean> checkCategoryName(@RequestBody MdcCategoryCheckNameDto categoryCheckNameDto);

    /**
     * 获取商品分类列表数据
     *
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/mdc/category/getTree")
    @ApiOperation(httpMethod = "POST", value = "获取商品分类树")
    Wrapper<List<MdcCategoryVo>> queryCategoryTreeList();

    /**
     * 根据ID获取商品分类信息.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/mdc/category/queryById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
    Wrapper<MdcCategoryVo> queryCategoryVoById(@ApiParam(name = "id", value = "商品分类id") @PathVariable("id") Long id);


    /**
     * 根据id修改商品分类的禁用状态
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/mdc/category/modifyStatus")
    @ApiOperation(httpMethod = "POST", value = "根据id修改商品分类的禁用状态")
    Wrapper updateMdcCategoryStatusById(@ApiParam(name = "mdcCategoryStatusDto", value = "修改商品分类状态Dto") @RequestBody UpdateStatusDto updateStatusDto);

    /**
     * Save category wrapper.
     *
     * @param mdcCategoryAddDto the mdc category add dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/mdc/category/save")
    @ApiOperation(httpMethod = "POST", value = "编辑商品分类")
    Wrapper saveCategory(@ApiParam(name = "saveCategory", value = "编辑商品分类") @RequestBody MdcEditCategoryDto mdcCategoryAddDto);

    /**
     * 根据id删除商品分类
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/mdc/category/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据id删除商品分类")
    Wrapper<Integer> deleteMdcCategoryById(@ApiParam(name = "id", value = "商品分类id") @PathVariable("id") Long id);

    /**
     * 查询分类信息.
     *
     * @param pid the pid
     *
     * @return the product category data
     */
    @PostMapping(value = "/mdc/productCategory/getProductCategoryDtoByPid/{pid}")
    @NoNeedAccessAuthentication
    Wrapper<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid);

    /**
     * 查询商品列表.
     *
     * @param productReqDto the product req dto
     *
     * @return the product list
     */
    @PostMapping(value = "/mdc/product/getProductList")
    @NoNeedAccessAuthentication
    Wrapper<PageInfo> getProductList(@RequestBody ProductReqDto productReqDto);
}
