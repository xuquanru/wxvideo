package com.rootbant.wxapp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.rootbant.wxapp.entity.UsersFans;
import com.rootbant.wxapp.mapper.UsersFansMapper;

@Service
public class UsersFansServiceImpl {

    @Resource
    private UsersFansMapper usersFansMapper;

    
    public int insert(UsersFans usersFans){
        return usersFansMapper.insert(usersFans);
    }

    
    public int insertSelective(UsersFans usersFans){
        return usersFansMapper.insertSelective(usersFans);
    }

    
    public int insertList(List<UsersFans> usersFanss){
        return usersFansMapper.insertList(usersFanss);
    }

    
    public int updateByPrimaryKeySelective(UsersFans usersFans){
        return usersFansMapper.updateByPrimaryKeySelective(usersFans);
    }
}
