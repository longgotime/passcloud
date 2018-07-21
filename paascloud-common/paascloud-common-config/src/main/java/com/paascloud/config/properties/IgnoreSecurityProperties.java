/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 *  类名称：OAuth2Properties.java
 *  创建人：刘兆明
 *  联系方式：paascloud.net@gmail.com
 *  开源地址: https://github.com/paascloud
 *  博客地址: http://blog.paascloud.net
 *  项目官网: http://paascloud.net
 */
package com.paascloud.config.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Ignore filter properties.
 * @author liuzhaoming
 */
@Data
public class IgnoreSecurityProperties {
    /**
     * 忽略的 URL 表达式
     */
    private List<String> urls;

    /**
     * 忽略的客户端 ID
     */
    private List<String> clientIds;
}
