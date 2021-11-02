package com.internetplus.bankpunishment.bl.blImpl;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.vo.BankPunishmentQueryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Service
public class BankPunishmentBlImpl implements BankPunishmentBl {

    @Autowired
    BankPunishmentMapper bankPunishmentMapper;

    @Override
    public Long insertBankPunishment(BankPunishment bankPunishment) throws Exception{
        boolean propertiesExistSpace = bankPunishment.propertiesToInsertExistSpace();//经考虑，仍检查status属性
        if(propertiesExistSpace){
            throw new Exception("properties can't be space(including null and empty)");
        }
        //在controller层设置0，bl层仍检验status，使insert与update有一致性
        bankPunishment.reportInvalidProp();
        bankPunishmentMapper.insertBankPunishment(bankPunishment);//成功插入时返回1
        return bankPunishment.getId();//主键会映射到id变量里
    }

    @Override
    public Integer uploadBankPunishmentByExcel(List<List<Object>> list) throws Exception{
        int inserted_num = 0;
        for (int i = 0; i < list.size(); i++) {
            List<Object> obj = list.get(i);
            BankPunishment bankPunishment = new BankPunishment();
            /**
             * 如果有一个obj里面，size不够大，就说明这行不能插入，要continue，插入的num不加
             */
            if (obj.size() < 11) {
                continue;
            }else{
                bankPunishment.setPunishmentName(obj.get(0).toString().equals("null")?null:obj.get(0).toString());
                bankPunishment.setPunishmentDocNo(obj.get(1).toString().equals("null")?null:obj.get(1).toString());
                bankPunishment.setPunishmentType(obj.get(2).toString().equals("null")?null:obj.get(2).toString());
                bankPunishment.setPunishedPartyName(obj.get(3).toString().equals("null")?null:obj.get(3).toString());
                bankPunishment.setMainResponsibleName(obj.get(4).toString().equals("null")?null:obj.get(4).toString());
                bankPunishment.setMainIllegalFact(obj.get(5).toString().equals("null")?null:obj.get(5).toString());
                bankPunishment.setPunishmentBasis(obj.get(1).toString().equals("null")?null:obj.get(6).toString());
                bankPunishment.setPunishmentDecision(obj.get(7).toString());
                bankPunishment.setPunisherName(obj.get(8).toString());
                bankPunishment.setPunishDate(obj.get(9).toString());
                bankPunishment.setStatus(obj.get(10).toString());
                try {
                    Long res = insertBankPunishment(bankPunishment);
                    inserted_num++;
                } catch (Exception e) {
                    continue;
                }

            }
//
//            bankPunishment.setPunishmentName(obj.get(0).toString().equals("null")?null:obj.get(0).toString());
//
//            if (obj.size() <=1) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishmentDocNo(obj.get(1).toString().equals("null")?null:obj.get(1).toString());
//
//            if (obj.size() <=2) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishmentType(obj.get(2).toString().equals("null")?null:obj.get(2).toString());
//
//            if (obj.size() <=3) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishedPartyName(obj.get(3).toString().equals("null")?null:obj.get(3).toString());
//
//            if (obj.size() <=4) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setMainResponsibleName(obj.get(4).toString().equals("null")?null:obj.get(4).toString());
//
//            if (obj.size() <=5) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setMainIllegalFact(obj.get(5).toString().equals("null")?null:obj.get(5).toString());
//
//            if (obj.size() <=6) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishmentBasis(obj.get(1).toString().equals("null")?null:obj.get(6).toString());
//
//            if (obj.size() <=7) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishmentDecision(obj.get(7).toString());
//            if (obj.size() <=8) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunisherName(obj.get(8).toString());
//            if (obj.size() <=9) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setPunishDate(obj.get(9).toString());
//            if (obj.size() <=10) {
//                Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
//                continue;
//            }
//            bankPunishment.setStatus(obj.get(10).toString());
//
//            Integer res = bankPunishmentMapper.insertBankPunishment(bankPunishment);
        }
        if (list.size() != inserted_num) {
            throw new Exception("some rows has empty or null properties");
        }

        return 1;
    }


    /**
     * 添加爬虫获取的数据对象到数据库
     */
    @Override
    public Long addCrawlerBankPunishment(DataEntity dataEntity) {
        BankPunishment bankPunishment = new BankPunishment();
        BeanUtils.copyProperties(dataEntity, bankPunishment);
        bankPunishment.setStatus("1"); // 所有已经爬取的状态都是已发布
        bankPunishmentMapper.insertBankPunishment(bankPunishment);
        return bankPunishment.getId();
    }

    @Override//考虑到可能有发布后修改的需求，此处的更新不对状态做限制
    public boolean updateBankPunishment(BankPunishment bankPunishment) throws Exception{//全部字段强制覆盖
        boolean propertiesExistSpace = bankPunishment.propertiesForceChangeExistSpace();
        if(propertiesExistSpace){
            throw new Exception("properties can't be space(including null and empty)");
        }
        bankPunishment.reportInvalidProp();
        Integer changedNum = bankPunishmentMapper.updateBankPunishment(bankPunishment);
        System.out.println("changeNum: "+changedNum);
        return changedNum==1;
    }

