package com.example.swagger.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserDetail {
    @ApiModelProperty(value = "dhrId")
    private String dhrId;

    @ApiModelProperty(value = "numId")
    private String numId;

    @ApiModelProperty(value = "orgName")
    private String orgName;

    @ApiModelProperty(value = "orgNo")
    private String orgNo;

    @ApiModelProperty(value = "usapId")
    private String usapId;

    @ApiModelProperty(value = "userMobile")
    private String userMobile;

    @ApiModelProperty(value = "userName")
    private String userName;

    @ApiModelProperty(value = "userId")
    private Long userId;

}
