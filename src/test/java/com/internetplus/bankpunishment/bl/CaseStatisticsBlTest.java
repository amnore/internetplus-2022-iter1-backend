package com.internetplus.bankpunishment.bl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CaseStatisticsBlTest {

    @Resource
    CaseStatisticsBl caseStatisticsBl;

    @Test
    void punishedPartyNameAndMoney() {
        LinkedHashMap<String, Double> punishedPartyAndMoneyMap = caseStatisticsBl.getCasesPunishedPartyAndMoney();
        System.out.println(punishedPartyAndMoneyMap);
    }

    @Test
    void punishedPartyNameAndTimes() {
        LinkedHashMap<String, Integer> punishedPartyAndTimesMap = caseStatisticsBl.getCasesPunishedPartyAndTimes();
        System.out.println(punishedPartyAndTimesMap);
    }

    @Test
    void punisherNameAndMoney() {
        LinkedHashMap<String, Double> punisherNameAndMoneyMap = caseStatisticsBl.getCasesPunisherNameAndMoney();
        System.out.println(punisherNameAndMoneyMap);
    }

    @Test
    void punisherNameAndTimes() {
        LinkedHashMap<String, Integer> punisherNameAndTimesMap = caseStatisticsBl.getCasesPunisherNameAndTimes();
        System.out.println(punisherNameAndTimesMap);
    }

    @Test
    void punishDateAndTimes() {
        LinkedHashMap<String, Integer> punishDateAndTimes = caseStatisticsBl.getCasesPunishDateAndTimes();
        System.out.println(punishDateAndTimes);
    }

    @Test
    void yearAndAverageMoney() {
        LinkedHashMap<String, Double> yearAndAverageMoney = caseStatisticsBl.getCasesYearAndAverageMoney();
        System.out.println(yearAndAverageMoney);
    }

    @Test
    void provinceAndTimes() {
        LinkedHashMap<String, Integer> provinceAndTimes = caseStatisticsBl.getCasesProvinceAndTimes();
        System.out.println(provinceAndTimes);
    }
}