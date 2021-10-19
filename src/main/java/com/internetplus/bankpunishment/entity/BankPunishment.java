package com.internetplus.bankpunishment.entity;

import lombok.Data;

@Data
public class BankPunishment {

    private Long id;

    /**
     * 行政处罚名称
     */
    private String punishmentName;

    /**
     * 行政处罚决定文号
     */
    private String punishmentDocNo;

    /**
     * 处罚类型：1.个人 2.单位
     */
    private String punishmentType;

    /**
     * 被罚当事人名称
     */
    private String punishedPartyName;

    /**
     * 主要负责人姓名
     */
    private String mainResponsibleName;

    /**
     * 主要违法违规事实（案由）
     */
    private String mainIllegalFact;

    /**
     * 行政处罚依据
     */
    private String punishmentBasis;

    /**
     * 行政处罚决定
     */
    private String punishmentDecision;

    /**
     * 行政处罚的机关名称
     */
    private String punisherName;

    /**
     * 作出处罚决定的日期
     */
    private String punishDate;

    /**
     * 状态：0.未发布 1.已发布
     */
    private String status;
}
