/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcFileFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.service.OpcFileFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Opt file feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class OpcFileFeignHystrix implements OpcFileFeignApi {

	@Override
	public Wrapper<OptAttachmentRespDto> queryAttachment(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<List<OptAttachmentRespDto>> queryAttachmentListByRefNo(String refNo) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper deleteAttachment(Long attachmentId) {
		return WrapMapper.error();
	}
}
