package com.internetplus.bankpunishment.controller;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.po.TestPO;
import com.internetplus.bankpunishment.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/insert")
    public ResultVO insertBankPunishment(@RequestBody BankPunishment bankPunishment) {
        System.out.println("insert "+bankPunishment);
        try {
            Integer id = bankPunishmentBl.insertBankPunishment(bankPunishment);
            return ResultVO.buildSuccess(id);
        }catch (Exception e){
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/update/{exceptNull}")
    //如果不需要可以删掉ExceptNull参数
    public ResultVO updateBankPunishment(@RequestBody BankPunishment bankPunishment,@PathVariable boolean exceptNull) {
        System.out.println("update "+bankPunishment);
        try {
            if(exceptNull){//某字段若为null则不更新该字段
                bankPunishmentBl.updateBankPunishmentExceptNull(bankPunishment);
            }else {//全部字段强制覆盖
                bankPunishmentBl.updateBankPunishment(bankPunishment);
            }
            return ResultVO.buildSuccess("update successfully");
        }catch (Exception e){
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResultVO deleteBankPunishment(@RequestBody List<Integer> IDList) {
        try {
            for(Integer id:IDList){
                System.out.print("delete "+id+";");
                bankPunishmentBl.deleteBankPunishment(id);
            }
            System.out.println();
            return ResultVO.buildSuccess("delete successfully");
        }catch (Exception e){
            System.out.println();
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }

    @GetMapping("/select")//顺便写了一套select，有更好的写法可以把这个删掉
    public ResultVO selectBankPunishment(@RequestBody BankPunishment bankPunishment) {
        System.out.println("select "+bankPunishment);
        try {
            List<BankPunishment> bankPunishments = bankPunishmentBl.selectBankPunishment(bankPunishment);//根据非null字段搜索，全null则返回全体
            return ResultVO.buildSuccess(bankPunishments);
        }catch (Exception e){
            return ResultVO.buildFailure(500,e.getMessage());
        }
    }



}
