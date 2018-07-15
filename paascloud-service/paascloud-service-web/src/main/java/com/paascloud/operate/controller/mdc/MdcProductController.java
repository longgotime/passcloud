/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.mdc;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.MdcProductFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductController extends BaseController {

    @Resource
    private MdcProductFeignApi mdcProductFeignApi;

    /**
     * 分页查询商品列表.
     *
     * @param mdcProduct the mdc product
     * @return the wrapper
     */
    @PostMapping(value = "/queryProductListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
    public Wrapper<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody ProductVo mdcProduct) {

        logger.info("分页查询商品列表, mdcProduct={}", mdcProduct);
        return mdcProductFeignApi.queryProductListWithPage(mdcProduct);
    }

    /**
     * 商品详情.
     */
    @PostMapping(value = "/getById/{id}")
    @ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
    public Wrapper<ProductVo> getById(@PathVariable Long id) {
        logger.info("查询商品详情, id={}", id);
        return mdcProductFeignApi.getById(id);
    }

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "编辑商品")
    @LogAnnotation
    public Wrapper saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto) {
        logger.info("编辑商品. mdcCategoryAddDto={}", mdcCategoryAddDto);
        mdcCategoryAddDto.setLoginAuthDto(getLoginAuthDto());
        return mdcProductFeignApi.saveCategory(mdcCategoryAddDto);
    }

    @PostMapping(value = "/deleteProductById/{id}")
    @ApiOperation(httpMethod = "POST", value = "删除商品信息")
    @LogAnnotation
    public Wrapper<ProductVo> deleteProductById(@PathVariable Long id) {
        logger.info("删除商品信息, id={}", id);
        return mdcProductFeignApi.deleteProductById(id);
    }
}
