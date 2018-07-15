/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.operate.controller.omc;

import com.github.pagehelper.PageInfo;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.OmcShippingDTO;
import com.paascloud.provider.model.dto.OmcShippingQuery;
import com.paascloud.provider.service.OmcShippingFeignApi;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The class Omc shipping controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/web/shipping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OmcShippingController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcShippingController extends BaseController {

    @Resource
    private OmcShippingFeignApi omcShippingFeignApi;

    /**
     * 增加收货人地址.
     *
     * @param shipping the shipping
     * @return the wrapper
     */
    @PostMapping("/addShipping")
    @ApiOperation(httpMethod = "POST", value = "增加收货人地址")
    public Wrapper addShipping(OmcShippingDTO shipping) {

        logger.info("addShipping - 增加收货人地址. shipping={}", shipping);
        shipping.setLoginAuthDto(getLoginAuthDto());
        return omcShippingFeignApi.addShipping(shipping);

    }

    /**
     * 删除收货人地址.
     *
     * @param shippingId the shipping id
     * @return the wrapper
     */
    @PostMapping("/deleteShipping/{shippingId}")
    @ApiOperation(httpMethod = "POST", value = "删除收货人地址")
    public Wrapper deleteShipping(@PathVariable Long shippingId) {
        Long userId = getLoginAuthDto().getUserId();
        logger.info("deleteShipping - 删除收货人地址. userId={}, shippingId={}", userId, shippingId);
        return omcShippingFeignApi.deleteShipping(shippingId, userId);
    }

    /**
     * 编辑收货人地址.
     *
     * @param shipping the shipping
     * @return the wrapper
     */
    @PostMapping("/updateShipping")
    @ApiOperation(httpMethod = "POST", value = "编辑收货人地址")
    public Wrapper updateShipping(OmcShippingDTO shipping) {
        logger.info("updateShipping - 编辑收货人地址. shipping={}", shipping);
        shipping.setLoginAuthDto(getLoginAuthDto());
        return omcShippingFeignApi.updateShipping(shipping);
    }

    /**
     * 设置默认收货地址.
     *
     * @param addressId the address id
     * @return the default address
     */
    @PostMapping("/setDefaultAddress/{addressId}")
    @ApiOperation(httpMethod = "POST", value = "设置默认收货地址")
    public Wrapper setDefaultAddress(@PathVariable Long addressId) {
        logger.info("updateShipping - 设置默认地址. addressId={}", addressId);
        OmcShippingDTO omcShippingDTO = new OmcShippingDTO();
        omcShippingDTO.setId(addressId);
        omcShippingDTO.setLoginAuthDto(getLoginAuthDto());
        return omcShippingFeignApi.setDefaultAddress(omcShippingDTO);
    }

    /**
     * 根据Id查询收货人地址.
     *
     * @param shippingId the shipping id
     * @return the wrapper
     */
    @PostMapping("/selectShippingById/{shippingId}")
    @ApiOperation(httpMethod = "POST", value = "根据Id查询收货人地址")
    public Wrapper<OmcShippingDTO> selectShippingById(@PathVariable Long shippingId) {
        Long userId = getLoginAuthDto().getUserId();
        logger.info("selectShippingById - 根据Id查询收货人地址. userId={}, shippingId={}", userId, shippingId);
        return omcShippingFeignApi.selectShippingById(shippingId, userId);
    }

    /**
     * 分页查询当前用户收货人地址列表.
     *
     * @param shipping the shipping
     * @return the wrapper
     */
    @PostMapping("/queryUserShippingListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询当前用户收货人地址列表")
    public Wrapper<PageInfo> queryUserShippingListWithPage(@RequestBody OmcShippingQuery shipping) {
        Long userId = getLoginAuthDto().getUserId();
        shipping.setUserId(userId);
        logger.info("queryUserShippingListWithPage - 分页查询当前用户收货人地址列表.userId={} shipping={}", userId, shipping);
        return omcShippingFeignApi.queryUserShippingListWithPage(shipping);
    }

    /**
     * 分页查询收货人地址列表.
     *
     * @param shipping the shipping
     * @return the wrapper
     */
    @PostMapping("/queryShippingListWithPage")
    @ApiOperation(httpMethod = "POST", value = "分页查询收货人地址列表")
    public Wrapper<PageInfo> queryShippingListWithPage(@RequestBody OmcShippingQuery shipping) {
        logger.info("queryShippingListWithPage - 分页查询收货人地址列表. shipping={}", shipping);
        return omcShippingFeignApi.queryShippingListWithPage(shipping);
    }

}
