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

package com.paascloud.operate.controller.uac;

import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.action.ActionMainQueryDto;
import com.paascloud.provider.model.dto.action.UacActionCheckCodeDto;
import com.paascloud.provider.model.dto.action.UacActionCheckUrlDto;
import com.paascloud.provider.model.dto.action.UacActionDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.model.service.UacActionFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Uac action controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacActionMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacActionController extends BaseController{

	@Resource
	private UacActionFeignApi uacActionFeignApi;

	/**
	 * 分页查询角色信息.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询角色列表")
	public Wrapper queryUacActionListWithPage(@RequestBody ActionMainQueryDto action) {
		logger.info("查询角色列表actionQuery={}", action);
		return uacActionFeignApi.queryUacActionListWithPage(action);
	}


	/**
	 * 删除角色信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteActionById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除角色")
	@LogAnnotation
	public Wrapper deleteUacActionById(@PathVariable Long id) {
		return uacActionFeignApi.deleteUacActionById(id);
	}

	/**
	 * 批量删除角色.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/batchDeleteByIdList")
	@ApiOperation(httpMethod = "POST", value = "批量删除角色")
	public Wrapper batchDeleteByIdList(@RequestBody List<Long> deleteIdList) {
		logger.info("批量删除角色 idList={}", deleteIdList);
		return uacActionFeignApi.batchDeleteByIdList(deleteIdList);
	}


	/**
	 * 保存权限信息.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增角色")
	public Wrapper save(@RequestBody UacActionDto action) {
		action.setLoginAuthDto(getLoginAuthDto());
		return uacActionFeignApi.save(action);
	}

	/**
	 * 根据权限Id修改角色状态.
	 *
	 * @param modifyStatusDto the modify status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "根据权限Id修改角色状态")
	@LogAnnotation
	public Wrapper modifyActionStatus(@RequestBody ModifyStatusDto modifyStatusDto) {
		modifyStatusDto.setLoginAuthDto(getLoginAuthDto());

		return uacActionFeignApi.modifyActionStatus(modifyStatusDto);
	}

	/**
	 * 检测权限编码是否已存在
	 *
	 * @param uacActionCheckCodeDto the uac action check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkActionCode")
	@ApiOperation(httpMethod = "POST", value = "检测权限编码是否已存在")
	public Wrapper<Boolean> checkActionCode(@RequestBody UacActionCheckCodeDto uacActionCheckCodeDto) {
		logger.info("校验权限编码唯一性 uacActionCheckCodeDto={}", uacActionCheckCodeDto);
		return uacActionFeignApi.checkActionCode(uacActionCheckCodeDto);
	}

	/**
	 * 检测权限URL唯一性
	 *
	 * @param uacActionCheckUrlDto the uac action check url dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUrl")
	@ApiOperation(httpMethod = "POST", value = "检测权限URL唯一性")
	public Wrapper<Boolean> checkActionUrl(@RequestBody UacActionCheckUrlDto uacActionCheckUrlDto) {
		logger.info("检测权限URL唯一性 uacActionCheckUrlDto={}", uacActionCheckUrlDto);
		return uacActionFeignApi.checkActionUrl(uacActionCheckUrlDto);
	}
}
