/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqConsumerQuery;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;
import com.paascloud.provider.service.TpcMqConsumerFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author paascloud.net @gmail.com
 */
@Component
public class TpcMqConsumerFeignHystrix implements TpcMqConsumerFeignApi {
    @Override
    public Wrapper<List<TpcMqConsumerVo>> queryConsumerVoList(TpcMqConsumerQuery tpcMqConsumer) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<TpcMqSubscribeVo>> querySubscribeListWithPage(TpcMqConsumerQuery tpcMqConsumer) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyConsumerStatusById(UpdateStatusDto updateStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteConsumerById(Long id) {
        return WrapMapper.error();
    }
}
