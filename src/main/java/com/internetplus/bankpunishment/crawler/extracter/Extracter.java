package com.internetplus.bankpunishment.crawler.extracter;

import com.internetplus.bankpunishment.crawler.pojo.CaseLibraryEntity;
import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.data.CrawlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    CrawlerMapper crawlerMapper;

    private static final String[] partyPostfixList = {"公司", "分行", "支行", "银行", "店", "合作联社", "合作社",  "服务中心", "厂", "行", "证券"};
    private static final String[] personPrefix = {"（时任", "(时任", "（时为", "(时为", "(系", "（系", "(", "（"}; // 不直接用 ( 和 （ ，方便提取机构的名字

    /**
     * 将数据从 new_bank_punishment 中取出来，提取需要的信息，存到 case_library 中
     */
    public void extract() {
        long count = crawlerMapper.getDataEntityNum();
        int currentNum = 0;
        while (currentNum <= count) {
            List<DataEntity> dataEntityList = crawlerMapper.getDataEntityListByOffsetAndLimit(100000, currentNum);
            dataEntityList.forEach(dataEntity -> {
                CaseLibraryEntity caseLibraryEntity = dataEntity2CaseLibraryEntity(dataEntity);
                crawlerMapper.addCaseLibraryEntity(caseLibraryEntity);
            });
            currentNum += 100000;
        }
    }



    /**
     * 将一阶段的数据对象转换成二阶段的案例库对象
     */
    private CaseLibraryEntity dataEntity2CaseLibraryEntity(DataEntity dataEntity) {
        CaseLibraryEntity caseLibraryEntity = new CaseLibraryEntity();
        // 设置案例库的行政处罚决定文号
        setPunishmentDocNo(caseLibraryEntity, dataEntity);
        // 设置当事人名称和机构名称和机构类型
        setPunishedParty(caseLibraryEntity, dataEntity);
        // 省份
        setProvince(caseLibraryEntity, dataEntity);
        // 违法事实
        setMainIllegalFact(caseLibraryEntity, dataEntity);
        // 法律名称、处罚依据
        setPunishmentLawName(caseLibraryEntity, dataEntity);
        // 处罚决定、金额
        setPunishmentDecision(caseLibraryEntity, dataEntity);
        // 做出处罚的机关
        setPunisherName(caseLibraryEntity, dataEntity);
        // 日期
        setPunishDate(caseLibraryEntity, dataEntity);
        return caseLibraryEntity;
    }


    /**
     * 设置案例库的行政处罚决定文号
     */
    private void setPunishmentDocNo(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punishmentDocNo == null) return;
        caseLibraryEntity.setPunishmentDocNo(dataEntity.punishmentDocNo.trim());
    }

    /**
     * 设置案例库的被罚当事人名称、当事人所在机构名称
     */
    private void setPunishedParty(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punishedPartyName == null) return;
        caseLibraryEntity.setPunishedPartyName(dataEntity.punishedPartyName.trim());
        if (dataEntity.punishedPartyName == null) return;
        String partyName = dataEntity.punishedPartyName.trim();
        String personName = null; // 当事人名称
        String partyType = "公司";
        if (partyName.length() <= 4) {
            personName = partyName;
        }
        for (String prefix : personPrefix) {
            if (partyName.contains(prefix) && partyName.indexOf(prefix) <= 5) {
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
            // 设置机构类型
            if (partyName.contains("有限公司")) partyType = "公司";
            else if (partyName.contains("银行") || partyName.contains("分行") || partyName.contains("支行")) partyType = "银行";
            else if (partyName.contains("店") || partyName.contains("经营部")) partyType = "店铺";
            else if (partyName.contains("联社")) partyType = "合作联社";
            else if (partyName.contains("厂")) partyType = "工厂";
            else if (partyName.contains("证券")) partyType = "证券";
            else if (partyName.contains("合作社")) partyType = "合作社";
            else if (partyName.contains("服务中心")) partyType = "服务中心";
        }
        caseLibraryEntity.setPunishedPersonName(personName);
        caseLibraryEntity.setPunishedPartyType(partyType);
        caseLibraryEntity.setPunishedPartyName(partyName);
    }

    /**
     * 设置当事人所在省份
     */
    private void setProvince(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (DataEntity.province == null) return;
        CaseLibraryEntity.province = DataEntity.province.trim();
    }

    /**
     * 设置违法违规事实
     */
    private void setMainIllegalFact(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.mainIllegalFact == null) return;
        caseLibraryEntity.setMainIllegalFact(dataEntity.mainIllegalFact.trim());
    }

    /**
     * 设置依据法律名称、处罚依据
     */
    private void setPunishmentLawName(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punishmentBasis == null) return;
        // 设置处罚依据
        caseLibraryEntity.setPunishmentBasis(dataEntity.punishmentBasis.trim());
        // 设置法律名称
        String lawName = dataEntity.punishmentBasis;
        if (dataEntity.punishmentBasis.contains("《")) {
            lawName = lawName.substring(lawName.indexOf("《") + 1, lawName.indexOf("》"));
        }
        caseLibraryEntity.setPunishmentLawName(lawName);
    }

    /**
     * 设置行政处罚决定、处罚金额
     */
    private void setPunishmentDecision(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punishmentDecision == null) return;
        // 设置行政处罚决定
        caseLibraryEntity.setPunishmentDecision(dataEntity.punishmentDecision.trim());
        // 设置处罚金额
        caseLibraryEntity.setPunishmentMoney(getPunishmentMoney(dataEntity.punishmentDecision.trim()));
    }

    /**
     * 获取处罚决定中的罚款金额信息
     * 返回以元为单位的金额
     */
    private double getPunishmentMoney(String punishmentInfo) {
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
    private double string2money(String moneyString) {
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

    /**
     * 设置处罚机关名称
     */
    private void setPunisherName(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punisherName == null) return;
        caseLibraryEntity.setPunisherName(dataEntity.punisherName.trim());
    }

    /**
     * 设置处罚决定的日期
     */
    private void setPunishDate(CaseLibraryEntity caseLibraryEntity, DataEntity dataEntity) {
        if (dataEntity.punishDate == null) return;
        caseLibraryEntity.setPunishDate(dataEntity.punishDate.trim());
    }


}
