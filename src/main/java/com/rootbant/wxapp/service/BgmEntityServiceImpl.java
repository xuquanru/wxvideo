package com.rootbant.wxapp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.rootbant.wxapp.entity.BgmEntity;
import com.rootbant.wxapp.mapper.BgmEntityMapper;

@Service
public class BgmEntityServiceImpl {

    @Resource
    private BgmEntityMapper bgmEntityMapper;

    
    public int insert(BgmEntity bgmEntity){
        return bgmEntityMapper.insert(bgmEntity);
    }

    
    public int insertSelective(BgmEntity bgmEntity){
        return bgmEntityMapper.insertSelective(bgmEntity);
    }

    
    public int insertList(List<BgmEntity> bgmEntitys){
        return bgmEntityMapper.insertList(bgmEntitys);
    }

    
    public int updateByPrimaryKeySelective(BgmEntity bgmEntity){
        return bgmEntityMapper.updateByPrimaryKeySelective(bgmEntity);
    }

    public List<BgmEntity> queryBgmList() {
        return bgmEntityMapper.queryBgmList();
    }

    public BgmEntity queryBgmById(String bgmId) {
        return bgmEntityMapper.queryBgmById(bgmId);
    }
}
