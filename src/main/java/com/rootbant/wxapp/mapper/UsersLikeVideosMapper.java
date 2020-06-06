package com.rootbant.wxapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.UsersLikeVideos;

@Mapper
public interface UsersLikeVideosMapper {
    int insert(@Param("usersLikeVideos") UsersLikeVideos usersLikeVideos);

    int insertSelective(@Param("usersLikeVideos") UsersLikeVideos usersLikeVideos);

    int insertList(@Param("usersLikeVideoss") List<UsersLikeVideos> usersLikeVideoss);

    int updateByPrimaryKeySelective(@Param("usersLikeVideos") UsersLikeVideos usersLikeVideos);

    int delete(@Param("usersLikeVideos")UsersLikeVideos usersLikeVideos);

    UsersLikeVideos findUserLikeVideo(@Param("usersLikeVideos")UsersLikeVideos usersLikeVideos);

    List<String> findUserLikeVideoByUserId(@Param("userId")String userId);
}
