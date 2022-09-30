package com.internetplus.bankpunishment.crawler.extracter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;

/**
 * @author Yunthin.Chow
 * @date 2021/12/18
 * @description 从爬取出来的数据中提取出要求中的字段
 * 可以直接从旧数据库直接移植的：
 *      行政处罚决定文号
 *
 */
@Component
public class Extracter {

    private static final String[] partyPostfixList = {"公司", "分行", "支行", "银行", "店", "合作联社", "合作社",  "服务中心", "厂", "行", "证券"};
    private static final String[] personPrefix = {"（时任", "(时任", "（时为", "(时为", "(系", "（系", "(", "（"}; // 不直接用 ( 和 （ ，方便提取机构的名字

    /**
     * 将一阶段的数据对象转换成二阶段的案例库对象
     */
    public static BankPunishment dataEntity2CaseLibraryEntity(BankPunishment dataEntity) {
        // 设置当事人名称和机构名称和机构类型
        setPunishedParty(dataEntity);
        // 法律名称、处罚依据
        setPunishmentLawName(dataEntity);
        // 处罚决定、金额
        setPunishmentDecision(dataEntity);
        return dataEntity;
    }

    /**
     * 设置案例库的被罚当事人名称、当事人所在机构名称
     */
    private static void setPunishedParty(BankPunishment dataEntity) {
        if (dataEntity.punishedPartyName == null) return;
        String partyName = dataEntity.punishedPartyName.trim();
        String personName = null; // 当事人名称
        String partyType = "企业";
        if (partyName.length() <= 4) {
            personName = partyName;
        }
        for (String prefix : personPrefix) {
            if (partyName.contains(prefix) && partyName.indexOf(prefix) <= 5) {
                partyType = "个人";
                // 如果是”时任xx类型的“
                int prefixIndex = partyName.indexOf(prefix);
                // 设置当事人名称
                personName = partyName.substring(0, prefixIndex);
                // 设置机构名称
                for (String postfix : partyPostfixList) {
                    if (partyName.contains(postfix)) {
                        int postfixIndex = partyName.indexOf(postfix) + postfix.length();
                        partyName = partyName.substring(prefixIndex + prefix.length(), postfixIndex);
                        break;
                    }
                }
            }
        }
        dataEntity.setPunishedPersonName(personName);
        dataEntity.setPunishmentType(partyType);
        dataEntity.setPunishedBusinessName(partyName);
    }

    /**
     * 设置依据法律名称、处罚依据
     */
    private static void setPunishmentLawName(BankPunishment dataEntity) {
        if (dataEntity.punishmentBasis == null) return;
        // 设置法律名称
        String lawName = dataEntity.punishmentBasis;
        if (dataEntity.punishmentBasis.contains("《")) {
            lawName = lawName.substring(lawName.indexOf("《") + 1, lawName.indexOf("》"));
        }
        dataEntity.setPunishmentLawName(lawName);
    }

    /**
     * 设置行政处罚决定、处罚金额
     */
    private static void setPunishmentDecision(BankPunishment dataEntity) {
        if (dataEntity.punishmentDecision == null) return;
        // 设置处罚金额
        dataEntity.setPunishmentMoney(getPunishmentMoney(dataEntity.punishmentDecision.trim()));
    }

    /**
     * 获取处罚决定中的罚款金额信息
     * 返回以元为单位的金额
     */
    private static double getPunishmentMoney(String punishmentInfo) {
        punishmentInfo = punishmentInfo.replaceAll(",", "").replaceAll("，", "").replaceAll(" ", "");
        Pattern pattern = Pattern.compile("[0-9]+\\.?[0-9]*.?元");
        Matcher matcher = pattern.matcher(punishmentInfo);
        double sumMoney = 0;
        if (!matcher.find()) {
            // 有两种格式的
            pattern = Pattern.compile("罚款[0-9]+\\.?[0-9]*");
            matcher = pattern.matcher(punishmentInfo);
        } else {
            matcher = pattern.matcher(punishmentInfo);
        }
        while (matcher.find()) {
            sumMoney += string2money(matcher.group());
        }
        return sumMoney;
    }

    /**
     * 将字符串人民币转换成金额
     */
    private static double string2money(String moneyString) {
        if (moneyString == null) return 0;
        double moneyNumber = 0;
        moneyString = moneyString.trim();
        moneyString = moneyString.replaceAll(" ", "");
        Matcher matcher = Pattern.compile("\\d+\\.?\\d*").matcher(moneyString);
        if (matcher.find()) {
            String moneyNumberStr = matcher.group();
            moneyNumber = Double.parseDouble(moneyNumberStr);
        }
        if (moneyString.contains("千万")) moneyNumber *= 10000000;
        else if (moneyString.contains("万")) moneyNumber *= 10000;
        else if (moneyString.contains("千")) moneyNumber *= 1000;
        else if (moneyString.contains("百")) moneyNumber *= 100;
        return moneyNumber;
    }
}
