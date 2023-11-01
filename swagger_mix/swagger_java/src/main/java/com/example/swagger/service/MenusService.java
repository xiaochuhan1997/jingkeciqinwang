package com.example.swagger.service;

import com.example.swagger.entity.Menus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author yinfeng
 * @since 2022年3月12日 下午9:40:48
 * @description 菜单表
 * @TableName menus
 */
public interface MenusService extends IService<Menus> {

    /**
     * 查询列表
     *
     * @param vo vo
     * @return IPage<Menus>
     */
    IPage<Menus> list(Menus vo);

    /**
     * 单个查询
     *
     * @param vo vo
     * @return Menus
     */
    Menus getOne(Menus vo);

    /**
     * 保存
     *
     * @param vo vo
     * @return 是否保存成功
     */
    Menus saveData(Menus vo);

    /**
     * 删除
     *
     * @param vo vo
     * @return 是否删除成功
     */
    boolean delete(Menus vo);

}
