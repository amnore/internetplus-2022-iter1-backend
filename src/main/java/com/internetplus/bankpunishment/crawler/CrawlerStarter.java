package com.internetplus.bankpunishment.crawler;

import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;
import com.internetplus.bankpunishment.crawler.pipeline.PunishmentPipeline;
import com.internetplus.bankpunishment.crawler.processor.PunishmentProcessor;
import com.internetplus.bankpunishment.crawler.target.PageTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    static final Logger logger = LoggerFactory.getLogger(CrawlerStarter.class);

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

        Request testRequest;
        // testRequest = new Request("http://huhehaote.pbc.gov.cn/huhehaote/129797/129815/129822/4435990/index.html");
        // testRequest = new Request("http://nanchang.pbc.gov.cn/nanchang/132372/132390/132397/4600783/index.html");
        // testRequest = new Request("http://beijing.pbc.gov.cn/beijing/132030/132052/132059/4106573/index.html");
        // testRequest = new Request("http://kunming.pbc.gov.cn/kunming/133736/133760/133767/4410340/index.html");
        // testRequest = new Request("http://kunming.pbc.gov.cn/kunming/133736/133760/133767/4182184/index.html");
        // testRequest = new Request("http://dalian.pbc.gov.cn/dalian/123812/123830/123837/3453713/index.html");
        testRequest = new Request("http://wulumuqi.pbc.gov.cn/wulumuqi/121755/121777/121784/4276742/index.html");
        testRequest.putExtra("target", PageTarget.PUNISHMENT_DETAIL_PAGE);

        int nthreads = Runtime.getRuntime().availableProcessors() * 8;
        logger.info("using {} threads", nthreads);

        // 启动
        spider.setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(nthreads)
                // .addRequest(testRequest)
                .addRequest(headquarterRequest, branchRequest)
                .start(); // 同步启动
    }

    /**
     * 关闭爬虫
     */
    public static void stopCrawler() {
        spider.stop();
    }
}