    @Override//考虑到可能有发布后修改的需求，此处的更新不对状态做限制
    public boolean updateBankPunishmentExceptNull(BankPunishment bankPunishment) throws Exception{//某字段若为null则不更新该字段
        boolean propertiesAllNull = bankPunishment.propertiesToChangeAllNull();
        if(propertiesAllNull){//若全为空，则动态sql中的set语句为空，将报错
            throw new Exception("there should be at least one property not null to be changed");
        }
        boolean propertiesExistSpace = bankPunishment.propertiesPartChangeExistSpace();
        if(propertiesExistSpace){
            throw new Exception("properties to be changed can't be space(including null and empty)");
        }
        bankPunishment.reportInvalidProp();
        Integer changedNum = bankPunishmentMapper.updateBankPunishmentExceptNull(bankPunishment);
        System.out.println("changeNum: "+changedNum);
        return changedNum==1;
    }//    百度：谨慎使用动态sql，因为（1）使用动态SQL存在内存溢出隐患（2）代码可读性非常差

    @Override
    public boolean deleteBankPunishment(Long id) {
        Integer changedNum = bankPunishmentMapper.deleteBankPunishment(id);
        System.out.println("changeNum: "+changedNum);
        return changedNum==1;
    }

    @Override
    public boolean publishBankPunishment(Long id) {
        Integer changedNum = bankPunishmentMapper.publishBankPunishment(id);
        //考虑发布操作可能频繁，专门写一个方法，而不是调用updateBankPunishmentExceptNull（其实好像差不多）
        System.out.println("changeNum: "+changedNum);
        return changedNum==1;
    }

    @Override
    public int countAll() {
        return bankPunishmentMapper.countAll();
    }

    @Override
    public BankPunishment selectBankPunishmentById(Long id) {
        return bankPunishmentMapper.selectBankPunishmentById(id);
    }

    @Override
    public List<BankPunishment> selectBankPunishmentByFuzzyQuery(String queryString) {
        return bankPunishmentMapper.selectBankPunishmentByFuzzyQuery(queryString);
    }

    @Override
    public List<BankPunishment> selectBankPunishment(BankPunishmentQueryVO query){
        return bankPunishmentMapper.selectBankPunishment(query);//若全字段为null，则动态sql将返回所有记录
    }//    百度：谨慎使用动态sql，因为（1）使用动态SQL存在内存溢出隐患（2）代码可读性非常差


    /**
     * 对获取的数据进行清洗
     *
     * 每次获取500条数据
     *      清洗规则：年份在 18xx 年的必定为假
     *                              将违法行为类型改成个人或单位
     */
    public boolean filterDirtyBankPunishment() {
        long dataCount = bankPunishmentMapper.getBankPunishmentCount();
        long offsetNum = 0;
        final int limitNum = 500;
        while (offsetNum < dataCount) {
            List<BankPunishment> list = bankPunishmentMapper.selectBankPunishmentByLimitAndOffset(limitNum, offsetNum);
            offsetNum += limitNum;
            System.out.println(offsetNum);
            if (list == null) continue;
            for (BankPunishment bankPunishment : list) {
                if (bankPunishment == null) continue;
                // 去除掉年份为 18xx 的脏数据
                if (bankPunishment.getPunishDate().trim().startsWith("18")) {
                    System.out.println("删除脏数据：" + bankPunishment);
                    bankPunishmentMapper.deleteBankPunishment(bankPunishment.getId());
                } else {
                    // 已经清理过的数据不用再更新
                    if (bankPunishment.getPunishmentType() != null &&
                            (bankPunishment.getPunishmentType().equals("个人") || bankPunishment.getPunishmentType().equals("企业"))) {
                        continue;
                    }
                    // 将违法行为类型改成个人或单位
                    if (bankPunishment.getPunishedPartyName() == null) {
                        // 如果 punishedPartyName 为空，则将其违法类型置为空
                        bankPunishment.setPunishmentType("");
                    } else {
                        String partyName = bankPunishment.getPunishedPartyName().trim();
                        if (partyName.contains("(")) {
                            partyName = partyName.substring(0, partyName.indexOf("("));
                        } else if (partyName.contains("（")) {
                            partyName = partyName.substring(0, partyName.indexOf("（"));
                        }
                        if (partyName.length() == 2 || partyName.length() == 3) {
                            bankPunishment.setPunishmentType("个人");
                        } else {
                            bankPunishment.setPunishmentType("企业");
                        }
                    }

                    // 将信息更新到数据库
                    bankPunishmentMapper.updateBankPunishment(bankPunishment);
                }
            }
        }
        return true;
    }

}
