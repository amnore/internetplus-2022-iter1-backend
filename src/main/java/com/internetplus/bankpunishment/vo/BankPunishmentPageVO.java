package com.internetplus.bankpunishment.vo;

import com.internetplus.bankpunishment.entity.BankPunishment;
import lombok.Data;

import java.util.List;

@Data
public class BankPunishmentPageVO {
    private int maxPageNO;
    private List<BankPunishment> resList;

    public BankPunishmentPageVO(int maxPageNO, List<BankPunishment> resList) {
        this.maxPageNO = maxPageNO;
        this.resList = resList;
    }
}
