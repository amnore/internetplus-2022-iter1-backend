package com.internetplus.bankpunishment.crawler;

import com.internetplus.bankpunishment.crawler.extracter.Extracter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Yunthin.Chow
 * @date 2021/12/18
 * @description
 */
@SpringBootTest
public class ExtracterStrater {

    @Autowired
    Extracter extracter;

    @Test
    public void extractData() {
        extracter.extract();
    }
}
