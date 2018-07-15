/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcFileController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.service.hystrix.OpcFileFeignHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * The interface Opc file feign api.
 *
 * @author paascloud.net @gmail.com
 */
@Api(value = "WEB - OptFileRest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@FeignClient(value = "paascloud-provider-opc", configuration = OAuth2FeignAutoConfiguration.class, fallback = OpcFileFeignHystrix.class)
public interface OpcFileFeignApi {

    /**
     * 根据ID查询附件信息.
     *
     * @param id the id
     * @return the wrapper
     */
    @PostMapping(value = "/queryAttachmentById/{id}")
    @ApiOperation(httpMethod = "POST", value = "根据ID查询附件信息")
    Wrapper<OptAttachmentRespDto> queryAttachment(@PathVariable("id") Long id);

    /**
     * 根据关联单号查询附件信息.
     *
     * @param refNo the ref no
     * @return the wrapper
     */
    @PostMapping(value = "/queryAttachmentListByRefNo/{refNo}")
    @ApiOperation(httpMethod = "POST", value = "根据关联单号查询附件信息")
    Wrapper<List<OptAttachmentRespDto>> queryAttachmentListByRefNo(@PathVariable("refNo") String refNo);


    /**
     * 删除附件信息.
     *
     * @param attachmentId the attachment id
     * @return the wrapper
     */
    @PostMapping(value = "/deleteAttachment/{attachmentId}")
    @ApiOperation(httpMethod = "POST", value = "删除附件信息")
    Wrapper deleteAttachment(@PathVariable("attachmentId") Long attachmentId);

}
