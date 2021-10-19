package com.internetplus.bankpunishment.data;
import com.internetplus.bankpunishment.po.TestPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @program: bank-punishment
 * @description: test
 * @author: xzh
 * @date: 2021-10-19
 **/
@Mapper
@Repository
public interface TestDataMapper {
    /**
     * 查询Test
     */
    TestPO retrieveTest(@Param("Id") Integer Id);

}
