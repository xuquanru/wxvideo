package com.rootbant.wxapp.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rootbant.wxapp.entity.SearchRecords;
import com.rootbant.wxapp.entity.UsersLikeVideos;
import com.rootbant.wxapp.mapper.SearchRecordsMapper;
import com.rootbant.wxapp.mapper.UsersLikeVideosMapper;
import com.rootbant.wxapp.utils.PagedResult;
import com.rootbant.wxapp.utils.UuidUtils;
import com.rootbant.wxapp.vo.MyPublishVideoVo;
import com.rootbant.wxapp.vo.VideosVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.rootbant.wxapp.entity.Videos;
import com.rootbant.wxapp.mapper.VideosMapper;

@Service
public class VideosServiceImpl {

    @Resource
    private VideosMapper videosMapper;
    @Resource
    private SearchRecordsMapper recordsMapper;
    @Resource
    private UsersLikeVideosMapper usersLikeVideosMapper;

    
    public int insert(Videos videos){
        return videosMapper.insert(videos);
    }

    
    public int insertSelective(Videos videos){
        return videosMapper.insertSelective(videos);
    }

    
    public int insertList(List<Videos> videoss){
        return videosMapper.insertList(videoss);
    }

    
    public int updateByPrimaryKeySelective(Videos videos){
        return videosMapper.updateByPrimaryKeySelective(videos);
    }

    public int updateVideo(String videoId, String uploadPathDB) {
        return videosMapper.updateVideo(videoId,uploadPathDB);
    }

    public PagedResult getAllVideos(Videos videos,Integer isSaveRecord, Integer page, Integer pageSize) {

        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords searchRecords = new SearchRecords();
            searchRecords.setId(UuidUtils.getUuid());
            searchRecords.setContent(videos.getVideoDesc());
            recordsMapper.insert(searchRecords);
        }

        PageHelper.startPage(page, pageSize);
        List<VideosVo> listvideo=  videosMapper.getAllVideos(videos.getVideoDesc());
        PageInfo<VideosVo> pageInfo=new PageInfo<>(listvideo);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(listvideo);
        pagedResult.setRecords(pageInfo.getTotal());
        return  pagedResult;


    }

    public List<String> getHotwords() {
      return   videosMapper.getHotwords();
    }

    public PagedResult showVideo(Integer page, Integer pageSize,String userId,String searchContent) {


        PageHelper.startPage(page, pageSize);
        //判断有没有搜索内容，有的话带条件去搜索
        if (StringUtils.isNotBlank(searchContent)) {
            SearchRecords searchRecords = new SearchRecords();
            searchRecords.setId(UuidUtils.getUuid());
            searchRecords.setContent(searchContent);
            recordsMapper.insert(searchRecords);
        }


        List<MyPublishVideoVo> listvideo=  videosMapper.showVideo(searchContent);

        //判断用户有没有登录，没有的话直接跳过，否则修改喜爱视频
        //先查出用户的喜爱视频关系

        if(!StringUtils.isBlank(userId)){
            List<String> likes=  usersLikeVideosMapper.findUserLikeVideoByUserId(userId);

            List<MyPublishVideoVo> collect = listvideo.stream().map(video -> {
                String videoId = video.getId();
                boolean contains = likes.contains(videoId);
                video.setLike(contains);
                return video;
            }).collect(Collectors.toList());
            listvideo=collect;


        }



        PageInfo<MyPublishVideoVo> pageInfo=new PageInfo<>(listvideo);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(listvideo);
        pagedResult.setRecords(pageInfo.getTotal());
        return  pagedResult;
    }

    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
            //
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(UuidUtils.getUuid());
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        //videoCreaterId用来追加视频被喜爱的数量后期追加
        usersLikeVideosMapper.insert(usersLikeVideos);

    }

    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        usersLikeVideosMapper.delete(usersLikeVideos);
    }

    public boolean findUserLikeVideo(String userId, String videoId) {
        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);
        UsersLikeVideos videos=  usersLikeVideosMapper.findUserLikeVideo(usersLikeVideos);
        if(videos!=null){
            return true;
        }else{
            return false;
        }

    }
}
