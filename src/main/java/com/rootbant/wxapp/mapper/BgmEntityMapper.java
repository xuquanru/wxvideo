package com.rootbant.wxapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.BgmEntity;

@Mapper
public interface BgmEntityMapper {
    int insert(@Param("bgmEntity") BgmEntity bgmEntity);

    int insertSelective(@Param("bgmEntity") BgmEntity bgmEntity);

    int insertList(@Param("bgmEntitys") List<BgmEntity> bgmEntitys);

    int updateByPrimaryKeySelective(@Param("bgmEntity") BgmEntity bgmEntity);

    List<BgmEntity> queryBgmList();

    BgmEntity queryBgmById(@Param("bgmId") String bgmId);
}
