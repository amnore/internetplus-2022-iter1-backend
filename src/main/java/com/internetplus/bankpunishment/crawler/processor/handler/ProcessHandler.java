package com.internetplus.bankpunishment.crawler.processor.handler;

import us.codecraft.webmagic.Page;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 */
public interface ProcessHandler {

    /**
     * 对 Page 进行解析
     */
    void process(Page page);
}
