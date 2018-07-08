/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RoleVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.role;

import com.paascloud.base.dto.BaseVo;
import com.paascloud.base.dto.LoginAuthDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * The class Role vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleDto extends BaseVo implements Serializable {


	private static final long serialVersionUID = 563763115380802644L;
	/**
	 * 角色编码
	 */
	private String roleCode;

	private String roleName;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 登录人信息
	 */
	private LoginAuthDto loginAuthDto;
}