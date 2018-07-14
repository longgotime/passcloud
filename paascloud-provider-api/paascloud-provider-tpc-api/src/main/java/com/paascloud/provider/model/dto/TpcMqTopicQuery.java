/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.provider.model.dto;

import com.paascloud.base.dto.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The type Tpc topic tag query.
 *
 * @author <a href="paascloud.net@gmail.com"/>刘兆明</a>
 * @date 2018 -07-08 上午8:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMqTopicQuery extends BaseQuery implements Serializable {
    /**
     * topic编码
     */
    private String topicCode;

    /**
     * topic名称
     */
    private String topicName;

    /**
     * MQ类型, 10 rocketmq 20 kafka
     */
    private Integer mqType;

    /**
     * 消息类型, 10 无序消息, 20 无序消息
     */
    private Integer msgType;

    /**
     * 状态, 0生效,10,失效
     */
    private Integer status;

    /**
     * 备注
     */
    private String remarks;
}
