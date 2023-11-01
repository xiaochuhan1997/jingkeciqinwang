package com.example.swagger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swagger.entity.Menus;
import com.example.swagger.service.MenusService;
import com.example.swagger.mapper.MenusMapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author yinfeng
 * @since 2022年3月12日 下午9:40:48
 * @description 菜单表
 * @TableName menus
 */
@Service
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus>
        implements MenusService{

    @Override
    public IPage<Menus> list(Menus vo){
        final QueryWrapper<Menus> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotEmpty(vo.getId()), "id", vo.getId());
        return super.page(new Page<>(vo.getCurrentPage(), vo.getPageSize()), wrapper);
    }

    @Override
    public Menus getOne(Menus vo){
        final QueryWrapper<Menus> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtils.allNotNull(vo.getId()), "id", vo.getId());
        return super.getOne(wrapper);
    }

    @Override
    public Menus saveData(Menus vo) {
        super.saveOrUpdate(vo);
        return vo;
    }

    @Override
    public boolean delete(Menus vo) {
        final QueryWrapper<Menus> wrapper = new QueryWrapper<>();
        wrapper.eq(ObjectUtils.allNotNull(vo.getId()), "id", vo.getId());
        return super.remove(wrapper);
    }

}
