/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.mdc;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.MdcCategoryCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditCategoryDto;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.MdcProductCategoryFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc category main controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/web/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductCategoryController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductCategoryController extends BaseController {

    @Resource
    private MdcProductCategoryFeignApi mdcProductCategoryFeignApi;

    /**
     * 获取商品分类列表数据
     *
     * @return the wrapper
     */
    @PostMapping(value = "/getTree")
    @ApiOperation(httpMethod = "POST", value = "获取商品分类树")
    public Wrapper<List<MdcCategoryVo>> queryCategoryTreeList() {
        return mdcProductCategoryFeignApi.queryCategoryTreeList();
    }

    /**
     * 根据ID获取商品分类信息.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/queryById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取商品分类信息")
    public Wrapper<MdcCategoryVo> queryCategoryVoById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id) {
        logger.info("根据Id查询商品分类信息, categoryId={}", id);
        return mdcProductCategoryFeignApi.queryCategoryVoById(id);
    }


    /**
     * 根据id修改商品分类的禁用状态
     *
     * @return the wrapper
     */
    @PostMapping(value = "/modifyStatus")
    @ApiOperation(httpMethod = "POST", value = "根据id修改商品分类的禁用状态")
    @LogAnnotation
    public Wrapper updateMdcCategoryStatusById(@ApiParam(name = "mdcCategoryStatusDto", value = "修改商品分类状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("根据id修改商品分类的禁用状态 updateStatusDto={}", updateStatusDto);
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        updateStatusDto.setLoginAuthDto(loginAuthDto);
        return mdcProductCategoryFeignApi.updateMdcCategoryStatusById(updateStatusDto);
    }

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "编辑商品分类")
    @LogAnnotation
    public Wrapper saveCategory(@ApiParam(name = "saveCategory", value = "编辑商品分类") @RequestBody MdcEditCategoryDto mdcCategoryAddDto) {
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        mdcCategoryAddDto.setLoginAuthDto(loginAuthDto);
        return mdcProductCategoryFeignApi.saveCategory(mdcCategoryAddDto);
    }

    /**
     * 根据id删除商品分类
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据id删除商品分类")
    @LogAnnotation
    public Wrapper<Integer> deleteMdcCategoryById(@ApiParam(name = "id", value = "商品分类id") @PathVariable Long id) {
        logger.info(" 根据id删除商品分类 id={}", id);
        return mdcProductCategoryFeignApi.deleteMdcCategoryById(id);
    }

    /**
     * 检测数据分类名称是否已存在.
     *
     * @param categoryCheckNameDto the category check name dto
     * @return the wrapper
     */
    @PostMapping(value = "/checkCategoryName")
    @ApiOperation(httpMethod = "POST", value = "检测数据分类名称是否已存在")
    public Wrapper<Boolean> checkCategoryName(@RequestBody MdcCategoryCheckNameDto categoryCheckNameDto) {
        logger.info("检测数据分类名称是否已存在 categoryCheckNameDto={}", categoryCheckNameDto);

        return mdcProductCategoryFeignApi.checkCategoryName(categoryCheckNameDto);
    }
}