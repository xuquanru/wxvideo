package com.rootbant.wxapp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.rootbant.wxapp.entity.UsersLikeVideos;
import com.rootbant.wxapp.mapper.UsersLikeVideosMapper;

@Service
public class UsersLikeVideosServiceImpl {

    @Resource
    private UsersLikeVideosMapper usersLikeVideosMapper;

    
    public int insert(UsersLikeVideos usersLikeVideos){
        return usersLikeVideosMapper.insert(usersLikeVideos);
    }

    
    public int insertSelective(UsersLikeVideos usersLikeVideos){
        return usersLikeVideosMapper.insertSelective(usersLikeVideos);
    }

    
    public int insertList(List<UsersLikeVideos> usersLikeVideoss){
        return usersLikeVideosMapper.insertList(usersLikeVideoss);
    }

    
    public int updateByPrimaryKeySelective(UsersLikeVideos usersLikeVideos){
        return usersLikeVideosMapper.updateByPrimaryKeySelective(usersLikeVideos);
    }
}
