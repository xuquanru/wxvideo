package com.rootbant.wxapp.mapper;

import com.rootbant.wxapp.entity.Videos;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.Users;

@Mapper
public interface UsersMapper {
    int insert(@Param("users") Users users);

    int insertSelective(@Param("users") Users users);

    int insertList(@Param("userss") List<Users> userss);

    int updateByPrimaryKeySelective(@Param("users") Users users);

    Users hasUserByName(@Param("username")String username);

    Users queryUserForLogin(@Param("username")String username, @Param("password")String password);

    Users queryUserInfo(@Param("userId")String userId);

    Integer isUserLikeVideo(@Param("loginUserId")String loginUserId, @Param("videoId")String videoId);
}
