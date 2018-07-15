/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcDictController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.mdc;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.MdcDictCheckCodeDto;
import com.paascloud.provider.model.dto.MdcDictCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditDictDto;
import com.paascloud.provider.model.vo.MdcDictVo;
import com.paascloud.provider.service.MdcDictFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/web/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcDictController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcDictController extends BaseController {

    @Resource
    private MdcDictFeignApi mdcDictFeignApi;

    /**
     * 获取字典列表数据
     *
     * @return the wrapper
     */
    @PostMapping(value = "/getTree")
    @ApiOperation(httpMethod = "POST", value = "获取字典树")
    public Wrapper<List<MdcDictVo>> queryDictTreeList() {
        return mdcDictFeignApi.queryDictTreeList();
    }

    /**
     * 根据ID获取字典信息.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/queryById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID获取字典信息")
    public Wrapper<MdcDictVo> queryDictVoById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
        logger.info("根据Id查询字典信息, dictId={}", id);
        return mdcDictFeignApi.queryDictVoById(id);
    }


    /**
     * 根据id修改字典的禁用状态
     *
     * @return the wrapper
     */
    @PostMapping(value = "/modifyStatus")
    @ApiOperation(httpMethod = "POST", value = "根据id修改字典的禁用状态")
    public Wrapper updateMdcDictStatusById(@ApiParam(name = "mdcDictStatusDto", value = "修改字典状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("根据id修改字典的禁用状态 updateStatusDto={}", updateStatusDto);
        LoginAuthDto loginAuthDto = super.getLoginAuthDto();
        updateStatusDto.setLoginAuthDto(loginAuthDto);
        return mdcDictFeignApi.updateMdcDictStatusById(updateStatusDto);
    }

    @PostMapping(value = "/save")
    @ApiOperation(httpMethod = "POST", value = "编辑字典")
    public Wrapper saveDict(@ApiParam(name = "saveDict", value = "编辑字典") @RequestBody MdcEditDictDto mdcDictAddDto) {
        LoginAuthDto loginAuthDto = getLoginAuthDto();
        mdcDictAddDto.setLoginAuthDto(loginAuthDto);
        return mdcDictFeignApi.saveDict(mdcDictAddDto);
    }

    /**
     * 根据id删除字典
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据id删除字典")
    public Wrapper<Integer> deleteMdcDictById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
        logger.info(" 根据id删除字典 id={}", id);
        return mdcDictFeignApi.deleteMdcDictById(id);
    }

    @PostMapping(value = "/checkDictCode")
    @ApiOperation(httpMethod = "POST", value = "检测数据字典编码是否已存在")
    public Wrapper<Boolean> checkDictCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckCodeDto mdcDictCheckCodeDto) {
        logger.info("检测数据字典编码是否已存在 mdcDictCheckCodeDto={}", mdcDictCheckCodeDto);
        return mdcDictFeignApi.checkDictCode(mdcDictCheckCodeDto);
    }

    /**
     * 检测数据字典名称是否已存在.
     *
     * @param mdcDictCheckNameDto the mdc dict check name dto
     * @return the wrapper
     */
    @PostMapping(value = "/checkDictName")
    @ApiOperation(httpMethod = "POST", value = "检测数据字典名称是否已存在")
    public Wrapper<Boolean> checkDictName(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckNameDto mdcDictCheckNameDto) {
        logger.info("检测数据字典名称是否已存在 mdcDictCheckNameDto={}", mdcDictCheckNameDto);

        return mdcDictFeignApi.checkDictName(mdcDictCheckNameDto);
    }
}