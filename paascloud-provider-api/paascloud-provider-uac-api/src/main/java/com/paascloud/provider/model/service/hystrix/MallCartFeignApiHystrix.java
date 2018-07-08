/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallCartController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.dto.CartListQuery;
import com.paascloud.provider.model.service.MallCartFeignApi;
import com.paascloud.provider.model.vo.CartProductVo;
import com.paascloud.provider.model.vo.CartVo;
import com.paascloud.provider.service.OmcCartFeignApi;
import com.paascloud.provider.service.OmcCartQueryFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车管理.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MallCartFeignApiHystrix implements MallCartFeignApi {

	@Override
	public Wrapper<CartVo> mergeUserCart(CartListQuery cartListQuery) {
		return null;
	}

	@Override
	public Wrapper addProduct(Long productId, Integer count) {
		return null;
	}

	@Override
	public Wrapper updateProduct(Long productId, Integer count) {
		return null;
	}

	@Override
	public Wrapper deleteProduct(String productIds) {
		return null;
	}

	@Override
	public Wrapper selectAll() {
		return null;
	}

	@Override
	public Wrapper unSelectAll() {
		return null;
	}

	@Override
	public Wrapper select(Long productId) {
		return null;
	}

	@Override
	public Wrapper unSelect(Long productId) {
		return null;
	}
}
