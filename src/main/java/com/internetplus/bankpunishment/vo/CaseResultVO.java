package com.internetplus.bankpunishment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

import java.util.LinkedHashMap;

/**
 * @description: 数据统计VO
 * @author: gzx
 * @date: 2021-12-30
 **/
@Data
@NoArgsConstructor
public class CaseResultVO {

    private LinkedHashMap<String, Double> doubleStatistics;

    private LinkedHashMap<String, Integer> integerStatistics;

    public CaseResultVO(LinkedHashMap<String, Double> dStatistics, LinkedHashMap<String, Integer> iStatistics) {
        if (dStatistics == null || dStatistics.isEmpty()) {
            doubleStatistics = new LinkedHashMap<>();
            doubleStatistics.put("empty", (double) 0);
        } else doubleStatistics = dStatistics;

        if (iStatistics == null || iStatistics.isEmpty()) {
            integerStatistics = new LinkedHashMap<>();
            integerStatistics.put("empty", 0);
        } else integerStatistics = iStatistics;
    }
}
