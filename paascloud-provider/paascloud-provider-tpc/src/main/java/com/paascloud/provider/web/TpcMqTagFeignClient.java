/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTagFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.TpcMqTag;
import com.paascloud.provider.model.dto.TpcMqTagQuery;
import com.paascloud.provider.model.vo.TpcMqTagVo;
import com.paascloud.provider.service.TpcMqTagFeignApi;
import com.paascloud.provider.service.TpcMqTagService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * The class Tpc mq tag feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "WEB - TpcMqTagController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqTagFeignClient extends BaseFeignClient implements TpcMqTagFeignApi {

    @Resource
    private TpcMqTagService tpcMqTagService;


    @Override
    @ApiOperation(httpMethod = "POST", value = "查询MQ-Tag列表")
    public Wrapper<PageInfo<TpcMqTagVo>> queryTagListWithPage(@ApiParam(name = "tag", value = "角色信息") @RequestBody TpcMqTagQuery tpcMqTagQuery) {

        logger.info("查询角色列表tpcMqTagQuery={}", tpcMqTagQuery);

        TpcMqTag tpcMqTag = new ModelMapper().map(tpcMqTagQuery, TpcMqTag.class);

        PageHelper.startPage(tpcMqTag.getPageNum(), tpcMqTag.getPageSize());
        tpcMqTag.setOrderBy("update_time desc");
        List<TpcMqTagVo> list = tpcMqTagService.listWithPage(tpcMqTag);
        return WrapMapper.ok(new PageInfo<>(list));
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "修改MQ-Tag状态")
    public Wrapper modifyProducerStatusById(@ApiParam(value = "修改tag状态") @RequestBody UpdateStatusDto updateStatusDto) {
        logger.info("修改tag状态 updateStatusDto={}", updateStatusDto);
        Long roleId = updateStatusDto.getId();

        LoginAuthDto loginAuthDto = updateStatusDto.getLoginAuthDto();

        TpcMqTag tag = new TpcMqTag();
        tag.setId(roleId);
        tag.setStatus(updateStatusDto.getStatus());
        tag.setUpdateInfo(loginAuthDto);

        int result = tpcMqTagService.update(tag);
        return WrapMapper.handleResult(result);
    }

    @Override
    @ApiOperation(httpMethod = "POST", value = "根据ID删除TAG")
    public Wrapper deleteTagById(@ApiParam(value = "Tag ID") @PathVariable("id") Long id) {
        logger.info("删除tag id={}", id);
        int result = tpcMqTagService.deleteTagById(id);
        return WrapMapper.handleResult(result);
    }
}
