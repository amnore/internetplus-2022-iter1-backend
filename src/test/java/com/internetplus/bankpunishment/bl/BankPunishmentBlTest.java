package com.internetplus.bankpunishment.bl;

import com.internetplus.bankpunishment.bl.blImpl.BankPunishmentBlImpl;
import com.internetplus.bankpunishment.entity.BankPunishment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankPunishmentBlTest {

    @Resource
    BankPunishmentBlImpl bankPunishmentBl;

    @Test
    void selectBankPunishmentById() {
        BankPunishment bankPunishment = bankPunishmentBl.selectBankPunishmentById(2);
        System.out.println(bankPunishment.getPunishmentDocNo());
    }

    @Test
    void selectBankPunishment() {
        BankPunishment bankPunishment = new BankPunishment();
//        bankPunishment.setPunishmentName("s");
        bankPunishment.setStatus("1");
        List<BankPunishment> punishments = bankPunishmentBl.selectBankPunishment(bankPunishment);
        System.out.println(punishments.size());
    }
}