/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AuthHeaderFilter.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.paascloud.PublicUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.config.properties.PaascloudProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * The class Auth header filter.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class AuthHeaderFilter extends ZuulFilter {

    private static final String BEARER_TOKEN_TYPE = "Bearer ";
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private PaascloudProperties paascloudProperties;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Filter type string.
     *
     * @return the string
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * Filter order int.
     *
     * @return the int
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * Should filter boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * Run object.
     *
     * @return the object
     */
    @Override
    public Object run() throws ZuulException {
        log.info("AuthHeaderFilter - 开始鉴权...");
        RequestContext requestContext = RequestContext.getCurrentContext();
        try {
            doSomething(requestContext);
        } catch (Exception e) {
            log.error("AuthHeaderFilter - [FAIL] EXCEPTION={}", e.getMessage(), e);
            throw new ZuulException(e, 403, "check token fail");
        }
        return null;
    }

    private void doSomething(RequestContext requestContext) throws UnsupportedEncodingException {
        HttpServletRequest request = requestContext.getRequest();
        String requestURI = request.getRequestURI();
        log.info("AuthHeaderFilter - requestURI={}...", requestURI);
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            return;
        }


        List<String> urls = paascloudProperties.getSecurity().getOauth2().getIgnore().getUrls();

        for (String url : urls) {
            if (antPathMatcher.match(url, requestURI)) {
                return;
            }
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authHeader.startsWith(BEARER_TOKEN_TYPE)) {

            log.info("authHeader={} ", authHeader);

            requestContext.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, authHeader);
            String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");
            LoginAuthDto authDto = (LoginAuthDto) redisTemplate.opsForValue().get(RedisKeyUtil.getAccessTokenKey(token.trim()));

            requestContext.addZuulRequestHeader(GlobalConstant.Sys.CURRENT_USER_NAME, authentication.getName());

            if (!HttpMethod.GET.toString().equalsIgnoreCase(request.getMethod())) {
                log.info("authDto={} ", authDto);
                requestContext.addZuulRequestHeader(GlobalConstant.Sys.TOKEN_AUTH_DTO, URLEncoder.encode(JSONObject.toJSONString(authDto), "UTF-8"));
            }

        }
    }

}
