package com.internetplus.bankpunishment.data;

import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.po.ArticlePO;
import com.internetplus.bankpunishment.vo.BankPunishmentQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: bank-punishment
 * @description:
 * @author: xzh
 * @date: 2021-10-20
 **/
@Mapper
@Repository
public interface ArticleMapper {
    List<ArticlePO> getArticles();
}
