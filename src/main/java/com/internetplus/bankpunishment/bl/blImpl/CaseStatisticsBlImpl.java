package com.internetplus.bankpunishment.bl.blImpl;

import com.internetplus.bankpunishment.bl.CaseStatisticsBl;
import com.internetplus.bankpunishment.data.CaseStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CaseStatisticsBlImpl implements CaseStatisticsBl {

    @Autowired
    CaseStatisticsMapper caseStatisticsMapper;

    private static final Integer startYear = 2014;
    private static final Integer endYear = 2022;

    @Override
    public LinkedHashMap<String, Double> getCasesPunishedPartyAndMoney(){
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getPunishedPartyAndMoney();
        return listToDoubleHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesPunishedPartyAndTimes() {
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getPunishedPartyAndTimes();
        return listToIntegerHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Double> getCasesPunisherNameAndMoney() {
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getPunisherNameAndMoney();
        return listToDoubleHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesPunisherNameAndTimes() {
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getPunisherNameAndTimes();
        return listToIntegerHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesPunishDateAndTimes() {
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getPunishDateAndTimes();
        return listToIntegerHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Double> getCasesYearAndAverageMoney() {
        LinkedHashMap<String, Double> list = new LinkedHashMap<>();

        for (int year = startYear; year <= endYear; year++) {
            Double average = caseStatisticsMapper.getYearAndAverageMoney(String.valueOf(year));
            list.put(String.valueOf(year), average);
        }

        return list;
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesProvinceAndTimes() {
        List<LinkedHashMap<String, Object>> list = caseStatisticsMapper.getProvinceAndTimes();
        return listToIntegerHashmap(list);
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesYearAndTimes() {
        // TODO implement this
        LinkedHashMap<String, Integer> list = new LinkedHashMap<>();
        list.put("2018", 1);
        list.put("2019", 2);
        list.put("2020", 3);
        return list;
    }

    @Override
    public LinkedHashMap<String, Integer> getCasesMoneyDistribution() {
        // TODO implement this
        LinkedHashMap<String, Integer> list = new LinkedHashMap<>();
        list.put("<10000", 1);
        list.put("10000-20000", 2);
        list.put("20000-30000", 3);
        list.put(">=30000", 4);
        return list;
    }

    private LinkedHashMap<String, Integer> listToIntegerHashmap(List<LinkedHashMap<String, Object>> list) {
        LinkedHashMap<String, Integer> resultMap = new LinkedHashMap<>();

        if (list != null && !list.isEmpty()){

            for (HashMap<String, Object> hashMap : list) {
                String key = null;
                Integer value = null;
                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                    if ("key".equals(entry.getKey())) key = (String) entry.getValue();
                    else if ("value".equals(entry.getKey())) value = ((Long) entry.getValue()).intValue();
                }
                if (key != null) {
                    resultMap.put(key, value);
                }
            }
        }

        return resultMap;
    }

    private LinkedHashMap<String, Double> listToDoubleHashmap(List<LinkedHashMap<String, Object>> list) {
        LinkedHashMap<String, Double> resultMap = new LinkedHashMap<>();

        if (list != null && !list.isEmpty()){
            for (HashMap<String, Object> hashMap : list) {
                String key = null;
                Double value = null;
                for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
                    if ("key".equals(entry.getKey())) key = (String) entry.getValue();
                    else if ("value".equals(entry.getKey())) value = (Double) entry.getValue();
                }
                if (key != null) {
                    resultMap.put(key, value);
                }
            }
        }

        return resultMap;
    }
}
