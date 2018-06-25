/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.rpc;

import com.paascloud.provider.model.dto.action.ActionMainQueryDto;
import com.paascloud.provider.model.dto.action.UacActionCheckCodeDto;
import com.paascloud.provider.model.dto.action.UacActionCheckUrlDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.service.UacActionFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * The class Uac action main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacActionFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacActionFeignClient implements UacActionFeignApi {


	@Override
	public Wrapper queryUacActionListWithPage(ActionMainQueryDto action) {
		return null;
	}

	@Override
	public Wrapper deleteUacActionById(Long id) {
		return null;
	}

	@Override
	public Wrapper batchDeleteByIdList(List<Long> deleteIdList) {
		return null;
	}

	@Override
	public Wrapper save(ActionMainQueryDto action) {
		return null;
	}

	@Override
	public Wrapper modifyActionStatus(ModifyStatusDto modifyStatusDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkActionCode(UacActionCheckCodeDto uacActionCheckCodeDto) {
		return null;
	}

	@Override
	public Wrapper<Boolean> checkActionUrl(UacActionCheckUrlDto uacActionCheckUrlDto) {
		return null;
	}
}
