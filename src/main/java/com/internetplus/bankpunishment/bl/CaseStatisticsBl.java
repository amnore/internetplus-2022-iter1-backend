package com.internetplus.bankpunishment.bl;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @program: bank-punishment
 * @description:
 * @author: gzx
 * @date: 2021-12-30
 **/
public interface CaseStatisticsBl {
    LinkedHashMap<String, Double> getCasesPunishedPartyAndMoney();

    LinkedHashMap<String, Integer> getCasesPunishedPartyAndTimes();

    LinkedHashMap<String, Double> getCasesPunisherNameAndMoney();

    LinkedHashMap<String, Integer> getCasesPunisherNameAndTimes();

    LinkedHashMap<String, Integer> getCasesPunishDateAndTimes();

    LinkedHashMap<String, Double> getCasesYearAndAverageMoney();

    LinkedHashMap<String, Integer> getCasesProvinceAndTimes();
}
