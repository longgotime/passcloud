package com.paascloud.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.paascloud.PublicUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UserTokenDto;
import com.paascloud.core.support.BaseService;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.mapper.UacUserTokenMapper;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.domain.UacUserToken;
import com.paascloud.provider.model.dto.token.TokenMainQueryDto;
import com.paascloud.provider.model.enums.UacUserTokenStatusEnum;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.provider.service.UacUserTokenService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * The class Uac user token service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserTokenServiceImpl extends BaseService<UacUserToken> implements UacUserTokenService {
	@Resource
	private UacUserTokenMapper uacUserTokenMapper;
	@Resource
	private UacUserService uacUserService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

    private final int ACCESS_TOKEN_VALIDATE_SECONDS = 7200;

    private final int REFRESH_TOKEN_VALIDITY_SECONDS = 2592000;


	@Value("${paascloud.auth.refresh-token-url}")
	private String refreshTokenUrl;

	@Override
	public void saveUserToken(String accessToken, String refreshToken, LoginAuthDto loginAuthDto, final String os, final String browser, final String remoteAddr, final String remoteLocation) {
		// 获取登录时间
		Long userId = loginAuthDto.getUserId();
		UacUser uacUser = uacUserService.selectByKey(userId);

		// 存入mysql数据库
		UacUserToken uacUserToken = new UacUserToken();
		uacUserToken.setOs(os);
		uacUserToken.setBrowser(browser);
		uacUserToken.setAccessToken(accessToken);
		uacUserToken.setAccessTokenValidity(ACCESS_TOKEN_VALIDATE_SECONDS);
		uacUserToken.setLoginIp(remoteAddr);
		uacUserToken.setLoginLocation(remoteLocation);
		uacUserToken.setLoginTime(uacUser.getLastLoginTime());
		uacUserToken.setLoginName(loginAuthDto.getLoginName());
		uacUserToken.setRefreshToken(refreshToken);
		uacUserToken.setRefreshTokenValidity(REFRESH_TOKEN_VALIDITY_SECONDS);
		uacUserToken.setStatus(UacUserTokenStatusEnum.ON_LINE.getStatus());
		uacUserToken.setUserId(userId);
		uacUserToken.setUserName(loginAuthDto.getUserName());
		uacUserToken.setUpdateInfo(loginAuthDto);
		uacUserToken.setGroupId(loginAuthDto.getGroupId());
		uacUserToken.setGroupName(loginAuthDto.getGroupName());
		uacUserToken.setId(generateId());
		try {
			uacUserTokenMapper.insertSelective(uacUserToken);
		} catch (Exception e) {
			logger.error("保存 accessToken 失败 e={}", e.getMessage(), e);
		}
		// 存入redis数据库
		this.updateRedisUserToken(accessToken, ACCESS_TOKEN_VALIDATE_SECONDS, loginAuthDto);
	}

	private void updateRedisUserToken(String accessToken, int accessTokenValidateSeconds, LoginAuthDto loginAuthDto) {
		redisTemplate.opsForValue().set(RedisKeyUtil.getAccessTokenKey(accessToken.trim()), loginAuthDto, accessTokenValidateSeconds, TimeUnit.SECONDS);
	}

	@Override
	public UserTokenDto getByAccessToken(String accessToken) {
        UacUserToken uacUserToken = new UacUserToken();
        uacUserToken.setAccessToken(accessToken);
        uacUserToken = uacUserTokenMapper.selectOne(uacUserToken);
        return new ModelMapper().map(uacUserToken, UserTokenDto.class);
	}

	@Override
	public void updateUacUserToken(UserTokenDto tokenDto, LoginAuthDto loginAuthDto) {
		UacUserToken uacUserToken = new ModelMapper().map(tokenDto, UacUserToken.class);
		uacUserToken.setUpdateInfo(loginAuthDto);
		uacUserTokenMapper.updateByPrimaryKeySelective(uacUserToken);
		updateRedisUserToken(uacUserToken.getAccessToken(), ACCESS_TOKEN_VALIDATE_SECONDS, tokenDto);
	}

	@Override
	public PageInfo listTokenWithPage(TokenMainQueryDto token) {
		PageHelper.startPage(token.getPageNum(), token.getPageSize());
		UacUserToken userToken = new UacUserToken();
		userToken.setStatus(token.getStatus());
		if (token.getStatus() != null) {
			userToken.setStatus(token.getStatus());
		}

		if (StringUtils.isNotBlank(token.getLoginName())) {
			userToken.setLoginName(token.getLoginName());
		}
		if (StringUtils.isNotBlank(token.getUserName())) {
			userToken.setUserName(token.getUserName());
		}
		List<UacUserToken> userTokenList = uacUserTokenMapper.selectTokenList(userToken);
		return new PageInfo<>(userTokenList);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String refreshToken(String refreshToken, String accessToken, String header, String remoteAddr, String os, String browser) throws HttpProcessException {
		String token;
		Map<String, Object> map = Maps.newTreeMap();
		map.put("grant_type", "refresh_token");
		map.put("refresh_token", refreshToken);

		//插件式配置请求参数（网址、请求参数、编码、client）
		Header[] headers = HttpHeader.custom().contentType(HttpHeader.Headers.APP_FORM_URLENCODED).authorization(header).build();
		HttpConfig config = HttpConfig.custom().headers(headers).url(refreshTokenUrl).map(map);
		token = HttpClientUtil.post(config);
		JSONObject jsonObj = JSON.parseObject(token);
		String accessTokenNew = (String) jsonObj.get("access_token");
		String refreshTokenNew = (String) jsonObj.get("refresh_token");
		String loginName = (String) jsonObj.get("loginName");


		// 更新本次token数据
		UserTokenDto tokenDto = this.getByAccessToken(accessToken);
		tokenDto.setStatus(UacUserTokenStatusEnum.ON_REFRESH.getStatus());
		UacUser uacUser = uacUserService.findUserInfoByLoginName(loginName);

		LoginAuthDto loginAuthDto = new LoginAuthDto(uacUser.getId(), uacUser.getLoginName(), uacUser.getUserName(), uacUser.getGroupId(), uacUser.getGroupName());
		this.updateUacUserToken(tokenDto, loginAuthDto);
		// 创建刷新token

		this.saveUserToken(accessTokenNew, refreshTokenNew, loginAuthDto, os, browser, remoteAddr, null);
		return token;
	}

	@Override
	public int batchUpdateTokenOffLine() {
		List<Long> idList = uacUserTokenMapper.listOffLineTokenId();
		if (PublicUtil.isEmpty(idList)) {
			return 1;
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", 20);
		map.put("tokenIdList", idList);
		return uacUserTokenMapper.batchUpdateTokenOffLine(map);
	}
}
