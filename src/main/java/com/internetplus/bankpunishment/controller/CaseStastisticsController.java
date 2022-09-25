package com.internetplus.bankpunishment.controller;

import com.internetplus.bankpunishment.bl.CaseStatisticsBl;
import com.internetplus.bankpunishment.entity.ApiResult;
import com.internetplus.bankpunishment.vo.CaseResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

/**
 * @program: bank-punishment
 * @description:
 * @author: gzx
 * @date: 2021-12-30
 **/
@Slf4j
@RestController
@RequestMapping("/api/statistics")
public class CaseStastisticsController {

    @Autowired
    CaseStatisticsBl caseStatisticsBl;

    @GetMapping("/PunishedPartyAndMoney")
    public ApiResult<CaseResultVO> getPunishedPartyAndMoney() {
        LinkedHashMap<String, Double> map;

        try {
            map = caseStatisticsBl.getCasesPunishedPartyAndMoney();
            CaseResultVO caseResultVO = new CaseResultVO(map, null);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/PunishedPartyAndTimes")
    public ApiResult<CaseResultVO> getPunishedPartyAndTimes() {
        LinkedHashMap<String, Integer> map;

        try {
            map = caseStatisticsBl.getCasesPunishedPartyAndTimes();
            CaseResultVO caseResultVO = new CaseResultVO(null, map);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/PunisherNameAndMoney")
    public ApiResult<CaseResultVO> getCasesPunisherNameAndMoney() {
        LinkedHashMap<String, Double> map;

        try {
            map = caseStatisticsBl.getCasesPunisherNameAndMoney();
            CaseResultVO caseResultVO = new CaseResultVO(map, null);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/PunisherNameAndTimes")
    public ApiResult<CaseResultVO> getCasesPunisherNameAndTimes() {
        LinkedHashMap<String, Integer> map;

        try {
            map = caseStatisticsBl.getCasesPunisherNameAndTimes();
            CaseResultVO caseResultVO = new CaseResultVO(null, map);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/PunishDateAndTimes")
    public ApiResult<CaseResultVO> getCasesPunishDateAndTimes() {
        LinkedHashMap<String, Integer> map;

        try {
            map = caseStatisticsBl.getCasesPunishDateAndTimes();
            CaseResultVO caseResultVO = new CaseResultVO(null, map);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/YearAndAverageMoney")
    public ApiResult<CaseResultVO> getCasesYearAndAverageMoney() {
        LinkedHashMap<String, Double> map;

        try {
            map = caseStatisticsBl.getCasesYearAndAverageMoney();
            CaseResultVO caseResultVO = new CaseResultVO(map, null);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }

    @GetMapping("/ProvinceAndTimes")
    public ApiResult<CaseResultVO> getCasesProvinceAndTimes() {
        LinkedHashMap<String, Integer> map;

        try {
            map = caseStatisticsBl.getCasesProvinceAndTimes();
            CaseResultVO caseResultVO = new CaseResultVO(null, map);
            return ApiResult.success(caseResultVO);
        } catch (Exception e) {
            return ApiResult.fail(e.getMessage());
        }
    }
}
