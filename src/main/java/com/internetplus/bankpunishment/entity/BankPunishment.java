package com.internetplus.bankpunishment.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankPunishment {

    @JSONField(serializeUsing = ToStringSerializer.class)//使用fastjson自动转化long和string
    public Long id;

//    public String stringId;//前后端传输long有精度损失，故用string传，也可以用fastjson

    /**
     * 行政处罚名称
     */
    public String punishmentName;

    /**
     * 行政处罚决定文号
     */
    public String punishmentDocNo;

    /**
     * 处罚类型：1.个人 2.单位
     */
    public String punishmentType;

    /**
     * 被罚当事人名称
     */
    public String punishedPartyName;

    /**
     * 主要负责人姓名
     */
    public String mainResponsibleName;

    /**
     * 主要违法违规事实（案由）
     */
    public String mainIllegalFact;

    /**
     * 行政处罚依据
     */
    public String punishmentBasis;

    /**
     * 行政处罚决定
     */
    public String punishmentDecision;

    /**
     * 行政处罚的机关名称
     */
    public String punisherName;

    /**
     * 作出处罚决定的日期
     */
    public String punishDate;

    // 省份
    public String province;

    /**
     * 状态：0.未发布 1.已发布
     */
    public String status;

    public String punishmentLawName;
    public String punishedPersonName;
    public Double punishmentMoney;
    public String punishedBusinessName;

    public boolean propertiesToChangeAllNull(){
        return this.punishmentName==null
                &&this.punishmentDocNo==null
                &&this.punishmentType==null
                &&this.punishedPartyName==null
                &&this.mainResponsibleName==null
                &&this.mainIllegalFact==null
                &&this.punishmentBasis==null
                &&this.punishmentDecision==null
                &&this.punisherName==null
                &&this.punishDate==null
                &&this.province==null
                &&this.status==null
                &&this.punishmentLawName==null
                &&this.punishedPersonName==null
                &&this.punishmentMoney==null;
    };

    public String connectAllCondition(){//不搜索data和status
        return this.punishmentName+
            this.punishmentDocNo+
            this.punishmentType+
            this.punishedPartyName+
            this.mainResponsibleName+
            this.mainIllegalFact+
            this.punishmentBasis+
            this.punishmentDecision+
            this.punisherName+
            this.province;
    }
}
