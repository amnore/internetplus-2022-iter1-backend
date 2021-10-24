package com.internetplus.bankpunishment.bl;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
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

    Long addCrawlerBankPunishment(DataEntity dataEntity);

    boolean updateBankPunishment(BankPunishment bankPunishment);

    boolean updateBankPunishmentExceptNull(BankPunishment bankPunishment);

    boolean deleteBankPunishment(Long id);

    boolean publishBankPunishment(Long id);

    BankPunishment selectBankPunishmentById(Long id);

    List<BankPunishment> selectBankPunishment(BankPunishment bankPunishment);
}
