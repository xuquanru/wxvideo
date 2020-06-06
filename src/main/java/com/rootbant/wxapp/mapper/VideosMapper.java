package com.rootbant.wxapp.mapper;

import com.rootbant.wxapp.vo.MyPublishVideoVo;
import com.rootbant.wxapp.vo.VideosVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.Videos;

@Mapper
public interface VideosMapper {
    int insert(@Param("videos") Videos videos);

    int insertSelective(@Param("videos") Videos videos);

    int insertList(@Param("videoss") List<Videos> videoss);

    int updateByPrimaryKeySelective(@Param("videos") Videos videos);

    int updateVideo(@Param("videoId")String videoId, @Param("uploadPathDB")String uploadPathDB);

    List<VideosVo> getAllVideos(@Param("desc")String desc);

    List<String> getHotwords();

    List<MyPublishVideoVo> showVideo(@Param("desc")String searchContent);

}
