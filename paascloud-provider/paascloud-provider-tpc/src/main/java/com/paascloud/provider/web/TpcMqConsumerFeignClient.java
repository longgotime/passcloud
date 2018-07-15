/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.TpcMqConsumer;
import com.paascloud.provider.model.dto.TpcMqConsumerQuery;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;
import com.paascloud.provider.service.TpcMqConsumerFeignApi;
import com.paascloud.provider.service.TpcMqConsumerService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 消费者管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "WEB - TpcMqConsumerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqConsumerFeignClient extends BaseFeignClient implements TpcMqConsumerFeignApi {

    @Resource
    private TpcMqConsumerService tpcMqConsumerService;

    @Override
    @ApiOperation(httpMethod = "POST", value = "查询Mq消费者列表")
    public Wrapper<List<TpcMqConsumerVo>> queryConsumerVoList(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumerQuery) {

        TpcMqConsumer tpcMqConsumer = new ModelMapper().map(tpcMqConsumerQuery, TpcMqConsumer.class);

        logger.info("查询消费者列表tpcMqProducerQuery={}", tpcMqConsumer);
        List<TpcMqConsumerVo> list = tpcMqConsumerService.listConsumerVoWithPage(tpcMqConsumer);
        return WrapMapper.ok(list);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "查询订阅者列表")
    public Wrapper<PageInfo<TpcMqSubscribeVo>> querySubscribeListWithPage(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumerQuery) {
        logger.info("查询Mq订阅列表tpcMqConsumerQuery={}", tpcMqConsumerQuery);

        TpcMqConsumer tpcMqConsumer = new ModelMapper().map(tpcMqConsumerQuery, TpcMqConsumer.class);

        PageHelper.startPage(tpcMqConsumer.getPageNum(), tpcMqConsumer.getPageSize());
        tpcMqConsumer.setOrderBy("update_time desc");
        List<TpcMqSubscribeVo> list = tpcMqConsumerService.listSubscribeVoWithPage(tpcMqConsumer);
        PageInfo<TpcMqSubscribeVo> pageInfo = new PageInfo<>(list);
        if (PublicUtil.isNotEmpty(list)) {
            Map<Long, TpcMqSubscribeVo> tpcMqSubscribeVoMap = this.trans2Map(list);
            List<Long> subscribeIdList = new ArrayList<>(tpcMqSubscribeVoMap.keySet());
            List<TpcMqSubscribeVo> tagVoList = tpcMqConsumerService.listSubscribeVo(subscribeIdList);
            for (TpcMqSubscribeVo vo : tagVoList) {
                Long subscribeId = vo.getId();
                if (!tpcMqSubscribeVoMap.containsKey(subscribeId)) {
                    continue;
                }
                TpcMqSubscribeVo tpcMqSubscribeVo = tpcMqSubscribeVoMap.get(subscribeId);
                tpcMqSubscribeVo.setTagVoList(vo.getTagVoList());
            }
            pageInfo.setList(new ArrayList<>(tpcMqSubscribeVoMap.values()));
        }
        return WrapMapper.ok(pageInfo);
    }

    private Map<Long, TpcMqSubscribeVo> trans2Map(List<TpcMqSubscribeVo> resultDTOS) {
        Map<Long, TpcMqSubscribeVo> resultMap = new TreeMap<>((o1, o2) -> {
            o1 = o1 == null ? 0 : o1;
            o2 = o2 == null ? 0 : o2;
            return o2.compareTo(o1);
        });
        for (TpcMqSubscribeVo resultDTO : resultDTOS) {
            resultMap.put(resultDTO.getId(), resultDTO);
        }
        return resultMap;
    }


    @Override
    @ApiOperation(httpMethod = "POST", value = "更改消费者状态")
    public Wrapper modifyConsumerStatusById(@ApiParam(value = "更改消费者状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改consumer状态 updateStatusDto={}", updateStatusDto);
        Long consumerId = updateStatusDto.getId();

        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();

        TpcMqConsumer consumer = new TpcMqConsumer();
        consumer.setId(consumerId);
        consumer.setStatus(updateStatusDto.getStatus());
        consumer.setUpdateInfo(loginAuthDto);

        int result = tpcMqConsumerService.update(consumer);
        return WrapMapper.handleResult(result);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "根据消费者ID删除消费者")
    public Wrapper deleteConsumerById(@PathVariable("id") Long id) {
        logger.info("删除consumer id={}", id);
        int result = tpcMqConsumerService.deleteConsumerById(id);
        return WrapMapper.handleResult(result);
    }
}
