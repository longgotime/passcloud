/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacTokenMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.token.TokenMainQueryDto;
import com.paascloud.provider.model.service.UacTokenFeignApi;
import com.paascloud.provider.service.UacUserTokenService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * token主页面.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacTokenFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacTokenFeignClient extends BaseFeignClient implements UacTokenFeignApi {


    @Resource
    private UacUserTokenService uacUserTokenService;

    @Override
    public Wrapper<Integer> updateTokenOffLine() {
        return null;
    }

    @Override
    public Wrapper queryUacActionListWithPage(@ApiParam(name = "token") @RequestBody TokenMainQueryDto token) {
        logger.info("查询在线用户列表. token={}", token);
        PageInfo pageInfo = uacUserTokenService.listTokenWithPage(token);
        return WrapMapper.ok(pageInfo);
    }

}
