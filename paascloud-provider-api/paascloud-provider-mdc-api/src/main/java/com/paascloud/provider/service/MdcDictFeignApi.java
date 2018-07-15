/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcDictMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.MdcDictCheckCodeDto;
import com.paascloud.provider.model.dto.MdcDictCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditDictDto;
import com.paascloud.provider.model.vo.MdcDictVo;
import com.paascloud.provider.service.hystrix.MdcDictFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The class Mdc dict feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Api(value = "Feign - MdcDictMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@FeignClient(value = "paascloud-provider-mdc", configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcDictFeignHystrix.class)
public interface MdcDictFeignApi {

    /**
     * 获取字典列表数据
     *
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/getTree")
    @ApiOperation(httpMethod = "POST", value = "获取字典树")
    Wrapper<List<MdcDictVo>> queryDictTreeList();

    /**
     * 根据ID获取字典信息.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/queryById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取字典信息")
    Wrapper<MdcDictVo> queryDictVoById(@ApiParam(name = "id", value = "字典id") @PathVariable("id") Long id);


    /**
     * 根据id修改字典的禁用状态
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/modifyStatus")
    @ApiOperation(httpMethod = "POST", value = "根据id修改字典的禁用状态")
    Wrapper updateMdcDictStatusById(@ApiParam(name = "mdcDictStatusDto", value = "修改字典状态Dto") @RequestBody UpdateStatusDto updateStatusDto);

    /**
     * Save dict wrapper.
     *
     * @param mdcDictAddDto the mdc dict add dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/save")
    @ApiOperation(httpMethod = "POST", value = "编辑字典")
    Wrapper saveDict(@ApiParam(name = "saveDict", value = "编辑字典") @RequestBody MdcEditDictDto mdcDictAddDto);

    /**
     * 根据id删除字典
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据id删除字典")
    Wrapper<Integer> deleteMdcDictById(@ApiParam(name = "id", value = "字典id") @PathVariable("id") Long id);

    /**
     * 检测菜单编码是否已存在
     *
     * @param mdcDictCheckCodeDto the mdc dict check code dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/checkDictCode")
    @ApiOperation(httpMethod = "POST", value = "检测数据字典编码是否已存在")
    Wrapper<Boolean> checkDictCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckCodeDto mdcDictCheckCodeDto);

    /**
     * 检测数据字典名称是否已存在.
     *
     * @param mdcDictCheckNameDto the mdc dict check name dto
     * @return the wrapper
     */
    @PostMapping(value = "/mdc/dict/checkDictName")
    @ApiOperation(httpMethod = "POST", value = "检测数据字典名称是否已存在")
    Wrapper<Boolean> checkDictName(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckNameDto mdcDictCheckNameDto);
}