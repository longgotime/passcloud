/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.MdcProductFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductFeignHystrix implements MdcProductFeignApi {
	@Override
	public Wrapper<Integer> updateProductStockById(ProductDto productDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<String> getMainImage(Long productId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<PageInfo<ProductVo>> queryProductListWithPage(ProductVo mdcProduct) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<ProductVo> getById(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper saveCategory(MdcEditProductDto mdcCategoryAddDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<ProductVo> deleteProductById(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<ProductDetailVo> getProductDetail(Long productId) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<ProductDto> selectById(Long productId) {
		return WrapMapper.error();
	}
}
