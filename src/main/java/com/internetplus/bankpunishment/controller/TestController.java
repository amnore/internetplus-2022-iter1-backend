package com.internetplus.bankpunishment.controller;

import com.internetplus.bankpunishment.bl.TestBl;
import com.internetplus.bankpunishment.po.TestPO;
import com.internetplus.bankpunishment.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: bank-punishment
 * @description: test
 * @author: xzh
 * @date: 2021-10-20
 **/
@Slf4j
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Resource
    TestBl testBl;

    @GetMapping("/{Id}")
    public ResultVO retrieveTest(@PathVariable("Id") Integer Id) {
        TestPO testPO = testBl.retriveTest(Id);
        return ResultVO.buildSuccess(testPO);
    }
}
