/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcDictFeignHystrix.java
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
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Mdc dict feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MdcDictFeignHystrix implements MdcDictFeignApi {

    @Override
    public Wrapper<List<MdcDictVo>> queryDictTreeList() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<MdcDictVo> queryDictVoById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateMdcDictStatusById(UpdateStatusDto updateStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper saveDict(MdcEditDictDto mdcDictAddDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Integer> deleteMdcDictById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> checkDictCode(MdcDictCheckCodeDto mdcDictCheckCodeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> checkDictName(MdcDictCheckNameDto mdcDictCheckNameDto) {
        return WrapMapper.error();
    }
}