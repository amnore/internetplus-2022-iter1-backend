package com.internetplus.bankpunishment.controller;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.po.TestPO;
import com.internetplus.bankpunishment.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Slf4j
@RestController
@RequestMapping("/api/bankpunishment")
public class bankPunishmentController {
    @Autowired
    BankPunishmentBl bankPunishmentBl;

    @PostMapping("/")
    public ResultVO insertBankPunishment(@RequestBody BankPunishment bankPunishment) {
        System.out.println(bankPunishment);
        Integer res = bankPunishmentBl.insertBankPunishment(bankPunishment);
        return ResultVO.buildSuccess(res);
    }

}
