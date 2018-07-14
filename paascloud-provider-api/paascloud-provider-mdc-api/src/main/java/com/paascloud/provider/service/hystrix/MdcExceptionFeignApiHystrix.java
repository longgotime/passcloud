/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionFeignApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.MdcExceptionQueryDto;
import com.paascloud.provider.service.MdcExceptionFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MdcExceptionFeignApiHystrix implements MdcExceptionFeignApi {

    @Override
    public Wrapper queryLogListWithPage(MdcExceptionQueryDto mdcExceptionQueryDto) {
        return WrapMapper.error();
    }
}