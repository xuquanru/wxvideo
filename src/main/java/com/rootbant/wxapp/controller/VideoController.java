package com.rootbant.wxapp.controller;

import com.rootbant.wxapp.entity.BgmEntity;
import com.rootbant.wxapp.entity.Users;
import com.rootbant.wxapp.entity.Videos;
import com.rootbant.wxapp.service.BgmEntityServiceImpl;
import com.rootbant.wxapp.service.UsersServiceImpl;
import com.rootbant.wxapp.service.VideosServiceImpl;
import com.rootbant.wxapp.utils.FFMpegUtils;
import com.rootbant.wxapp.utils.IMoocJSONResult;
import com.rootbant.wxapp.utils.PagedResult;
import com.rootbant.wxapp.utils.UuidUtils;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-05 20:32
 **/
@RestController
@Api(value="视频相关业务的接口", tags= {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController {
    @Value("${myfile.path}")
    private String filepath;
    @Value("${myfile.ffmpeg}")
    private String ffmpegpath;
    @Value("${myfile.bgmpath}")
    private String bgmpath;
    @Value("${myfile.logo}")
    private  String logopath;

    @Resource
    private VideosServiceImpl videosService;

    @Resource
    private BgmEntityServiceImpl bgmService;

    @Resource
    private UsersServiceImpl usersService;


    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="bgmId", value="背景音乐id", required=false,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoWidth", value="视频宽度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoHeight", value="视频高度", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="desc", value="视频描述", required=false,
                    dataType="String", paramType="form")
    })
    @ApiOperation(value="上传视频", notes="上传视频的接口")
    @PostMapping(value="/upload", headers="content-type=multipart/form-data")
    public IMoocJSONResult upload(String userId,
                                  String bgmId, double videoSeconds,
                                  int videoWidth, int videoHeight,
                                  String desc,
                                  @ApiParam(value="短视频", required=true)
                                          MultipartFile file) throws Exception {



        String uploadPathDB="/"+userId+"/video";
        String coverPathDB = "/" + userId + "/video";
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String finalVideoPath = "";
        try {
            if(file!=null){

                String filename = file.getOriginalFilename();
                String arrayFilenameItem[] =  filename.split("\\.");
                String fileNamePrefix = "";
                for (int i = 0 ; i < arrayFilenameItem.length-1 ; i ++) {
                    fileNamePrefix += arrayFilenameItem[i];
                }


                if (StringUtils.isNotBlank(filename)) {
                    finalVideoPath=filepath+uploadPathDB+"/"+filename;
                    //设置数据库保存位置
                    uploadPathDB += ("/" + filename);
                    coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";
                    File outFile = new File(finalVideoPath);
                    if(outFile.getParentFile()!=null||!outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream=file.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                }
            }else {
                return IMoocJSONResult.errorMsg("上传出错！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错！");
        }finally {
            fileOutputStream.flush();
            fileOutputStream.close();
        }


        // 判断bgmId是否为空，如果不为空，
        // 那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
         BgmEntity bgm= bgmService.queryBgmById(bgmId);
            String mp3InputPath = bgmpath + bgm.getPath();
            String videoInputPath = finalVideoPath;
            String videoOutputName = UUID.randomUUID().toString().replace("-","") + ".mp4";
            uploadPathDB = "/" + userId + "/video" + "/" + videoOutputName;
            finalVideoPath = filepath + uploadPathDB;
            FFMpegUtils tool=new FFMpegUtils(ffmpegpath);
            tool.Merge(mp3InputPath,videoInputPath,logopath,videoSeconds,finalVideoPath);
            System.err.println("uploadPathDB=" + uploadPathDB);
            System.err.println("finalVideoPath=" + finalVideoPath);
        }






        //对视频截图
        FFMpegUtils tool=new FFMpegUtils(ffmpegpath);
        tool.cover(finalVideoPath,filepath+coverPathDB);


        // 保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds(videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDB);
        video.setCoverPath(coverPathDB);
        video.setStatus(1);
        video.setLikeCounts(0l);
        video.setCreateTime(new Date());
        video.setId(UuidUtils.getUuid());
        videosService.insert(video);
        return IMoocJSONResult.ok(video.getId());
    }


    @ApiOperation(value="上传封面", notes="上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", required=true,
                    dataType="String", paramType="form"),
            @ApiImplicitParam(name="videoId", value="视频主键id", required=true,
                    dataType="String", paramType="form")
    })
    @PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
    public IMoocJSONResult uploadCover(String userId,
                                       String videoId,
                                       @ApiParam(value="视频封面", required=true)
                                               MultipartFile file) throws Exception {

        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
            return IMoocJSONResult.errorMsg("视频主键id和用户id不能为空...");
        }

        // 保存到数据库中的相对路径
        String uploadPathDB = "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件上传的最终保存路径
        String finalCoverPath = "";

        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {
                    finalCoverPath = filepath + uploadPathDB + "/" + fileName;
                    // 设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalCoverPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        // 创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }

            } else {
                return IMoocJSONResult.errorMsg("上传出错...");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return IMoocJSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        videosService.updateVideo(videoId, uploadPathDB);
        return IMoocJSONResult.ok();

    }

    @PostMapping(value="/showAll")
    @ApiOperation(value = "查询视频列表",notes ="查询视频列表接口" )
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前第几页",name ="page",type = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "每页大小",name ="pageSize",type = "Integer",paramType = "query"),
            @ApiImplicitParam(value = "是否搜索",name ="isSaveRecord",type = "Integer",paramType = "query")
    })
    public IMoocJSONResult showAll(@RequestBody Videos video,Integer page, Integer pageSize,Integer isSaveRecord) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }

        PagedResult result = videosService.getAllVideos(video,isSaveRecord,page, pageSize);
        return IMoocJSONResult.ok(result);
    }

    @PostMapping(value="/hot")
    @ApiOperation(value = "查询热门搜索词",notes ="查询热门搜索词接口" )
    public IMoocJSONResult hot() throws Exception {
        return IMoocJSONResult.ok(videosService.getHotwords());
    }

    @PostMapping(value="/showvideos")
    @ApiOperation(value = "查询滑动视频",notes ="查询滑动视频接口" )
    public IMoocJSONResult showVideo(String searchContent,Integer page, Integer pageSize,String userId) throws Exception {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 5;
        }

        PagedResult result = videosService.showVideo(page,pageSize,userId,searchContent);



        return IMoocJSONResult.ok(result);

    }


    @PostMapping(value="/userLike")
    public IMoocJSONResult userLike(String userId, String videoId, String videoCreaterId)
            throws Exception {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(videoId)) {
            return IMoocJSONResult.errorMsg("用户id和视频ID不能为空");
        }
        videosService.userLikeVideo(userId, videoId, videoCreaterId);

        //查询用户信息
        Users users = usersService.queryUserInfo(videoCreaterId);
        users.setFollowCounts(users.getFollowCounts()+1);
        return IMoocJSONResult.ok();
    }

    @PostMapping(value="/userUnLike")
    public IMoocJSONResult userUnLike(String userId, String videoId, String videoCreaterId) throws Exception {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(videoId)) {
            return IMoocJSONResult.errorMsg("用户id和视频ID不能为空");
        }

        videosService.userUnLikeVideo(userId, videoId, videoCreaterId);
        //查询用户信息
        Users users = usersService.queryUserInfo(videoCreaterId);
        users.setFollowCounts(users.getFollowCounts()-1);
        return IMoocJSONResult.ok();
    }

    @PostMapping(value="/isLike")
    public IMoocJSONResult isLike(String userId, String videoId){
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(videoId)) {
            return IMoocJSONResult.errorMsg("用户id和视频ID不能为空");
        }
       boolean flag= videosService.findUserLikeVideo(userId,videoId);
        return IMoocJSONResult.ok(flag);
    }

}
