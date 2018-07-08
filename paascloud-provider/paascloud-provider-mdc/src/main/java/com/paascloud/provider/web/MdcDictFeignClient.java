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

package com.paascloud.provider.web;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.MdcDict;
import com.paascloud.provider.model.dto.MdcDictCheckCodeDto;
import com.paascloud.provider.model.dto.MdcDictCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditDictDto;
import com.paascloud.provider.model.vo.MdcDictVo;
import com.paascloud.provider.service.MdcDictFeignApi;
import com.paascloud.provider.service.MdcDictService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@Api(value = "Feign - MdcDictMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcDictFeignClient extends BaseFeignClient implements MdcDictFeignApi {

    @Resource
    private MdcDictService mdcDictService;

    @Override
    public Wrapper<List<MdcDictVo>> queryDictTreeList() {
        List<MdcDictVo> dictVoList = mdcDictService.getDictTreeList();
        return WrapMapper.ok(dictVoList);
    }

    @Override
    public Wrapper<MdcDictVo> queryDictVoById(@ApiParam(name = "id", value = "字典id") @PathVariable("id") Long id) {
        logger.info("根据Id查询字典信息, dictId={}", id);
        MdcDictVo mdcDictVo = mdcDictService.getMdcDictVoById(id);
        return WrapMapper.ok(mdcDictVo);
    }


    @Override
    public Wrapper updateMdcDictStatusById(@ApiParam(name = "mdcDictStatusDto", value = "修改字典状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("根据id修改字典的禁用状态 updateStatusDto={}", updateStatusDto);
        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();
        mdcDictService.updateMdcDictStatusById(updateStatusDto, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper saveDict(@ApiParam(name = "saveDict", value = "编辑字典") @RequestBody MdcEditDictDto mdcDictAddDto) {
        MdcDict mdcDict = new MdcDict();
        LoginAuthDto loginAuthDto = mdcDictAddDto.getLoginAuthDto();
        BeanUtils.copyProperties(mdcDictAddDto, mdcDict);
        mdcDictService.saveMdcDict(mdcDict, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<Integer> deleteMdcDictById(@ApiParam(name = "id", value = "字典id") @PathVariable("id") Long id) {
        logger.info(" 根据id删除字典 id={}", id);
        // 判断此字典是否有子节点
        boolean hasChild = mdcDictService.checkDictHasChildDict(id);
        if (hasChild) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "此字典含有子字典, 请先删除子字典");
        }

        int result = mdcDictService.deleteByKey(id);
        return WrapMapper.handleResult(result);
    }


    @Override
    public Wrapper<Boolean> checkDictCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckCodeDto mdcDictCheckCodeDto) {
        logger.info("检测数据字典编码是否已存在 mdcDictCheckCodeDto={}", mdcDictCheckCodeDto);

        Long id = mdcDictCheckCodeDto.getDictId();
        String dictCode = mdcDictCheckCodeDto.getDictCode();

        Example example = new Example(MdcDict.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("dictCode", dictCode);

        int result = mdcDictService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkDictName(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckNameDto mdcDictCheckNameDto) {
        logger.info("检测数据字典名称是否已存在 mdcDictCheckNameDto={}", mdcDictCheckNameDto);

        Long id = mdcDictCheckNameDto.getDictId();
        String dictName = mdcDictCheckNameDto.getDictName();

        Example example = new Example(MdcDict.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("dictName", dictName);

        int result = mdcDictService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }
}