package com.internetplus.bankpunishment.crawler;

import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;
import com.internetplus.bankpunishment.crawler.pipeline.PunishmentPipeline;
import com.internetplus.bankpunishment.crawler.processor.PunishmentProcessor;
import com.internetplus.bankpunishment.crawler.target.PageTarget;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 */
@SpringBootTest
public class CrawlerStarter {

    /**
     * 启动爬虫进行数据的爬取
     * (需要安装 chromeDriver 驱动，然后在 application.yml 里面配置驱动的路径)
     */
    @Test
    void startCrawler() {
        // 所有分行的请求，目标是解析所有分支银行的url
        Request branchRequest = new Request("http://www.pbc.gov.cn/rmyh/105226/105442/index.html");
        branchRequest.putExtra("target", PageTarget.BRANCH_LINKS);

        //总行的请求
        Request headquarterRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
        headquarterRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);

        // 启动
        Spider.create(new PunishmentProcessor()).setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(1)
                .addRequest(headquarterRequest)
                .addRequest(branchRequest)
                .run(); // 同步启动

        System.out.println("总行数据爬取成功");
    }

    /**
     * 单独地对页面进行爬取
     */
    @Test
    void startCrawlerSingly() {
        // 某分支银行的请求
        Request branchRequest = new Request("http://shanghai.pbc.gov.cn/");
        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);

        //总行的请求
//        Request branchRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
//        branchRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);

        // 启动
        Spider.create(new PunishmentProcessor()).setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(1)
                .addRequest(branchRequest)
                .run(); // 同步启动

//        System.out.println("总行数据爬取成功");
        System.out.println("上海分行数据爬取成功");
    }

}
