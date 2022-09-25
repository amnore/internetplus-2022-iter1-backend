package com.internetplus.bankpunishment.crawler.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yunthin.Chow
 * @date 2021/12/13
 * @description 案例库类实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CaseLibraryEntity {
    private long id;
    private String punishmentDocNo; // 行政处罚决定文号
    private String punishedPersonName; //被罚当事人名称
    private String punishedPartyName; //被罚当事人所在机构名称
    private String punishedPartyType; //被罚当事人所在机构类型
    public static String province; //被罚当事人所在省份（直辖市）
    private String mainIllegalFact; //主要违法违规事实（案由）
    private String punishmentLawName; //行政处罚依据法律名称
    private String punishmentBasis; //行政处罚依据
    private String punishmentDecision; //行政处罚决定
    private double punishmentMoney; //行政处罚金额
    private String punisherName; // 行政处罚的机关名称
    private String punishDate; // 作出处罚决定的日期
}

