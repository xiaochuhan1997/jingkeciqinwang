package com.example.swagger.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(hidden=true)
//    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "状态")
    private Integer status;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @TableField(fill = FieldFill.INSERT )
    @JsonProperty("create_Time")
    @ApiModelProperty(value = "创建时间", name = "createTime", hidden = true)
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonProperty("update_Time" )
    @ApiModelProperty(value = "更新时间", name = "updateTime",hidden = true)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "用户详细信息列表")
    @TableField(exist = false)
    private List<UserDetail> userDetailList;

}
