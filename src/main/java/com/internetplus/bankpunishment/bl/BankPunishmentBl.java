package com.internetplus.bankpunishment.bl;

import com.internetplus.bankpunishment.entity.BankPunishment;
import org.apache.ibatis.annotations.Param;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
public interface BankPunishmentBl {
    Integer insertBankPunishment( BankPunishment bankPunishment);
}