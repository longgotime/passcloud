/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressQueryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.TreeNode;
import com.paascloud.provider.model.dto.AddressDTO;
import com.paascloud.provider.service.hystrix.MdcAddressFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * The interface Mdc product query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcAddressFeignHystrix.class)
public interface MdcAddressFeignApi {

	/**
	 * 根据ID获取地址信息.
	 *
	 * @param addressId the address id
	 *
	 * @return the by id
	 */
	@PostMapping(value = "/mdc/address/getById/{addressId}")
	Wrapper<AddressDTO> getById(@PathVariable("addressId") Long addressId);

	/**
	 * Gets 4 city.
	 *
	 * @return the 4 city
	 */
	@PostMapping(value = "/mdc/address/get4City")
	@ApiOperation(httpMethod = "POST", value = "查看四级地址")
	Wrapper<List<TreeNode>> get4City();
}
