/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.provider.model.dto.TpcMqProducerQuery;
import com.paascloud.provider.model.vo.TpcMqProducerVo;
import com.paascloud.provider.model.vo.TpcMqPublishVo;
import com.paascloud.provider.service.TpcMqProducerFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 生产者管理.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class TpcMqProducerFeignHystrix implements TpcMqProducerFeignApi {
    @Override
    public Wrapper<List<TpcMqProducerVo>> queryProducerList(TpcMqProducerQuery tpcMqProducer) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<TpcMqPublishVo>> queryPublishListWithPage(TpcMqProducerQuery tpcMqProducer) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyProducerStatusById(UpdateStatusDto updateStatusDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper deleteProducerById(Long id) {
        return WrapMapper.error();
    }
}
