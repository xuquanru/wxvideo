package com.rootbant.wxapp.vo;

import lombok.Data;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-09 13:21
 **/
@Data
public class MyPublishVideoVo {
    private String id;
    private Integer videoWidth;
    private Integer videoHeight;
    private String videoPath;
    private String videoDesc;
    private String publisherId;
    private String publisherFace;
    private String publisherNickname;
    private boolean isLike=false;
    private String videoCoverPath;

}
