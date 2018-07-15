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

package com.paascloud.provider.model.service;

import com.paascloud.provider.model.dto.menu.*;
import com.paascloud.provider.model.service.hystrix.UacMenuFeignHystrix;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.menu.ViewMenuVo;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 菜单主页面.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacMenuFeignHystrix.class)
public interface UacMenuFeignApi{


	/**
	 * 获取菜单列表数据
	 *
	 * @param userId the user id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/getTree/{userId}")
	Wrapper<List<MenuVo>> queryMenuTreeList(@PathVariable("userId") Long userId);

	/**
	 * 编辑菜单.
	 *
	 * @param id the id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/queryById/{id}")
	Wrapper<ViewMenuVo> queryMenuVoById(@ApiParam(name = "id", value = "菜单id") @PathVariable("id") Long id);


	/**
	 * 根据id修改菜单的禁用状态
	 *
	 * @param uacMenuStatusDto the uac menu status dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/modifyStatus")
	Wrapper updateUacMenuStatusById(@ApiParam(name = "uacMenuStatusDto", value = "修改菜单状态Dto") @RequestBody UacMenuStatusDto uacMenuStatusDto);

	/**
	 * 新增菜单.
	 *
	 * @param uacMenuAddDto the uac menu add dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/save")
	Wrapper saveMenu(@ApiParam(name = "saveMenu", value = "保存菜单") @RequestBody UacEditMenuDto uacMenuAddDto);

	/**
	 * 根据id删除菜单
	 *
	 * @param id the id
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除菜单")
	Wrapper<Integer> deleteUacMenuById(@ApiParam(name = "id", value = "菜单id") @PathVariable("id") Long id);

	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param uacMenuCheckCodeDto the uac menu check code dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/checkMenuCode")
	@ApiOperation(httpMethod = "POST", value = "检测菜单编码是否已存在")
	Wrapper<Boolean> checkUacMenuActionCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody UacMenuCheckCodeDto uacMenuCheckCodeDto);

	/**
	 * 检测菜单名称唯一性
	 *
	 * @param uacMenuCheckNameDto the uac menu check name dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/checkMenuName")
	@ApiOperation(httpMethod = "POST", value = "检测菜单名称唯一性")
	Wrapper<Boolean> checkUacMenuName(@ApiParam(name = "uacMenuCheckNameDto", value = "id与name") @RequestBody UacMenuCheckNameDto uacMenuCheckNameDto);


	/**
	 * 检测菜单URL唯一性
	 *
	 * @param uacMenuCheckUrlDto the uac menu check url dto
	 * @return the wrapper
	 */
	@PostMapping(value = "/uac/menu/checkMenuUrl")
	@ApiOperation(httpMethod = "POST", value = "检测菜单URL唯一性")
	Wrapper<Boolean> checkUacMenuUrl(@ApiParam(name = "uacMenuCheckUrlDto", value = "id与url") @RequestBody UacMenuCheckUrlDto uacMenuCheckUrlDto);
}