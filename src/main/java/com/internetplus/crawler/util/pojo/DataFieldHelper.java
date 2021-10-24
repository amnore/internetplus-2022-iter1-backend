package com.internetplus.crawler.util.pojo;

import com.internetplus.crawler.pojo.DataEntity;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/21
 * 数字字段辅助类
 * 不同的分行对于术语所采用的表达不一样
 */
public class DataFieldHelper {
    private static final String[] number = new String[]{"序号"};
    private static final String[] punishmentName = new String[]{"行政处罚名称"};
    private static final String[] punishmentDocNo = new String[]{"行政处罚决定文号", "行政处罚决定书文号", "处罚决定书文号", "行政处罚决定书号"};
    private static final String[] punishmentType = new String[]{"处罚类型", "违法行为类型"};
    private static final String[] punishedPartyName = new String[]{"被罚当事人名称", "当事人名称", "企业名称", "当事人名称(姓名)", "企业名称(姓名)"};
    private static final String[] mainResponsibleName = new String[]{"主要负责人姓名"};
    private static final String[] mainIllegalFact = new String[]{"主要违法违规事实(案由)"};
    private static final String[] punishmentBasis = new String[]{"行政处罚依据"};
    private static final String[] punishmentDecision = new String[]{"行政处罚决定", "行政处罚内容"};
    private static final String[] punisherName = new String[]{"行政处罚的机关名称", "作出行政处罚决定机关名称", "处罚作出机关"};
    private static final String[] punishDate = new String[]{"作出处罚决定的日期", "作出行政处罚决定日期"};
    private static final String[] remark = new String[]{"备注"};



    /**
     * 判断一个字符串属于哪一个数据字段
     */
    public static String typeJudge(String fieldName) {
        if (isNumber(fieldName)) return number[0];
        if (isPunishmentName(fieldName)) return punishmentName[0];
        if (isPunishmentDocNo(fieldName)) return punishmentDocNo[0];
        if (isPunishmentType(fieldName)) return punishmentType[0];
        if (isPunishedPartyName(fieldName)) return punishedPartyName[0];
        if (isMainResponsibleName(fieldName)) return mainResponsibleName[0];
        if (isMainIllegalFact(fieldName)) return mainIllegalFact[0];
        if (isPunishmentBasis(fieldName)) return punishmentBasis[0];
        if (isPunishmentDecision(fieldName)) return punishmentDecision[0];
        if (isPunisherName(fieldName)) return punisherName[0];
        if (isPunishDate(fieldName)) return punishDate[0];
        if (isRemark(fieldName)) return remark[0];
        return null;
    }

    /**
     * 按照该 Excel 表格中出现的字段顺序，对数据对象进行赋值
     */
    public static DataEntity setRegardingValue(String fieldName, String value, DataEntity dataEntity) {
        if (isPunishmentName(fieldName)) dataEntity.setPunishmentName(value);
        else if (isPunishmentDocNo(fieldName)) dataEntity.setPunishmentDocNo(value);
        else if (isPunishmentType(fieldName)) dataEntity.setPunishmentType(value);
        else if (isPunishedPartyName(fieldName)) dataEntity.setPunishedPartyName(value);
        else if (isMainResponsibleName(fieldName)) dataEntity.setMainResponsibleName(value);
        else if (isMainIllegalFact(fieldName)) dataEntity.setMainIllegalFact(value);
        else if (isPunishmentBasis(fieldName)) dataEntity.setPunishmentBasis(value);
        else if (isPunishmentDecision(fieldName)) dataEntity.setPunishmentDecision(value);
        else if (isPunisherName(fieldName)) dataEntity.setPunisherName(value);
        else if (isPunishDate(fieldName)) dataEntity.setPunishDate(value);
        return dataEntity;
    }


    /**
     * 判断某个字段名是不是 序号
     */
    private static boolean isNumber(String fieldName) {
        return Arrays.stream(number).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishmentName(String fieldName) {
        return Arrays.stream(punishmentName).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishmentDocNo(String fieldName) {
        return Arrays.stream(punishmentDocNo).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishmentType(String fieldName) {
        return Arrays.stream(punishmentType).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishedPartyName(String fieldName) {
        return Arrays.stream(punishedPartyName).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isMainResponsibleName(String fieldName) {
        return Arrays.stream(mainResponsibleName).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isMainIllegalFact(String fieldName) {
        return Arrays.stream(mainIllegalFact).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishmentBasis(String fieldName) {
        return Arrays.stream(punishmentBasis).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunishmentDecision(String fieldName) {
        return Arrays.stream(punishmentDecision).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isPunisherName(String fieldName) {
        return Arrays.stream(punisherName).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    public static boolean isPunishDate(String fieldName) {
        return Arrays.stream(punishDate).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    private static boolean isRemark(String fieldName) {
        return Arrays.stream(remark).anyMatch((name) -> name.equals(removeBlank(fieldName)));
    }

    /**
     * 去除所有空白符
     * 去除括号和括号中的内容
     */
    private static String removeBlank(String s) {
        Pattern p = Pattern.compile("\t|\r|\n");
        Matcher m = p.matcher(s);
        s = m.replaceAll("").replaceAll(" +", "");
        if (s.contains("(")) return s.substring(0, s.indexOf("("));
        else if (s.contains("（")) return s.substring(0, s.indexOf("（"));
        return s;
    }

    /**
     * 检查是否所有字段都为空 (日期不做判断）
     */
    public static boolean isAllFieldNull (DataEntity dataEntity) {
        return (
                (dataEntity.getPunishmentName() == null || dataEntity.getPunishmentName().isEmpty()) &&
                        (dataEntity.getPunishmentDocNo() == null || dataEntity.getPunishmentDocNo().isEmpty()) &&
                        (dataEntity.getPunishmentType() == null || dataEntity.getPunishmentType().isEmpty()) &&
                        (dataEntity.getPunishedPartyName() == null || dataEntity.getPunishedPartyName().isEmpty()) &&
                        (dataEntity.getMainResponsibleName() == null || dataEntity.getMainResponsibleName().isEmpty()) &&
                        (dataEntity.getMainIllegalFact() == null || dataEntity.getMainIllegalFact().isEmpty()) &&
                        (dataEntity.getPunishmentBasis() == null || dataEntity.getPunishmentBasis().isEmpty()) &&
                        (dataEntity.getPunishmentDecision() == null || dataEntity.getPunishmentDecision().isEmpty()) &&
                        (dataEntity.getPunisherName() == null || dataEntity.getPunisherName().isEmpty())
        );
    }

}
