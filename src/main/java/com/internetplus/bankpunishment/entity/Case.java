package com.internetplus.bankpunishment.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Case {

    @JSONField(serializeUsing = ToStringSerializer.class)//使用fastjson自动转化long和string
    private Long id;

    /**
     * 行政处罚决定文号
     */
    private String punishmentDocNo;

    /**
     * 被处罚人姓名
     */
    private String punishedPersonName;

    /**
     * 被罚组织名称
     */
    private String punishedPartyName;

    /**
     * 省份
     */
    private String province;

    /**
     * 被罚组织类型
     */
    private String punishedPartyType;

    /**
     * 主要违法违规事实（案由）
     */
    private String mainIllegalFact;

    /**
     * 违反法律
     */
    private String punishmentLawName;

    /**
     * 行政处罚依据
     */
    private String punishmentBasis;

    /**
     * 行政处罚决定
     */
    private String punishmentDecision;

    /**
     * 罚款金额
     */
    private Double punishmentMoney;

    /**
     * 行政处罚的机关名称
     */
    private String punisherName;

    /**
     * 作出处罚决定的日期
     */
    private String punishDate;
}
