package com.internetplus.bankpunishment.crawler;

import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;
import com.internetplus.bankpunishment.crawler.pipeline.PunishmentPipeline;
import com.internetplus.bankpunishment.crawler.processor.PunishmentProcessor;
import com.internetplus.bankpunishment.crawler.target.PageTarget;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 */
public class CrawlerStarter {

    public static Spider spider = Spider.create(new PunishmentProcessor());

    /**
     * 启动爬虫，爬取所有数据
     */
    public static void startCrawler() {
        // 所有分行的请求，目标是解析所有分支银行的url
        Request branchRequest = new Request("http://www.pbc.gov.cn/rmyh/105226/105442/index.html");
        branchRequest.putExtra("target", PageTarget.BRANCH_LINKS);

        //总行的请求
        Request headquarterRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
        headquarterRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);

        // 启动
        spider.setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(1)
                .addRequest(headquarterRequest)
                .addRequest(branchRequest)
                .run(); // 同步启动
    }

    /**
     * 关闭爬虫
     */
    public static void stopCrawler() {
        spider.stop();
    }
}
