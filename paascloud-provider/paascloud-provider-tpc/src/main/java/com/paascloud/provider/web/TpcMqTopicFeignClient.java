/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTopicFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.TpcMqTopic;
import com.paascloud.provider.model.dto.TpcMqTopicQuery;
import com.paascloud.provider.model.vo.TpcMqTopicVo;
import com.paascloud.provider.service.TpcMqTopicFeignApi;
import com.paascloud.provider.service.TpcMqTopicService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Tpc mq topic feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "WEB - TpcMqTopicController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqTopicFeignClient extends BaseFeignClient implements TpcMqTopicFeignApi {

    @Resource
    private TpcMqTopicService tpcMqTopicService;

    @Override
    @ApiOperation(httpMethod = "POST", value = "查询MQ topic列表")
    public Wrapper<List<TpcMqTopicVo>> queryTopicListWithPage(@ApiParam(name = "topic", value = "MQ-Topic") @RequestBody TpcMqTopicQuery tpcMqTopicQuery) {

        logger.info("查询角色列表tpcMqTopicQuery={}", tpcMqTopicQuery);

        TpcMqTopic tpcMqTopic = new ModelMapper().map(tpcMqTopicQuery, TpcMqTopic.class);

        List<TpcMqTopicVo> list = tpcMqTopicService.listWithPage(tpcMqTopic);
        return WrapMapper.ok(list);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "修改topic状态")
    @LogAnnotation
    public Wrapper modifyTopicStatusById(@ApiParam(value = "修改topic状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改topic状态 updateStatusDto={}", updateStatusDto);
        Long roleId = updateStatusDto.getId();

        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();

        TpcMqTopic topic = new TpcMqTopic();
        topic.setId(roleId);
        topic.setStatus(updateStatusDto.getStatus());
        topic.setUpdateInfo(loginAuthDto);

        int result = tpcMqTopicService.update(topic);
        return WrapMapper.handleResult(result);
    }
}
