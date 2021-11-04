package com.internetplus.bankpunishment.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @description: 数据统计的类
 * @author: gzx
 * @date: 2021-11-04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankPunishmentStasticsVO {

    private HashMap<Integer, Integer> punishDateStastics;

    private HashMap<String, Integer> punishmentTypeStastics;
}
