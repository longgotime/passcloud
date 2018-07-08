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

package com.paascloud.provider.model.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.dto.ProductReqDto;
import com.paascloud.provider.model.service.MallAuthFeignApi;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The class Mall auth rest controller.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MallAuthFeignApiHystrix implements MallAuthFeignApi {


    @Override
    public Wrapper<PageInfo> queryProductList(ProductReqDto productReqDto) {
        return null;
    }

    @Override
    public Wrapper<ProductDetailVo> queryProductDetail(Long productId) {
        return null;
    }

    @Override
    public Wrapper<List<ProductCategoryDto>> getProductCategoryDtoByPid(Long pid) {
        return null;
    }
}
