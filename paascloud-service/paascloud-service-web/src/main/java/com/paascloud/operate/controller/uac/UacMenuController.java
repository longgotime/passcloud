/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.uac;

import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.menu.*;
import com.paascloud.provider.model.service.UacMenuFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.menu.ViewMenuVo;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacMenuController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacMenuController extends BaseController{

	@Resource
	private UacMenuFeignApi uacMenuFeignApi;

	/**
	 * 获取菜单列表数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<List<MenuVo>> queryMenuTreeList() {
		return uacMenuFeignApi.queryMenuTreeList(getLoginAuthDto().getUserId());
	}

	/**
	 * 编辑菜单.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "编辑菜单")
	public Wrapper<ViewMenuVo> queryMenuVoById(@PathVariable Long id) {
		return uacMenuFeignApi.queryMenuVoById(id);
	}

	/**
	 * 根据id修改菜单的禁用状态
	 *
	 * @param uacMenuStatusDto the uac menu status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "修改菜单状态")
	@LogAnnotation
	public Wrapper updateUacMenuStatusById(@RequestBody UacMenuStatusDto uacMenuStatusDto) {
		logger.info("根据id修改菜单的禁用状态 uacMenuStatusDto={}", uacMenuStatusDto);
		uacMenuStatusDto.setLoginAuthDto(getLoginAuthDto());

		return uacMenuFeignApi.updateUacMenuStatusById(uacMenuStatusDto);
	}

	/**
	 * 新增菜单.
	 *
	 * @param uacMenuAddDto the uac menu add dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增菜单")
	@LogAnnotation
	public Wrapper saveMenu(@RequestBody UacEditMenuDto uacMenuAddDto) {
		uacMenuAddDto.setLoginAuthDto(getLoginAuthDto());

		return uacMenuFeignApi.saveMenu(uacMenuAddDto);
	}

	/**
	 * 根据id删除菜单
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除菜单")
	@LogAnnotation
	public Wrapper<Integer> deleteUacMenuById(@PathVariable Long id) {
		logger.info(" 根据id删除菜单 id={}", id);
		return uacMenuFeignApi.deleteUacMenuById(id);
	}


	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param uacMenuCheckCodeDto the uac menu check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuCode")
	@ApiOperation(httpMethod = "POST", value = "检测菜单编码是否已存在")
	public Wrapper<Boolean> checkUacMenuActionCode(@RequestBody UacMenuCheckCodeDto uacMenuCheckCodeDto) {
		logger.info("校验菜单编码唯一性 uacMenuCheckCodeDto={}", uacMenuCheckCodeDto);
		return uacMenuFeignApi.checkUacMenuActionCode(uacMenuCheckCodeDto);
	}

	/**
	 * 检测菜单名称唯一性
	 *
	 * @param uacMenuCheckNameDto the uac menu check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuName")
	@ApiOperation(httpMethod = "POST", value = "检测菜单名称唯一性")
	public Wrapper<Boolean> checkUacMenuName(@RequestBody UacMenuCheckNameDto uacMenuCheckNameDto) {
		logger.info("校验菜单编码唯一性 uacMenuCheckCodeDto={}", uacMenuCheckNameDto);
		return uacMenuFeignApi.checkUacMenuName(uacMenuCheckNameDto);
	}

	/**
	 * 检测菜单URL唯一性
	 *
	 * @param uacMenuCheckUrlDto the uac menu check url dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuUrl")
	@ApiOperation(httpMethod = "POST", value = "检测菜单URL唯一性")
	public Wrapper<Boolean> checkUacMenuUrl(@RequestBody UacMenuCheckUrlDto uacMenuCheckUrlDto) {
		logger.info("检测菜单URL唯一性 uacMenuCheckUrlDto={}", uacMenuCheckUrlDto);
		return uacMenuFeignApi.checkUacMenuUrl(uacMenuCheckUrlDto);
	}
}