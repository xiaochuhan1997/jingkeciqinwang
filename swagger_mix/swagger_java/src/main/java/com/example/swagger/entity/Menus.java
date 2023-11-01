package com.example.swagger.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;


/**
 * @author yinfeng
 * @description 菜单表
 * @TableName menus
 * @since 2022年3月12日 下午9:40:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单表")
@TableName("menus")
public class Menus {

    /**
     * 菜单id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "菜单id", example = "")
    private Long id;

    /**
     * 菜单等级
     */
    @TableField("level")
    @ApiModelProperty(value = "菜单等级", example = "")
    private Integer level;

    /**
     * 菜单名
     */
    @TableField("name")
    @ApiModelProperty(value = "菜单名", example = "")
    private String name;

    /**
     * 备注
     */
    @TableField("note")
    @ApiModelProperty(value = "备注", example = "")
    private String note;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", example = "")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间", example = "")
    private Date updateTime;

    /**
     * 删除标志
     */
    @TableField("deleted")
    @TableLogic
    @JsonIgnore
    @ApiModelProperty(value = "删除标志", example = "")
    private Integer deleted;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", example = "1")
    @TableField(exist = false)
    private Integer currentPage;

    /**
     * 分页页数
     */
    @ApiModelProperty(value = "分页页数", example = "20")
    @TableField(exist = false)
    private Integer pageSize;
}
