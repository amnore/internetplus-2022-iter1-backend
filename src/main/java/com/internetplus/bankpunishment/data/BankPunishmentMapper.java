package com.internetplus.bankpunishment.data;

import com.internetplus.bankpunishment.entity.BankPunishment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Mapper
@Repository
public interface BankPunishmentMapper {
    Integer insertBankPunishment(@Param("bankPunishment") BankPunishment bankPunishment);
}
