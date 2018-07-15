/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseFeignClient;
import com.paascloud.provider.model.domain.UacMenu;
import com.paascloud.provider.model.dto.menu.*;
import com.paascloud.provider.model.service.UacMenuFeignApi;
import com.paascloud.provider.model.vo.menu.MenuVo;
import com.paascloud.provider.model.vo.menu.ViewMenuVo;
import com.paascloud.provider.service.UacMenuService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - UacMenuFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacMenuFeignClient extends BaseFeignClient implements UacMenuFeignApi {

    @Resource
    private UacMenuService uacMenuService;

    @Override
    public Wrapper<List<MenuVo>> queryMenuTreeList(@PathVariable("userId") Long userId) {
        List<MenuVo> menuVoList = uacMenuService.getMenuVoList(userId, null);
        return WrapMapper.ok(menuVoList);
    }

    @Override
    public Wrapper<ViewMenuVo> queryMenuVoById(@PathVariable("id") Long id) {
        ViewMenuVo menuVo = uacMenuService.getViewVoById(id);
        return WrapMapper.ok(menuVo);
    }

    @Override
    public Wrapper updateUacMenuStatusById(@RequestBody UacMenuStatusDto uacMenuStatusDto) {
        logger.info("根据id修改菜单的禁用状态 uacMenuStatusDto={}", uacMenuStatusDto);
        LoginAuthDto loginAuthDto = uacMenuStatusDto.getLoginAuthDto();
        uacMenuService.updateUacMenuStatusById(uacMenuStatusDto, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper saveMenu(@RequestBody UacEditMenuDto uacMenuAddDto) {
        UacMenu uacMenu = new UacMenu();
        LoginAuthDto loginAuthDto = uacMenuAddDto.getLoginAuthDto();
        BeanUtils.copyProperties(uacMenuAddDto, uacMenu);
        uacMenuService.saveUacMenu(uacMenu, loginAuthDto);
        return WrapMapper.ok();
    }

    @Override
    public Wrapper<Integer> deleteUacMenuById(@PathVariable("id") Long id) {
        logger.info(" 根据id删除菜单 id={}", id);

        // 判断此菜单是否有子节点
        boolean hasChild = uacMenuService.checkMenuHasChildMenu(id);
        if (hasChild) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "此菜单含有子菜单, 请先删除子菜单");
        }

        int result = uacMenuService.deleteUacMenuById(id);
        return WrapMapper.handleResult(result);
    }

    @Override
    public Wrapper<Boolean> checkUacMenuActionCode(@RequestBody UacMenuCheckCodeDto uacMenuCheckCodeDto) {
        logger.info("校验菜单编码唯一性 uacMenuCheckCodeDto={}", uacMenuCheckCodeDto);

        Long id = uacMenuCheckCodeDto.getMenuId();
        String menuCode = uacMenuCheckCodeDto.getMenuCode();

        Example example = new Example(UacMenu.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("menuCode", menuCode);

        int result = uacMenuService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkUacMenuName(@RequestBody UacMenuCheckNameDto uacMenuCheckNameDto) {
        logger.info("校验菜单名称唯一性 uacMenuCheckNameDto={}", uacMenuCheckNameDto);
        Long id = uacMenuCheckNameDto.getMenuId();
        Long pid = uacMenuCheckNameDto.getPid();
        String menuName = uacMenuCheckNameDto.getMenuName();

        Example example = new Example(UacMenu.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("menuName", menuName);
        criteria.andEqualTo("pid", pid);

        int result = uacMenuService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }

    @Override
    public Wrapper<Boolean> checkUacMenuUrl(@RequestBody UacMenuCheckUrlDto uacMenuCheckUrlDto) {
        logger.info("检测菜单URL唯一性 uacMenuCheckUrlDto={}", uacMenuCheckUrlDto);

        Long id = uacMenuCheckUrlDto.getMenuId();
        String url = uacMenuCheckUrlDto.getUrl();

        Example example = new Example(UacMenu.class);
        Example.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andNotEqualTo("id", id);
        }
        criteria.andEqualTo("url", url);

        int result = uacMenuService.selectCountByExample(example);
        return WrapMapper.ok(result < 1);
    }
}