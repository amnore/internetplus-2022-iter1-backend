package com.internetplus.crawler;

import com.internetplus.crawler.downloader.PunishmentDownloader;
import com.internetplus.crawler.pipeline.PunishmentPipeline;
import com.internetplus.crawler.processor.PunishmentProcessor;
import com.internetplus.crawler.target.PageTarget;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 */
public class Starter {

    public static void main(String[] args) {
        // 所有分行的请求，目标是解析所有分支银行的url

        Request request1 = new Request("http://beijing.pbc.gov.cn/beijing/132030/132052/132059/index.html"); // 嵌套 html
//        Request request1 = new Request("http://wulumuqi.pbc.gov.cn/wulumuqi/121755/121777/121784/index.html");
        request1.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);

        //总行的请求
        Request headquarterRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
        headquarterRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);

        Spider.create(new PunishmentProcessor())
                .setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(1)
                .addRequest(headquarterRequest)
//                .addRequest(request1)
                .run();
    }
}
