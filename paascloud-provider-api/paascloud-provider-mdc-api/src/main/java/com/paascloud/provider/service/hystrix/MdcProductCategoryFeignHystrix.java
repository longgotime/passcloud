/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.MdcCategoryCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditCategoryDto;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.MdcProductCategoryFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Mdc product category feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductCategoryFeignHystrix implements MdcProductCategoryFeignApi {
    @Override
    public Wrapper<Boolean> checkCategoryName(MdcCategoryCheckNameDto categoryCheckNameDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<MdcCategoryVo>> queryCategoryTreeList() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<MdcCategoryVo> queryCategoryVoById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateMdcCategoryStatusById(UpdateStatusDto updateStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper saveCategory(MdcEditCategoryDto mdcCategoryAddDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Integer> deleteMdcCategoryById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<ProductCategoryDto>> getProductCategoryData(Long pid) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo> getProductList(ProductReqDto productReqDto) {
        return WrapMapper.error();
    }
}
