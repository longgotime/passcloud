/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.PublicUtil;
import com.paascloud.TreeNode;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.MdcAddress;
import com.paascloud.provider.model.dto.AddressDTO;
import com.paascloud.provider.service.MdcAddressFeignApi;
import com.paascloud.provider.service.MdcAddressService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc product query feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
public class MdcAddressFeignClient extends BaseFeignClient implements MdcAddressFeignApi {

	@Resource
	private MdcAddressService mdcAddressService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据ID获取地址信息")
	public Wrapper<AddressDTO> getById(@PathVariable("addressId") Long addressId) {
		logger.info("根据ID获取地址信息 addressId={}", addressId);
		AddressDTO addressDTO = null;
		MdcAddress mdcAddress = mdcAddressService.selectByKey(addressId);
		if (PublicUtil.isNotEmpty(mdcAddress)) {
			addressDTO = new AddressDTO();
			BeanUtils.copyProperties(mdcAddress, addressDTO);
		}
		return WrapMapper.ok(addressDTO);
	}

	@Override
	public Wrapper<List<TreeNode>> get4City() {
		logger.info("get4City - 获取四级地址");
		List<TreeNode> treeNodeList = mdcAddressService.get4City();
		return WrapMapper.ok(treeNodeList);
	}
}
