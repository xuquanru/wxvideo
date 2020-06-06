package com.rootbant.wxapp.controller;

import com.rootbant.wxapp.entity.Users;
import com.rootbant.wxapp.service.UsersServiceImpl;
import com.rootbant.wxapp.utils.IMoocJSONResult;
import com.rootbant.wxapp.utils.MD5Utils;
import com.rootbant.wxapp.vo.PublisherVideo;
import com.rootbant.wxapp.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-03 18:28
 **/
@RestController
@Api(value = "用户相关业务接口",tags ="用户相关业务controller" )
@RequestMapping("user")
public class UserController extends  BasicController{
    @Value("${myfile.path}")
    private String filepath;
    @Resource
    private UsersServiceImpl usersService;

    @ApiOperation(value = "用户上传头像",notes = "用户上传头像接口")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true ,dataType = "String",paramType = "query")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId,@RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isBlank(userId)) {
            return  IMoocJSONResult.errorMsg("用户ID不能为空");
        }

        String filespace=filepath;
        String uploadPathDB="/"+userId+"/face";
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        try {
            if(files!=null&&files.length>0){

                String filename = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(filename)) {
                    String finalFacePath=filespace+uploadPathDB+"/"+filename;
                    //设置数据库保存位置
                    uploadPathDB += ("/" + filename);
                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile()!=null||!outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream=files[0].getInputStream();
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

            Users user=new Users();
            user.setId(userId);
            user.setFaceImage(uploadPathDB);
        usersService.updateUserInfo(user);

        return IMoocJSONResult.ok(uploadPathDB);
    }


    @PostMapping("/query")
    @ApiOperation(value = "查询用户信息",notes = "查询用户信息接口")
    @ApiImplicitParam(name="userId",value = "用户ID",required = true,dataType = "string",paramType = "query")
    public  IMoocJSONResult queryUserInfo(String userId,String loginid){

        if (StringUtils.isBlank(userId)) {
            IMoocJSONResult.errorMsg("用户ID不能为空");
        }
        Users user=   usersService.queryUserInfo(userId);

        //查询登陆者和发布者的消息
        boolean flag =usersService.queryUserFans(userId,loginid);


        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user,usersVo);
        usersVo.setFollow(flag);
        return IMoocJSONResult.ok(usersVo);

    }


    @PostMapping("/queryPublisher")
    public IMoocJSONResult queryPublisher(String loginUserId, String videoId,
                                          String publishUserId) throws Exception {

        if (StringUtils.isBlank(publishUserId)) {
            return IMoocJSONResult.errorMsg("发布视频者ID不能为空");
        }

        // 1. 查询视频发布者的信息
        Users userInfo = usersService.queryUserInfo(publishUserId);
        UsersVo publisher = new UsersVo();
        BeanUtils.copyProperties(userInfo, publisher);
        // 2. 查询当前登录者和视频的点赞关系
        boolean userLikeVideo = usersService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);
        return IMoocJSONResult.ok(bean);
    }


    @PostMapping("/beyourfans")
    public IMoocJSONResult beyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return IMoocJSONResult.errorMsg("参数不能为空！");
        }
        usersService.saveUserFanRelation(userId, fanId);
        //
        Users users = usersService.queryUserInfo(userId);
        users.setFansCounts(users.getFansCounts()+1);
        usersService.updateUserInfo(users);
        return IMoocJSONResult.ok("关注成功...");
    }

    @PostMapping("/dontbeyourfans")
    public IMoocJSONResult dontbeyourfans(String userId, String fanId) throws Exception {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return IMoocJSONResult.errorMsg("");
        }
        usersService.deleteUserFanRelation(userId, fanId);
        Users users = usersService.queryUserInfo(userId);
        users.setFansCounts(users.getFansCounts()-1);
        usersService.updateUserInfo(users);

        return IMoocJSONResult.ok("取消关注成功...");
    }


}
