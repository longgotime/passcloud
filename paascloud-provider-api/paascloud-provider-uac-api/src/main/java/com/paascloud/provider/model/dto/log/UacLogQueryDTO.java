/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.provider.model.dto.log;

import com.paascloud.base.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The type Uac log query dto.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UacLogQueryDTO extends BaseQuery {
    private static final long serialVersionUID = -916634603848976674L;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    @Length(min = 5, max = 50, message = "{user.loginName.length}")
    @NotBlank(message="{user.loginName.notNull}")
    private String loginName;
}
