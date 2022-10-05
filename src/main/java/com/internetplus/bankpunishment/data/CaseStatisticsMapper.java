package com.internetplus.bankpunishment.data;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @program: bank-punishment
 * @description:
 * @author: gzx
 * @date: 2021-12-30
 **/
@Mapper
@Repository
public interface CaseStatisticsMapper {

    /**
     * x：被罚当事人所在机构名称
     * y：处罚金额/处罚次数
     * @return List<HashMap<String, Object>>
     */
    List<LinkedHashMap<String, Object>> getPunishedPartyAndMoney();
    List<LinkedHashMap<String, Object>> getPunishedPartyAndTimes();

    /**
     * x：行政处罚的机关名称
     * y：处罚金额/处罚次数
     */
    List<LinkedHashMap<String, Object>> getPunisherNameAndMoney();
    List<LinkedHashMap<String, Object>> getPunisherNameAndTimes();

    /**
     * x：日期
     * y：行政处罚次数
     */
    List<LinkedHashMap<String, Object>> getPunishDateAndTimes();

    /**
     * x：年份
     * y：平均金额
     */
    Double getYearAndAverageMoney(String year);

    /**
     * 省份/次数
     */
    List<LinkedHashMap<String, Object>> getProvinceAndTimes();

    Integer getTimesByYear(Integer year);
    Integer getTimesByMoneyRange(Double begin, Double end);
}
