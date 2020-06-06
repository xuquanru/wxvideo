package com.rootbant.wxapp.controller;

import com.rootbant.wxapp.entity.Users;
import com.rootbant.wxapp.service.UsersServiceImpl;
import com.rootbant.wxapp.utils.IMoocJSONResult;
import com.rootbant.wxapp.utils.MD5Utils;
import com.rootbant.wxapp.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
@Api(value = "用户注册登录接口",tags ="注册登录的controller" )
public class RegistLoginController extends  BasicController{
    @Resource
    private UsersServiceImpl usersService;

    @PostMapping("/regist")
    @ApiOperation(value = "用户注册接口")
    public IMoocJSONResult regist(@RequestBody Users user) throws Exception {
    //        1.判断用户不能为空
        if (StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())) {
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }

        //判断用户是否存在
       boolean userIsExist= usersService.hasUserByName(user.getUsername());
        if(!userIsExist){
            user.setNickname(user.getUsername());
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            usersService.insert(user);

        }else{
            return IMoocJSONResult.errorMsg("用户已经存在，请换一个再试试");
        }
        user.setPassword("");
        UsersVo userVo = setUserRedisSessionToken(user);
        return  IMoocJSONResult.ok(userVo);
    }



    @ApiOperation(value="用户登录", notes="用户登录的接口")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

//		Thread.sleep(3000);

        // 1. 判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return IMoocJSONResult.ok("用户名或密码不能为空...");
        }

        // 2. 判断用户是否存在
        Users userResult = usersService.queryUserForLogin(username,
                MD5Utils.getMD5Str(user.getPassword()));

        // 3. 返回
        if (userResult != null) {
            userResult.setPassword("");

            //这里采用redis存储登录信息

            UsersVo userVo = setUserRedisSessionToken(userResult);
            return IMoocJSONResult.ok(userVo);
        } else {
            return IMoocJSONResult.errorMsg("用户名或密码不正确, 请重试...");
        }
    }

    public UsersVo setUserRedisSessionToken(Users userModel) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + userModel.getId(), uniqueToken, 1000 * 60 * 30);

        UsersVo userVo = new UsersVo();
        BeanUtils.copyProperties(userModel, userVo);
        userVo.setUsertoken(uniqueToken);
        return userVo;
    }

    @ApiOperation(value = "用户注销",notes = "用户注销接口")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true ,dataType = "String",paramType = "query")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) throws Exception {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return IMoocJSONResult.ok();
    }


}
