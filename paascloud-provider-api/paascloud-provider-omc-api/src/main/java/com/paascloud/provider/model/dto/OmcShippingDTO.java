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


import com.paascloud.base.dto.LoginAuthDto;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Omc shipping.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OmcShippingDTO implements Serializable {

    private static final long serialVersionUID = 7337074530378267740L;
    private Long id;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收货姓名
     */
    private String receiverName;

    /**
     * 收货固定电话
     */
    private String receiverPhoneNo;

    /**
     * 收货移动电话
     */
    private String receiverMobileNo;

    /**
     * 收货人省ID
     */
    private Long provinceId;

    /**
     * 省份
     */
    private String provinceName;

    /**
     * 收货人城市ID
     */
    private Long cityId;

    /**
     * 收货人城市名称
     */
    private String cityName;

    /**
     * 区/县
     */
    private String districtName;

    /**
     * 区/县 编码
     */
    private Long districtId;

    /**
     * 街道ID
     */
    private Long streetId;

    /**
     * 接到名称
     */
    private String streetName;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮编
     */
    private String receiverZipCode;

    /**
     * 邮编
     */
    private Integer defaultAddress;

    private LoginAuthDto loginAuthDto;
}