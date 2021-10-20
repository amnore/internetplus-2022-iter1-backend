package com.internetplus.bankpunishment.bl.blImpl;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Service
public class BankPunishmentBlImpl implements BankPunishmentBl {

    @Autowired
    BankPunishmentMapper bankPunishmentMapper;
    @Override
    public Integer insertBankPunishment(BankPunishment bankPunishment) {
        return bankPunishmentMapper.insertBankPunishment(bankPunishment);
    }
}
