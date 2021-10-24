package com.internetplus.bankpunishment.bl.blImpl;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Long insertBankPunishment(BankPunishment bankPunishment) {
        bankPunishment.setStatus("0");//发布状态由系统录入，即新建时一律尚未发布
        bankPunishmentMapper.insertBankPunishment(bankPunishment);//成功插入时返回1
        return bankPunishment.getId();//主键会映射到id变量里
    }

    @Override//考虑到可能有发布后修改的需求，此处的更新不对状态做限制
    public boolean updateBankPunishment(BankPunishment bankPunishment) {//全部字段强制覆盖
        Integer changedNum = bankPunishmentMapper.updateBankPunishment(bankPunishment);
        System.out.println("changeNum: "+changedNum);
        return changedNum==1;
    }

    @Override//考虑到可能有发布后修改的需求，此处的更新不对状态做限制
    public boolean updateBankPunishmentExceptNull(BankPunishment bankPunishment) {//某字段若为null则不更新该字段
        Integer changedNum = 0;
        boolean propertiesAllNull = bankPunishment.getPunisherName()==null
                &&bankPunishment.getPunishmentDocNo()==null
                &&bankPunishment.getPunishmentType()==null
                &&bankPunishment.getPunishedPartyName()==null
                &&bankPunishment.getMainResponsibleName()==null
                &&bankPunishment.getMainIllegalFact()==null
                &&bankPunishment.getPunishmentBasis()==null
                &&bankPunishment.getPunishmentDecision()==null
                &&bankPunishment.getPunisherName()==null
                &&bankPunishment.getPunishDate()==null
                &&bankPunishment.getStatus()==null;
        if(!propertiesAllNull){//若全为空，则动态sql中的set语句为空，将报错
            changedNum = bankPunishmentMapper.updateBankPunishmentExceptNull(bankPunishment);
        }
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
    public BankPunishment selectBankPunishmentById(Long id) {
        return bankPunishmentMapper.selectBankPunishmentById(id);
    }

    @Override
    public List<BankPunishment> selectBankPunishment(BankPunishment bankPunishment){
        return bankPunishmentMapper.selectBankPunishment(bankPunishment);//若全字段为null，则动态sql将返回所有记录
    }//    百度：谨慎使用动态sql，因为（1）使用动态SQL存在内存溢出隐患（2）代码可读性非常差



}
