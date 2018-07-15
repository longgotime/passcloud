/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.tpc;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.TpcMqConsumerQuery;
import com.paascloud.provider.model.vo.TpcMqConsumerVo;
import com.paascloud.provider.model.vo.TpcMqSubscribeVo;
import com.paascloud.provider.service.TpcMqConsumerFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 消费者管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/web/consumer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqConsumerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqConsumerController extends BaseController {

    @Resource
    private TpcMqConsumerFeignApi tpcMqConsumerFeignApi;

    /**
     * 查询Mq消费者列表.
     *
     * @param tpcMqConsumer the tpc mq consumer
     * @return the wrapper
     */
    @PostMapping(value = "/queryConsumerVoListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询Mq消费者列表")
    public Wrapper<List<TpcMqConsumerVo>> queryConsumerVoList(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumer) {

        logger.info("查询消费者列表tpcMqProducerQuery={}", tpcMqConsumer);
        return tpcMqConsumerFeignApi.queryConsumerVoList(tpcMqConsumer);
    }

    /**
     * 查询订阅者列表.
     *
     * @param tpcMqConsumer the tpc mq consumer
     * @return the wrapper
     */
    @PostMapping(value = "/querySubscribeListWithPage")
    @ApiOperation(httpMethod = "POST", value = "查询订阅者列表")
    public Wrapper<PageInfo<TpcMqSubscribeVo>> querySubscribeListWithPage(@ApiParam(name = "consumer", value = "Mq消费者") @RequestBody TpcMqConsumerQuery tpcMqConsumer) {
        return tpcMqConsumerFeignApi.querySubscribeListWithPage(tpcMqConsumer);
    }

    /**
     * 更改消费者状态.
     *
     * @param updateStatusDto the update status dto
     * @return the wrapper
     */
    @PostMapping(value = "/modifyStatusById")
    @ApiOperation(httpMethod = "POST", value = "更改消费者状态")
    @LogAnnotation
    public Wrapper modifyConsumerStatusById(@ApiParam(value = "更改消费者状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改consumer状态 updateStatusDto={}", updateStatusDto);

        LoginAuthDto loginAuthDto = getLoginAuthDto();
        updateStatusDto.setLoginAuthDto(loginAuthDto);

        return tpcMqConsumerFeignApi.modifyConsumerStatusById(updateStatusDto);
    }

    /**
     * 根据消费者ID删除消费者.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/deleteById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据消费者ID删除消费者")
    @LogAnnotation
    public Wrapper deleteConsumerById(@PathVariable Long id) {
        logger.info("删除consumer id={}", id);
        return tpcMqConsumerFeignApi.deleteConsumerById(id);
    }
}
