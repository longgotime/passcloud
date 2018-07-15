/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.TpcMqProducer;
import com.paascloud.provider.model.dto.TpcMqProducerQuery;
import com.paascloud.provider.model.vo.TpcMqProducerVo;
import com.paascloud.provider.model.vo.TpcMqPublishVo;
import com.paascloud.provider.service.TpcMqProducerFeignApi;
import com.paascloud.provider.service.TpcMqProducerService;
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
import java.util.List;


/**
 * 生产者管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "WEB - TpcMqProducerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqProducerFeignClient extends BaseFeignClient implements TpcMqProducerFeignApi {

    @Resource
    private TpcMqProducerService tpcMqProducerService;

    @Override
    @ApiOperation(httpMethod = "POST", value = "查询生产者列表")
    public Wrapper<List<TpcMqProducerVo>> queryProducerList(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducerQuery tpcMqProducerQuery) {

        logger.info("查询生产者列表tpcMqTopicQuery={}", tpcMqProducerQuery);

        TpcMqProducer tpcMqProducer = new ModelMapper().map(tpcMqProducerQuery, TpcMqProducer.class);


        List<TpcMqProducerVo> list = tpcMqProducerService.listProducerVoWithPage(tpcMqProducer);
        return WrapMapper.ok(list);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "查询发布者列表")
    public Wrapper<PageInfo<TpcMqPublishVo>> queryPublishListWithPage(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducerQuery tpcMqProducerQuery) {
        logger.info("查询Mq发布列表tpcMqTopicQuery={}", tpcMqProducerQuery);
        PageHelper.startPage(tpcMqProducerQuery.getPageNum(), tpcMqProducerQuery.getPageSize());
        tpcMqProducerQuery.setOrderBy("update_time desc");

        TpcMqProducer tpcMqProducer = new ModelMapper().map(tpcMqProducerQuery, TpcMqProducer.class);

        List<TpcMqPublishVo> list = tpcMqProducerService.listPublishVoWithPage(tpcMqProducer);
        return WrapMapper.ok(new PageInfo<>(list));
    }

    @Override
    public Wrapper modifyProducerStatusById(@ApiParam(value = "修改producer状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改producer状态 updateStatusDto={}", updateStatusDto);
        Long roleId = updateStatusDto.getId();

        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();

        TpcMqProducer producer = new TpcMqProducer();
        producer.setId(roleId);
        producer.setStatus(updateStatusDto.getStatus());
        producer.setUpdateInfo(loginAuthDto);

        int result = tpcMqProducerService.update(producer);
        return WrapMapper.handleResult(result);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "根据生产者ID删除生产者")
    public Wrapper deleteProducerById(@PathVariable("id") Long id) {
        logger.info("删除producer id={}", id);
        int result = tpcMqProducerService.deleteProducerById(id);
        return WrapMapper.handleResult(result);
    }
}
