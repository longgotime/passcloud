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

package com.paascloud.provider.web;

import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.service.OpcAttachmentService;
import com.paascloud.provider.service.OpcFileFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Opt file feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OptFileRest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcFileFeignClient extends BaseFeignClient implements OpcFileFeignApi {

	@Resource
	private OpcAttachmentService optAttachmentService;

	/**
	 * 根据ID查询附件信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "根据ID查询附件信息")
	public Wrapper<OptAttachmentRespDto> queryAttachment(@PathVariable("id") Long id) {
		logger.info("queryAttachment -根据ID查询文件信息. id={}", id);

		OptAttachmentRespDto optAttachmentRespDto = optAttachmentService.queryAttachmentById(id);
		return WrapMapper.ok(optAttachmentRespDto);
	}

	/**
	 * 根据关联单号查询附件信息.
	 *
	 * @param refNo the ref no
	 *
	 * @return the wrapper
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "根据关联单号查询附件信息")
	public Wrapper<List<OptAttachmentRespDto>> queryAttachmentListByRefNo(@PathVariable("refNo") String refNo) {
		logger.info("queryAttachment -查询附件信息. refNo={}", refNo);

		List<OptAttachmentRespDto> optAttachmentRespDtos = optAttachmentService.queryAttachmentListByRefNo(refNo);
		return WrapMapper.ok(optAttachmentRespDtos);
	}


	/**
	 * 删除附件信息.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the wrapper
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "删除附件信息")
	public Wrapper deleteAttachment(@PathVariable("attachmentId") Long attachmentId) {
		logger.info("deleteAttachment - 删除文件. attachmentId={}", attachmentId);
		int result;
		try {
			result = optAttachmentService.deleteFile(attachmentId);
		} catch (BusinessException ex) {
			logger.error("删除文件, 出现异常={}", ex.getMessage(), ex);
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "出现异常");
		} catch (Exception e) {
			logger.error("删除取文件, 出现异常={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return WrapMapper.handleResult(result);
	}

}
