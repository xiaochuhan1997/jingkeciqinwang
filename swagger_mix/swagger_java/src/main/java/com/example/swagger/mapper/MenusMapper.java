package com.example.swagger.mapper;

import com.example.swagger.entity.Menus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yinfeng
 * @since 2022年3月12日 下午9:40:48
 * @description 菜单表
 * @TableName menus
 */
@Mapper
public interface MenusMapper extends BaseMapper<Menus> {

}
