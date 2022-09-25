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

    public boolean propertiesForceChangeExistSpace(){
        return propertiesToInsertExistSpace();
    };

    public boolean propertiesPartChangeExistSpace(){
        return isSpaceExceptNull(this.punishmentName)
                ||isSpaceExceptNull(this.punishmentDocNo)
                ||isSpaceExceptNull(this.punishmentType)
                ||isSpaceExceptNull(this.punishedPartyName)
                ||isSpaceExceptNull(this.mainResponsibleName)
                ||isSpaceExceptNull(this.mainIllegalFact)
                ||isSpaceExceptNull(this.punishmentBasis)
                ||isSpaceExceptNull(this.punishmentDecision)
                ||isSpaceExceptNull(this.punisherName)
                ||isSpaceExceptNull(this.punishDate)
                ||isSpaceExceptNull(this.status);
    }

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
                &&this.status==null;
    };

    public boolean propertiesToInsertExistSpace(){
        return isSpace(this.punishmentName)
                ||isSpace(this.punishmentDocNo)
                ||isSpace(this.punishmentType)
                ||isSpace(this.punishedPartyName)
                ||isSpace(this.mainResponsibleName)
                ||isSpace(this.mainIllegalFact)
                ||isSpace(this.punishmentBasis)
                ||isSpace(this.punishmentDecision)
                ||isSpace(this.punisherName)
                ||isSpace(this.punishDate)
                ||isSpace(this.status);
    }

    public boolean punishmentTypeIsValid(){
        return this.punishmentType.equals("个人")||this.punishmentType.equals("企业");
    }

    public boolean statusIsValid(){
        return  this.status.equals("0")||this.status.equals("1");
    }

    public void reportInvalidProp() throws Exception{
        if(!punishmentTypeIsValid()){
            throw new Exception("punishment_type should be 个人 or 企业");
        }
        if(!statusIsValid()){
            throw new Exception("status should be 0 or 1");
        }
    }

    private boolean isSpace(String prop){
        String spaceReg = "^\\s*$";
        return prop==null||prop.matches(spaceReg);
    }

    private boolean isSpaceExceptNull(String prop){
        String spaceReg = "^\\s*$";
        return prop!=null&&prop.matches(spaceReg);
    }

//    public boolean propertiesToChangeExistNull(){
//            return propertiesToInsertExistNull();
//    };
//
//    public boolean propertiesToInsertExistNull(){
//        return this.punishmentName==null
//                ||this.punishmentDocNo==null
//                ||this.punishmentType==null
//                ||this.punishedPartyName==null
//                ||this.mainResponsibleName==null
//                ||this.mainIllegalFact==null
//                ||this.punishmentBasis==null
//                ||this.punishmentDecision==null
//                ||this.punisherName==null
//                ||this.punishDate==null
//                ||this.status==null;
//    };

    public String connectAllCondition(){//不搜索data和status
        return this.punishmentName+
                this.punishmentDocNo+
                this.punishmentType+
                this.punishedPartyName+
                this.mainResponsibleName+
                this.mainIllegalFact+
                this.punishmentBasis+
                this.punishmentDecision+
                this.punisherName;
    }
}
