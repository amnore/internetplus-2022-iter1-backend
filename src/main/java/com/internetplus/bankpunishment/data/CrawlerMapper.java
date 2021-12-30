package com.internetplus.bankpunishment.data;

import com.internetplus.bankpunishment.crawler.pojo.CaseLibraryEntity;
import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @date 2021/12/18
 * @description 爬虫相关数据操作层
 */
@Mapper
@Repository
public interface CrawlerMapper {

    List<DataEntity> getDataEntityListByOffsetAndLimit(@Param("limitNum") int limitNum, @Param("offsetNum") int offsetNum);

    long getDataEntityNum();

    void addCaseLibraryEntity(CaseLibraryEntity caseLibraryEntity);
}
