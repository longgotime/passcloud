/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTagFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqTagQuery;
import com.paascloud.provider.model.vo.TpcMqTagVo;
import com.paascloud.provider.service.TpcMqTagFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;


/**
 * The class Tpc mq tag feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class TpcMqTagFeignHystrix implements TpcMqTagFeignApi {

    @Override
    public Wrapper<PageInfo<TpcMqTagVo>> queryTagListWithPage(TpcMqTagQuery tpcMqTag) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyProducerStatusById(UpdateStatusDto updateStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteTagById(Long id) {
        return WrapMapper.error();
    }
}
