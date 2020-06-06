package com.rootbant.wxapp.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户对象",description = "用户对象描述")
public class UsersVo {
    /**
     *
     */
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    private String usertoken;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名字",name = "username",example = "StephenXu",required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "用户密码",name = "password",example = "123456",required = true)
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /**
     * 昵称
     */
    @ApiModelProperty(hidden = true)
    private String nickname;

    /**
     * 我的粉丝数量
     */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

    private boolean follow;






}

