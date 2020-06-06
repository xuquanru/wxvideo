package com.rootbant.wxapp.entity;

import lombok.Data;

@Data
public class UsersLikeVideos {
    /**
     *
     */
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 视频
     */
    private String videoId;

}

