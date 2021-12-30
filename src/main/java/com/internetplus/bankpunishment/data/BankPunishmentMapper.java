package com.internetplus.bankpunishment.data;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.vo.BankPunishmentQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Mapper
@Repository
public interface BankPunishmentMapper {
    //成功插入时返回1，不是返回主键；主键映射到类的id变量里了
    Integer insertBankPunishment(@Param("bankPunishment") BankPunishment bankPunishment);

    Integer updateBankPunishment(@Param("bankPunishment") BankPunishment bankPunishment);

    Integer updateBankPunishmentExceptNull(@Param("bankPunishment") BankPunishment bankPunishment);

    Integer publishBankPunishment(@Param("id") long id);

    Integer deleteBankPunishment(@Param("id") long id);

    int countAll();

    BankPunishment selectBankPunishmentById(@Param("id") long id);

    List<BankPunishment> selectBankPunishment(@Param("query") BankPunishmentQueryVO query);

    //全字段模糊搜索
    List<BankPunishment> selectBankPunishmentByFuzzyQuery(@Param("queryString") String queryString);

    List<BankPunishment> selectBankPunishmentByLimitAndOffset(@Param("limitNum") int limitNum, @Param("offsetNum") long offsetNum);

    Long getBankPunishmentCount();




    Long addCrawlerBankPunishment(DataEntity dataEntity);
}
