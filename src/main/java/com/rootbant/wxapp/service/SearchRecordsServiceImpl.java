package com.rootbant.wxapp.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.rootbant.wxapp.entity.SearchRecords;
import com.rootbant.wxapp.mapper.SearchRecordsMapper;

@Service
public class SearchRecordsServiceImpl {

    @Resource
    private SearchRecordsMapper searchRecordsMapper;

    
    public int insert(SearchRecords searchRecords){
        return searchRecordsMapper.insert(searchRecords);
    }

    
    public int insertSelective(SearchRecords searchRecords){
        return searchRecordsMapper.insertSelective(searchRecords);
    }

    
    public int insertList(List<SearchRecords> searchRecordss){
        return searchRecordsMapper.insertList(searchRecordss);
    }

    
    public int updateByPrimaryKeySelective(SearchRecords searchRecords){
        return searchRecordsMapper.updateByPrimaryKeySelective(searchRecords);
    }
}
