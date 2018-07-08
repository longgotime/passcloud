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

package com.paascloud.provider.service.hystrix;

import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.MdcDictCheckCodeDto;
import com.paascloud.provider.model.dto.MdcDictCheckNameDto;
import com.paascloud.provider.model.dto.MdcEditDictDto;
import com.paascloud.provider.model.vo.MdcDictVo;
import com.paascloud.provider.service.MdcDictFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "Feign - MdcDictMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcDictFeignApiHystrix implements MdcDictFeignApi {

    @Override
    public Wrapper<List<MdcDictVo>> queryDictTreeList() {
        return null;
    }

    @Override
    public Wrapper<MdcDictVo> queryDictVoById(Long id) {
        return null;
    }

    @Override
    public Wrapper updateMdcDictStatusById(UpdateStatusDto updateStatusDto) {
        return null;
    }

    @Override
    public Wrapper saveDict(MdcEditDictDto mdcDictAddDto) {
        return null;
    }

    @Override
    public Wrapper<Integer> deleteMdcDictById(Long id) {
        return null;
    }

    @Override
    public Wrapper<Boolean> checkDictCode(MdcDictCheckCodeDto mdcDictCheckCodeDto) {
        return null;
    }

    @Override
    public Wrapper<Boolean> checkDictName(MdcDictCheckNameDto mdcDictCheckNameDto) {
        return null;
    }
}