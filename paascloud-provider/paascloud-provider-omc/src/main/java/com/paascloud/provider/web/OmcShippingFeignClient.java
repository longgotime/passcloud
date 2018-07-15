/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.OmcShipping;
import com.paascloud.provider.model.dto.OmcShippingDTO;
import com.paascloud.provider.model.dto.OmcShippingQuery;
import com.paascloud.provider.service.OmcShippingFeignApi;
import com.paascloud.provider.service.OmcShippingService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Omc shipping feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@Api(value = "WEB - OmcShippingController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcShippingFeignClient extends BaseFeignClient implements OmcShippingFeignApi {

	@Resource
	private OmcShippingService omcShippingService;

	@Override
	public Wrapper addShipping(@RequestBody OmcShippingDTO shipping) {

		logger.info("addShipping - 增加收货人地址. shipping={}", shipping);

		OmcShipping omcShipping = new ModelMapper().map(shipping, OmcShipping.class);

		int result = omcShippingService.saveShipping(shipping.getLoginAuthDto(), omcShipping);
		return WrapMapper.handleResult(result);

	}


	@Override
	public Wrapper deleteShipping(@PathVariable("shippingId") Long shippingId, @PathVariable("userId") Long userId) {
		logger.info("deleteShipping - 删除收货人地址. userId={}, shippingId={}", userId, shippingId);
		int result = omcShippingService.deleteShipping(userId, shippingId);
		return WrapMapper.handleResult(result);
	}

	@Override
	public Wrapper updateShipping(@RequestBody OmcShippingDTO shipping) {
		logger.info("updateShipping - 编辑收货人地址. shipping={}", shipping);
		OmcShipping omcShipping = new ModelMapper().map(shipping, OmcShipping.class);
		int result = omcShippingService.saveShipping(shipping.getLoginAuthDto(), omcShipping);
		return WrapMapper.handleResult(result);
	}

	@Override
	public Wrapper setDefaultAddress(@RequestBody OmcShippingDTO omcShippingDTO) {
		logger.info("updateShipping - 设置默认地址. omcShippingDTO={}", omcShippingDTO);
		int result = omcShippingService.setDefaultAddress(omcShippingDTO.getLoginAuthDto(), omcShippingDTO.getId());
		return WrapMapper.handleResult(result);
	}

	@Override
	public Wrapper<OmcShippingDTO> selectShippingById(@PathVariable("shippingId") Long shippingId, @PathVariable("userId") Long userId) {
		logger.info("selectShippingById - 根据Id查询收货人地址. userId={}, shippingId={}", userId, shippingId);
		OmcShipping omcShipping = omcShippingService.selectByShippingIdUserId(userId, shippingId);
		OmcShippingDTO result = new ModelMapper().map(omcShipping, OmcShippingDTO.class);
		return WrapMapper.ok(result);
	}

	@Override
	public Wrapper<PageInfo> queryUserShippingListWithPage(@RequestBody OmcShippingQuery shipping) {
		Long userId = shipping.getUserId();
		logger.info("queryUserShippingListWithPage - 分页查询当前用户收货人地址列表.userId={} shipping={}", userId, shipping);
		PageInfo pageInfo = omcShippingService.queryListWithPageByUserId(userId, shipping.getPageNum(), shipping.getPageSize());
		return WrapMapper.ok(pageInfo);
	}

	@Override
	public Wrapper<PageInfo> queryShippingListWithPage(@RequestBody OmcShippingQuery shipping) {
		logger.info("queryShippingListWithPage - 分页查询收货人地址列表. shipping={}", shipping);
		OmcShipping omcShipping = new ModelMapper().map(shipping, OmcShipping.class);
		PageInfo pageInfo = omcShippingService.queryShippingListWithPage(omcShipping);
		return WrapMapper.ok(pageInfo);
	}

}
