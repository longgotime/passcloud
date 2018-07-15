/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.provider.model.dto.action.ActionMainQueryDto;
import com.paascloud.provider.model.dto.action.UacActionCheckCodeDto;
import com.paascloud.provider.model.dto.action.UacActionCheckUrlDto;
import com.paascloud.provider.model.dto.action.UacActionDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.service.UacActionFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The type Uac action feign api hystrix.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 /7/1 上午11:18
 */
@Component
public class UacActionFeignHystrix implements UacActionFeignApi {

	@Override
	public Wrapper queryUacActionListWithPage(ActionMainQueryDto action) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper deleteUacActionById(Long id) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper batchDeleteByIdList(List<Long> deleteIdList) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper save(UacActionDto action) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper modifyActionStatus(ModifyStatusDto modifyStatusDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkActionCode(UacActionCheckCodeDto uacActionCheckCodeDto) {
		return WrapMapper.error();
	}

	@Override
	public Wrapper<Boolean> checkActionUrl(UacActionCheckUrlDto uacActionCheckUrlDto) {
		return WrapMapper.error();
	}
}
