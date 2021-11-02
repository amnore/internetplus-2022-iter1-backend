package com.internetplus.bankpunishment.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.internetplus.bankpunishment.entity.BankPunishment;
import lombok.Data;

/**
 * @description: 前端向后端传送query参数的类，包含所有BankPunishment参数和模糊搜索参数及页尺寸、页码
 * @author: gzx
 * @date: 2021-10-27
 **/
@Data
public class BankPunishmentQueryVO {

    /**
     * 模糊搜索
     */
    private String queryString;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNO;

    @JSONField(serializeUsing = ToStringSerializer.class)//使用fastjson自动转化long和string
    private Long id;

//    private String stringId;//前后端传输long有精度损失，故用string传，也可以用fastjson

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

    public boolean conditionAllNull(){
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
                &&this.status==null;
    }
}
