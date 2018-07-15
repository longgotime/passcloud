/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTopicController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.tpc;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.TpcMqTopicQuery;
import com.paascloud.provider.model.vo.TpcMqTopicVo;
import com.paascloud.provider.service.TpcMqTopicFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Tpc mq topic controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/topic", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqTopicController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqTopicController extends BaseController {

    @Resource
    private TpcMqTopicFeignApi tpcMqTopicFeignApi;


    /**
     * 查询MQ topic列表.
     *
     * @param tpcMqTopic the tpc mq topic
     * @return the wrapper
     */
    @PostMapping(value = "/queryTopicListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询MQ topic列表")
    public Wrapper<List<TpcMqTopicVo>> queryTopicListWithPage(@ApiParam(name = "topic", value = "MQ-Topic") @RequestBody TpcMqTopicQuery tpcMqTopic) {

        logger.info("查询角色列表tpcMqTopicQuery={}", tpcMqTopic);

        return tpcMqTopicFeignApi.queryTopicListWithPage(tpcMqTopic);
    }

    /**
     * 修改topic状态.
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/modifyStatusById")
    @ApiOperation(httpMethod = "POST", value = "修改topic状态")
    @LogAnnotation
    public Wrapper modifyTopicStatusById(@ApiParam(value = "修改topic状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改topic状态 updateStatusDto={}", updateStatusDto);

        LoginAuthDto loginAuthDto = getLoginAuthDto();

        updateStatusDto.setLoginAuthDto(loginAuthDto);
        return tpcMqTopicFeignApi.modifyTopicStatusById(updateStatusDto);
    }
}
