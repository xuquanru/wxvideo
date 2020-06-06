package com.rootbant.wxapp.service;

import com.rootbant.wxapp.entity.UsersFans;
import com.rootbant.wxapp.entity.Videos;
import com.rootbant.wxapp.mapper.UsersFansMapper;
import com.rootbant.wxapp.utils.UuidUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.rootbant.wxapp.entity.Users;
import com.rootbant.wxapp.mapper.UsersMapper;

@Service
public class UsersServiceImpl {

    @Resource
    private UsersMapper usersMapper;
    @Resource
    private UsersFansMapper usersFansMapper;

    
    public int insert(Users users){
        return usersMapper.insert(users);
    }

    
    public int insertSelective(Users users){
        return usersMapper.insertSelective(users);
    }

    
    public int insertList(List<Users> userss){
        return usersMapper.insertList(userss);
    }

    

    public boolean hasUserByName(String username) {
       Users user=  usersMapper.hasUserByName(username);
       if(user!=null){
           return true;
       }else{
           return false;
       }

    }

    public Users queryUserForLogin(String username, String md5Str) {
      return   usersMapper.queryUserForLogin(username,md5Str);
    }


    public  int updateUserInfo(Users user){
        return   usersMapper.updateByPrimaryKeySelective(user);
    }

    public Users queryUserInfo(String userId) {
        return   usersMapper.queryUserInfo(userId);
    }

    public boolean isUserLikeVideo(String loginUserId, String videoId) {
        Integer userLikeVideo = usersMapper.isUserLikeVideo(loginUserId, videoId);
        if(userLikeVideo>0){
        return true;
        }else{
            return false;
        }
    }

    public int saveUserFanRelation(String userId, String fanId) {
        UsersFans usersFans = new UsersFans();
        usersFans.setId(UuidUtils.getUuid());
        usersFans.setFanId(fanId);
        usersFans.setUserId(userId);
     return    usersFansMapper.insert(usersFans);
    }

    public int deleteUserFanRelation(String userId, String fanId) {
        UsersFans usersFans = new UsersFans();
        usersFans.setFanId(fanId);
        usersFans.setUserId(userId);
        return    usersFansMapper.delete(usersFans);

    }

    public boolean queryUserFans(String userId, String loginid) {
        UsersFans usersFans=new UsersFans();
        usersFans.setFanId(loginid);
        usersFans.setUserId(userId);

       usersFans=  usersFansMapper.queryUserFans( usersFans);
      if(usersFans!=null){
          return true;
      }else{
          return false;
      }
    }
}
