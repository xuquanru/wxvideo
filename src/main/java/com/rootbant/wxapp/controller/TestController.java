package com.rootbant.wxapp.controller;

import com.rootbant.wxapp.utils.FFMpegUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-12 16:13
 **/
@RestController
@RequestMapping("test")
public class TestController {
    @Value("${myfile.path}")
    private String filepath;
    @Value("${myfile.ffmpeg}")
    private String ffmpegpath;
    @Value("${myfile.bgmpath}")
    private String bgmpath;
    @Value("${myfile.logo}")
    private  String logopath;

    @Value("${myfile.testmp3}")
    private  String testmp3;
    @Value("${myfile.testmp4}")
    private  String testmp4;
    @Value("${myfile.testout}")
    private  String testout;



    @RequestMapping("merge")
    public String merge() throws Exception {
        FFMpegUtils ffmpeg = new FFMpegUtils(ffmpegpath);
        ffmpeg.Merge(testmp3,
                testmp4,
                logopath,
					8,
                testout);

        return "OK";
    }
}
