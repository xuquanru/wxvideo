package com.rootbant.wxapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.UsersFans;

@Mapper
public interface UsersFansMapper {
    int insert(@Param("usersFans") UsersFans usersFans);

    int insertSelective(@Param("usersFans") UsersFans usersFans);

    int insertList(@Param("usersFanss") List<UsersFans> usersFanss);

    int updateByPrimaryKeySelective(@Param("usersFans") UsersFans usersFans);

    int delete(@Param("usersFans") UsersFans usersFans);

    UsersFans queryUserFans(@Param("usersFans") UsersFans usersFans);

}
