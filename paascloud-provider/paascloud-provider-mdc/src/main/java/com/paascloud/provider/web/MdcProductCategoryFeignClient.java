/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.*;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.MdcProductCategoryFeignApi;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc product category feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
public class MdcProductCategoryFeignClient extends BaseFeignClient implements MdcProductCategoryFeignApi {

    @Resource
    private MdcProductCategoryService mdcProductCategoryService;

    @Resource
    private MdcProductService mdcProductService;

    @Override
    public Wrapper<Boolean> checkCategoryName(@RequestBody MdcCategoryCheckNameDto categoryCheckNameDto) {
        logger.info("检测数据分类名称是否已存在 categoryCheckNameDto={}", categoryCheckNameDto);

        Long id = categoryCheckNameDto.getCategoryId();
        String categoryName = categoryCheckNameDto.getCategoryName();

        Example example = new Example(MdcProductCategory.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("name", categoryName);

        int result = mdcProductCategoryService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<List<MdcCategoryVo>> queryCategoryTreeList() {
        List<MdcCategoryVo> categoryVoList = mdcProductCategoryService.getCategoryTreeList();
        return WrapMapper.ok(categoryVoList);
    }

    @Override
    public Wrapper<MdcCategoryVo> queryCategoryVoById(@PathVariable("id") Long id) {
        logger.info("根据Id查询商品分类信息, categoryId={}", id);
        MdcCategoryVo mdcCategoryVo = mdcProductCategoryService.getMdcCategoryVoById(id);
        return WrapMapper.ok(mdcCategoryVo);
    }


    @Override
    public Wrapper updateMdcCategoryStatusById(@RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("根据id修改商品分类的禁用状态 updateStatusDto={}", updateStatusDto);
        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();
        mdcProductCategoryService.updateMdcCategoryStatusById(updateStatusDto, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper saveCategory(@RequestBody MdcEditCategoryDto mdcCategoryAddDto) {
        MdcProductCategory mdcCategory = new MdcProductCategory();
        LoginAuthDto loginAuthDto = mdcCategoryAddDto.getLoginAuthDto();
        BeanUtils.copyProperties(mdcCategoryAddDto, mdcCategory);
        mdcProductCategoryService.saveMdcCategory(mdcCategory, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<Integer> deleteMdcCategoryById(@PathVariable("id") Long id) {
        logger.info(" 根据id删除商品分类 id={}", id);
        // 判断此商品分类是否有子节点
        boolean hasChild = mdcProductCategoryService.checkCategoryHasChildCategory(id);
        if (hasChild) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "此商品分类含有子商品分类, 请先删除子商品分类");
        }

        int result = mdcProductCategoryService.deleteByKey(id);
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid) {
        logger.info("获取商品品类信息. pid={}", pid);
        List<ProductCategoryDto> list;
        if (0L == pid) {
            // 查询所有一级分类

            list = mdcProductCategoryService.getCategoryDtoList(pid);
            for (ProductCategoryDto productCategoryDto : list) {
                Long categoryPid = productCategoryDto.getCategoryId();
                if (PublicUtil.isEmpty(categoryPid)) {
                    continue;
                }
                List<ProductCategoryDto> product2CategoryDtoList = mdcProductCategoryService.getCategoryDtoList(categoryPid);
                if (product2CategoryDtoList.size() > 5) {
                    product2CategoryDtoList = product2CategoryDtoList.subList(0, 4);
                }
                productCategoryDto.setCategoryList(product2CategoryDtoList);
            }

        } else {
            // 根据分类ID 查询分类下的所有二级分类
            list = mdcProductCategoryService.getCategoryDtoList(pid);
        }

        return WrapMapper.ok(list);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "获取商品列表信息")
    public Wrapper<PageInfo> getProductList(@RequestBody ProductReqDto productReqDto) {
        logger.info("获取商品列表信息. productReqDto={}", productReqDto);
        Long categoryId = productReqDto.getCategoryId();
        String keyword = productReqDto.getKeyword();
        Integer pageNum = productReqDto.getPageNum();
        Integer pageSize = productReqDto.getPageSize();
        String orderBy = productReqDto.getOrderBy();
        if (StringUtils.isBlank(keyword) && null == categoryId) {
            return WrapMapper.ok(new PageInfo());
        }
        List<Long> categoryIdList = Lists.newArrayList();

        if (categoryId != null) {
            MdcProductCategory category = mdcProductCategoryService.selectByKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                // 没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                PageHelper.startPage(pageNum, pageSize);
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, new PageInfo());
            }
            categoryIdList = mdcProductCategoryService.selectCategoryAndChildrenById(categoryId);
        }

        PageHelper.startPage(pageNum, pageSize);
        //排序处理
        List<MdcProduct> productList = mdcProductService.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, PublicUtil.isEmpty(categoryIdList) ? null : categoryIdList, orderBy);

        List<ProductDto> productListVoList = Lists.newArrayList();
        for (MdcProduct product : productList) {
            ProductDto productListVo = assembleProductListVo(product);
            String url = mdcProductService.getMainImage(product.getId());
            productListVo.setMainImage(url);
            productListVoList.add(productListVo);
        }


        return PublicUtil.isNotEmpty(productListVoList) ? WrapMapper.ok(new PageInfo<>(productListVoList)) : WrapMapper.ok();
    }

    private ProductDto assembleProductListVo(MdcProduct product) {
        ProductDto productListVo = new ProductDto();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getId());
        productListVo.setImageHost("");
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }
}
