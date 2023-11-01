package com.example.swagger.controller;

import com.example.swagger.service.MenusService;
import com.example.swagger.entity.Menus;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yinfeng
 * @since 2022年3月12日 下午9:40:48
 * @description 菜单表
 */
@Api(tags = "菜单表")
@RestController
@RequestMapping("/menus")
@Slf4j
public class MenusController{

    @Resource
    private MenusService menusService;

    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "菜单表")
    public IPage<Menus> list(@RequestBody Menus menus) {
        log.info("从数据库读取数据");
        return menusService.list(menus);
    }

    @PostMapping("/getOne")
    @ApiOperation(value = "单个查询", notes = "菜单表")
    @Cacheable(value = "MenusController", key = "'menus-' + #menus.id")
    public Menus getOne(@RequestBody Menus menus) {
        log.info("从数据库读取数据");
        return menusService.getOne(menus);
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增或编辑", notes = "菜单表")
    @CachePut(value = "MenusController", key = "'menus-' + #menus.id")
    public Menus save(@RequestBody Menus menus) {
        return menusService.saveData(menus);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "菜单表")
    @CacheEvict(value = "MenusController", key = "'menus-' + #menus.id")
    public boolean delete(@RequestBody Menus menus) {
        return menusService.delete(menus);
    }

}
