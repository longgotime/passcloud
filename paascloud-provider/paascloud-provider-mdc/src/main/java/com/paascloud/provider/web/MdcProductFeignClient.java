/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.paascloud.PubUtils;
import com.paascloud.PublicUtil;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.MdcProduct;
import com.paascloud.provider.model.dto.MdcEditProductDto;
import com.paascloud.provider.model.dto.ProductDto;
import com.paascloud.provider.model.vo.ProductDetailVo;
import com.paascloud.provider.model.vo.ProductVo;
import com.paascloud.provider.service.MdcProductFeignApi;
import com.paascloud.provider.service.MdcProductService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc product feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
public class MdcProductFeignClient extends BaseFeignClient implements MdcProductFeignApi {
	@Resource
	private MdcProductService mdcProductService;

	@Override
	public Wrapper<Integer> updateProductStockById(@RequestBody ProductDto productDto) {
		logger.info("更新商品库存. productDto={}", productDto);
		Preconditions.checkArgument(!PubUtils.isNull(productDto, productDto.getId()), ErrorCodeEnum.MDC10021021.msg());
		int result = mdcProductService.updateProductStockById(productDto);
		return WrapMapper.ok(result);
	}

	@Override
	public Wrapper<String> getMainImage(@RequestParam("productId") Long productId) {
		String mainImage = mdcProductService.getMainImage(productId);
		return WrapMapper.ok(mainImage);
	}

	@Override
	public Wrapper<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody ProductVo productVo) {

		logger.info("分页查询商品列表, mdcProduct={}", productVo);
		PageHelper.startPage(productVo.getPageNum(), productVo.getPageSize());
        productVo.setOrderBy("update_time desc");
		MdcProduct mdcProduct = new ModelMapper().map(productVo, MdcProduct.class);
		List<ProductVo> roleVoList = mdcProductService.queryProductListWithPage(mdcProduct);
		return WrapMapper.ok(new PageInfo<>(roleVoList));
	}

	@Override
	public Wrapper<ProductVo> getById(@PathVariable("id") Long id) {
		logger.info("查询商品详情, id={}", id);
		ProductVo productVo = mdcProductService.getProductVo(id);
		return WrapMapper.ok(productVo);
	}

	@Override
	public Wrapper saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto) {
		logger.info("编辑商品. mdcCategoryAddDto={}", mdcCategoryAddDto);
		mdcProductService.saveProduct(mdcCategoryAddDto, mdcCategoryAddDto.getLoginAuthDto());
		return WrapMapper.ok();
	}

	@Override
	public Wrapper<ProductVo> deleteProductById(@PathVariable("id") Long id) {
		logger.info("删除商品信息, id={}", id);
		mdcProductService.deleteProductById(id);
		return WrapMapper.ok();
	}

    @Override
    public Wrapper<ProductDetailVo> getProductDetail(@PathVariable("productId") Long productId) {
        logger.info("根据商品ID查询商品详细信息. productId={}", productId);
        ProductDetailVo productDto = mdcProductService.getProductDetail(productId);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, productDto);
    }

    @Override
    public Wrapper<ProductDto> selectById(@PathVariable("productId") Long productId) {
        logger.info("根据商品ID查询商品信息. productId={}", productId);
        ProductDto productDto = null;
        MdcProduct mdcProduct = mdcProductService.selectByKey(productId);
        if (PublicUtil.isNotEmpty(mdcProduct)) {
            productDto = new ProductDto();
            BeanUtils.copyProperties(mdcProduct, productDto);
        }
        return WrapMapper.ok(productDto);
    }
}
