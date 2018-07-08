/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallUserController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.service.hystrix;

import com.paascloud.provider.model.dto.user.UserInfoDto;
import com.paascloud.provider.model.service.MallUserFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

/**
 * The class Mall user controller.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MallUserFeignApiHystrix implements MallUserFeignApi {

	@Override
	public Wrapper<UserInfoDto> updateInformation(UserInfoDto userInfoDto) {
		return null;
	}

	@Override
	public Wrapper<UserInfoDto> getInformation() {
		return null;
	}
}
