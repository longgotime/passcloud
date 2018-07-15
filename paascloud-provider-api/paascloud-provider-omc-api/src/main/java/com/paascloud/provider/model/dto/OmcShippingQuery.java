/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingDTO.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;


import com.paascloud.base.dto.BaseQuery;
import com.paascloud.base.dto.LoginAuthDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The class Omc shipping.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OmcShippingQuery extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 7337074530378267740L;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收货姓名
     */
    private String receiverName;

    /**
     * 收货移动电话
     */
    private String receiverMobileNo;

}