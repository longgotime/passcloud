/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.MdcExceptionQueryDto;
import com.paascloud.provider.service.MdcExceptionFeignApi;
import com.paascloud.provider.service.MdcExceptionLogService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 异常管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
public class MdcExceptionMainFeignClient extends BaseFeignClient implements MdcExceptionFeignApi {
    @Resource
    private MdcExceptionLogService mdcExceptionLogService;

    @Override
    public Wrapper queryLogListWithPage(@RequestBody MdcExceptionQueryDto mdcExceptionQueryDto) {
        logger.info("查询日志处理列表 mdcExceptionQueryDto={}", mdcExceptionQueryDto);
        PageInfo pageInfo = mdcExceptionLogService.queryExceptionListWithPage(mdcExceptionQueryDto);
        return WrapMapper.ok(pageInfo);
    }
}
