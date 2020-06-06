package com.rootbant.wxapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import com.rootbant.wxapp.entity.SearchRecords;

@Mapper
public interface SearchRecordsMapper {
    int insert(@Param("searchRecords") SearchRecords searchRecords);

    int insertSelective(@Param("searchRecords") SearchRecords searchRecords);

    int insertList(@Param("searchRecordss") List<SearchRecords> searchRecordss);

    int updateByPrimaryKeySelective(@Param("searchRecords") SearchRecords searchRecords);
}
