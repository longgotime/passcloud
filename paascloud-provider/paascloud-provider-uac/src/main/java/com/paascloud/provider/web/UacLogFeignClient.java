/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacLogMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.log.UacLogMainDto;
import com.paascloud.provider.model.service.UacLogFeignApi;
import com.paascloud.provider.service.UacLogService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志管理.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacLogFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacLogFeignClient extends BaseFeignClient implements UacLogFeignApi {

    @Resource
    private UacLogService uacLogService;

    @Override
    public Wrapper queryLogListWithPage(@RequestBody UacLogMainDto uacLogQueryDtoPage) {
        logger.info("查询日志处理列表 uacLogQueryDtoPage={}", uacLogQueryDtoPage);
        PageInfo pageInfo = uacLogService.queryLogListWithPage(uacLogQueryDtoPage);
        return WrapMapper.ok(pageInfo);
    }
}
