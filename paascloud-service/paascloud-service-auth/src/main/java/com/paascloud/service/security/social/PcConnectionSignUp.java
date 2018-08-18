package com.paascloud.service.security.social;

import com.paascloud.PubUtils;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.dto.user.SaveUserDTO;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.model.service.UacUserFeignApi;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhailiang
 *
 */
@Component
public class PcConnectionSignUp implements ConnectionSignUp {

	@Resource
	private UacUserFeignApi uacUserFeignApi;

	@Override
	public String execute(Connection<?> connection) {
		//根据社交用户信息默认创建用户并返回用户唯一标识

		String loginName = PubUtils.uuid();
		String tel = com.xiaoleilu.hutool.util.RandomUtil.randomNumbers(11);

		SaveUserDTO user = new SaveUserDTO();

		user.setEmail(loginName + "@163.com");
		user.setGroupId(1L);
		user.setGroupName("paascloud");
		user.setLastLoginTime(new Date());
		user.setLoginName(loginName);
		user.setMobileNo(tel);
		user.setStatus(UacUserStatusEnum.ENABLE.getKey());
		user.setUserName(connection.getDisplayName());
		user.setUserCode(loginName);
		user.setRemark("微信授权, 自动生成");
		user.setSalt(loginName);
		user.setLoginPwd("abc123456");


		LoginAuthDto authDto = new LoginAuthDto();
		authDto.setGroupName("paascloud");
		authDto.setGroupId(1L);
		authDto.setLoginName("admin");
		authDto.setUserName("超级管理员");
		authDto.setUserId(1L);
		user.setLoginAuthDto(authDto);

		uacUserFeignApi.saveUacUser(user);

		return loginName;
	}

}
