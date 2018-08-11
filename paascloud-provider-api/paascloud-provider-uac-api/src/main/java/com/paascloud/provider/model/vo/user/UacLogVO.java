/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.provider.model.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * The type Uac log vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UacLogVO extends BaseVo {
    private static final long serialVersionUID = 7908332190253437978L;

    /**
     * 组织流水号
     */
    private Long groupId;

    /**
     * 组织名称
     */
    private String groupName;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 日志类型名称
     */
    private String logName;

    /**
     * 权限ID
     */
    private Long actionId;

    /**
     * 权限编码
     */
    private String actionCode;

    /**
     * 权限名称
     */
    private String actionName;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 物理地址
     */
    private String mac;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 请求参数
     */
    private String requestData;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 响应结果
     */
    private String responseData;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    /**
     * 耗时,秒
     */
    private Long excuteTime;
    /**
     * 登录位置
     */
    private String location;
}
