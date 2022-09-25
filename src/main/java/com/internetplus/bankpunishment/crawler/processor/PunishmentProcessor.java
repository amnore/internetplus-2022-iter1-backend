package com.internetplus.bankpunishment.crawler.processor;

import com.internetplus.bankpunishment.crawler.processor.handler.BranchHomePageHandler;
import com.internetplus.bankpunishment.crawler.processor.handler.BranchLinksPageHandler;
import com.internetplus.bankpunishment.crawler.processor.handler.BranchPunishLinksPageHandler;
import com.internetplus.bankpunishment.crawler.processor.handler.ProcessHandler;
import com.internetplus.bankpunishment.crawler.processor.handler.PunishmentListPageHandler;
import com.internetplus.bankpunishment.crawler.processor.handler.PunishmentDetailPageHandler;
import com.internetplus.bankpunishment.crawler.target.PageTarget;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 * 处罚案例库的爬虫处理器
 */
@Slf4j
public class PunishmentProcessor implements PageProcessor {
    static final Logger logger = LoggerFactory.getLogger(PunishmentProcessor.class);

    @Override
    public void process(Page page) {
        logger.info("processing page {}", page.getUrl());

        // 获取页面的目标
        String target = page.getRequest().getExtra("target").toString();
        switch (target) {
            case PageTarget.BRANCH_LINKS:
                this.processWithHandler(page, BranchLinksPageHandler.class);
                break;

            case PageTarget.BRANCH_HOME:
                this.processWithHandler(page, BranchHomePageHandler.class);
                break;

            case PageTarget.BRANCH_PUNISH_LINKS:
                this.processWithHandler(page, BranchPunishLinksPageHandler.class);
                break;

            case PageTarget.PUNISHMENT_LIST_PAGE:
                this.processWithHandler(page, PunishmentListPageHandler.class);
                break;

            case PageTarget.PUNISHMENT_DETAIL_PAGE:
            default:
                this.processWithHandler(page, PunishmentDetailPageHandler.class);
        }

    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    /**
     * 调用相应的页面处理器进行处理
     */
    private void processWithHandler(Page page, Class c) {
        try {
            ProcessHandler processHandler = (ProcessHandler) c.newInstance();
            processHandler.process(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
