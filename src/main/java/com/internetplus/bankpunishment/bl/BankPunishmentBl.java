package com.internetplus.bankpunishment.bl;

import com.internetplus.bankpunishment.entity.BankPunishment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
public interface BankPunishmentBl {
    Long insertBankPunishment(BankPunishment bankPunishment);

    void updateBankPunishment(BankPunishment bankPunishment);

    void updateBankPunishmentExceptNull(BankPunishment bankPunishment);

    void deleteBankPunishment(Long id);

    void publishBankPunishment(Long id);

    BankPunishment selectBankPunishmentById(Long id);

    List<BankPunishment> selectBankPunishment(BankPunishment bankPunishment);
}
