package com.internetplus.bankpunishment.bl.blImpl;

import com.internetplus.bankpunishment.bl.TestBl;
import com.internetplus.bankpunishment.data.TestDataMapper;
import com.internetplus.bankpunishment.po.TestPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @program: bank-punishment
 * @description: test
 * @author: xzh
 * @date: 2021-10-19
 **/
@Service
public class TestBlImpl implements TestBl {
    @Autowired
    TestDataMapper testDataMapper;
    @Override
    public TestPO retriveTest(Integer Id) {
        return testDataMapper.retrieveTest(Id);
    }
}
