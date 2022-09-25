package com.internetplus.bankpunishment.vo;

import com.internetplus.bankpunishment.entity.BankPunishment;
import lombok.Data;

import java.util.List;

@Data
public class BankPunishmentPageVO {
    private int resultSize;
    private List<BankPunishment> resList;

    public BankPunishmentPageVO(int resultSize, List<BankPunishment> resList) {
        this.resultSize = resultSize;
        this.resList = resList;
    }
}
