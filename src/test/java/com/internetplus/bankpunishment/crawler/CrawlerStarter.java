//package com.internetplus.bankpunishment.crawler;
//
//import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;
//import com.internetplus.bankpunishment.crawler.pipeline.PunishmentPipeline;
//import com.internetplus.bankpunishment.crawler.processor.PunishmentProcessor;
//import com.internetplus.bankpunishment.crawler.target.PageTarget;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import us.codecraft.webmagic.Request;
//import us.codecraft.webmagic.Spider;
//import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
//import us.codecraft.webmagic.scheduler.QueueScheduler;
//
///**
// * @author Yunthin.Chow
// * @version 1.0
// * Created by Yunthin.Chow on 2021/10/20
// */
//@SpringBootTest
//public class CrawlerStarter {
//
////    /**
////     * 启动爬虫进行数据的爬取
////     * (需要安装 chromeDriver 驱动，然后在 application.yml 里面配置驱动的路径)
////     */
////    @Test
////    void startCrawler() {
////        // 所有分行的请求，目标是解析所有分支银行的url
////        Request branchRequest = new Request("http://www.pbc.gov.cn/rmyh/105226/105442/index.html");
////        branchRequest.putExtra("target", PageTarget.BRANCH_LINKS);
////
////        //总行的请求
////        Request headquarterRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
////        headquarterRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);
////
////        // 启动
////        Spider.create(new PunishmentProcessor()).setDownloader(new PunishmentDownloader())
////                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
////                .addPipeline(new PunishmentPipeline())
////                .thread(1)
////                .addRequest(headquarterRequest)
////                .addRequest(branchRequest)
////                .run(); // 同步启动
////
////        System.out.println("总行数据爬取成功");
////    }
//
//    /**
//     * 单独地对页面进行爬取
//     */
//    @Test
//    void startCrawlerSingly() {
//
//        //总行的请求
////        Request branchRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
////        branchRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);
//
//        // 厦门分支的请求
////        Request branchRequest = new Request("http://xiamen.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 武汉分支的请求
////        Request branchRequest = new Request("http://wuhan.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 重庆分支的请求
////        Request branchRequest = new Request("http://chongqing.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 哈尔滨分支的请求
////        Request branchRequest = new Request("http://haerbin.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 长沙分支的请求
////        Request branchRequest = new Request("http://changsha.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 贵阳分支的请求
////        Request branchRequest = new Request("http://guiyang.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 乌鲁木齐分支的请求
////        Request branchRequest = new Request("http://wulumuqi.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 石家庄分支的请求
////        Request branchRequest = new Request("http://shijiazhuang.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 杭州分支的请求
////        Request branchRequest = new Request("http://hangzhou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 兰州分支的请求
////        Request branchRequest = new Request("http://lanzhou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 天津分支的请求
////        Request branchRequest = new Request("http://tianjin.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 广州分支的请求
////        Request branchRequest = new Request("http://guangzhou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 南昌分支的请求
////        Request branchRequest = new Request("http://nanchang.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 拉萨分支的请求
////        Request branchRequest = new Request("http://lasa.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 深圳分支的请求
////        Request branchRequest = new Request("http://shenzhen.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 沈阳分支的请求
////        Request branchRequest = new Request("http://shenyang.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 成都分支的请求
////        Request branchRequest = new Request("http://chengdu.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 太原分支的请求
////        Request branchRequest = new Request("http://taiyuan.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 福州分支的请求
////        Request branchRequest = new Request("http://fuzhou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 南宁分支的请求
////        Request branchRequest = new Request("http://nanning.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 大连分支的请求
////        Request branchRequest = new Request("http://dalian.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 南京分支的请求
////        Request branchRequest = new Request("http://nanjing.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 呼和浩特分支的请求
////        Request branchRequest = new Request("http://huhehaote.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 西安分支的请求
////        Request branchRequest = new Request("http://xian.pbc.gov.cn//");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 合肥分支的请求
////        Request branchRequest = new Request("http://hefei.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 海口分支的请求
////        Request branchRequest = new Request("http://haikou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 西宁分支的请求
////        Request branchRequest = new Request("http://xining.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 青岛分支的请求
////        Request branchRequest = new Request("http://qingdao.pbc.gov.cn//");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 济南分支的请求
////        Request branchRequest = new Request("http://jinan.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 北京分支的请求
////        Request branchRequest = new Request("http://beijing.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 长春分支的请求
////        Request branchRequest = new Request("http://changchun.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 郑州分支的请求
////        Request branchRequest = new Request("http://zhengzhou.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 昆明分支的请求
////        Request branchRequest = new Request("http://kunming.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 银川分支的请求
////        Request branchRequest = new Request("http://yinchuan.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 宁波分支的请求
////        Request branchRequest = new Request("http://ningbo.pbc.gov.cn/");
////        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 上海分支的请求
//        Request branchRequest = new Request("http://shanghai.pbc.gov.cn/");
//        branchRequest.putExtra("target", PageTarget.BRANCH_HOME);
//
//        // 启动
//        Spider.create(new PunishmentProcessor()).setDownloader(new PunishmentDownloader())
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
//                .addPipeline(new PunishmentPipeline())
//                .thread(1)
//                .addRequest(branchRequest)
//                .run(); // 同步启动
//
////        System.out.println("总行数据爬取成功"); // 67
////        System.out.println("厦门分行数据爬取成功"); //  72
////        System.out.println("武汉分行数据爬取成功"); // 449
////        System.out.println("重庆分行数据爬取成功"); // 845
////        System.out.println("哈尔滨分行数据爬取成功"); // 1058
////        System.out.println("长沙分行数据爬取成功"); // 1444
////        System.out.println("贵阳分行数据爬取成功"); // 1588
////        System.out.println("乌鲁木齐分行数据爬取成功"); // 1907
////        System.out.println("石家庄分行数据爬取成功"); // 2026
////        System.out.println("杭州分行数据爬取成功"); // 2195
////        System.out.println("兰州分行数据爬取成功"); // 2324
////        System.out.println("天津分行数据爬取成功"); // 4299
////        System.out.println("广州分行数据爬取成功"); // 7255
////        System.out.println("南昌分行数据爬取成功"); // 7487
////        System.out.println("拉萨分行数据爬取成功"); // 7508
////        System.out.println("深圳分行数据爬取成功"); // 40638 52615
////        System.out.println("沈阳分行数据爬取成功"); // 44666 56643
////        System.out.println("成都分行数据爬取成功"); // 45264 57241
////        System.out.println("太原分行数据爬取成功"); // 45416 57393
////        System.out.println("福州分行数据爬取成功"); // 46154 58131
////        System.out.println("南宁分行数据爬取成功"); // 46565 58542
////        System.out.println("大连分行数据爬取成功"); // 48424 60401
////        System.out.println("南京分行数据爬取成功"); // 48507 60484
////        System.out.println("呼和浩特分行数据爬取成功"); // 48845 60822
////        System.out.println("西安分行数据爬取成功"); // 49141 61160
////        System.out.println("合肥分行数据爬取成功"); // 49710 61729
////        System.out.println("海口分行数据爬取成功"); // 49752 61771
////        System.out.println("西宁分行数据爬取成功"); // 49786 61805
////        System.out.println("青岛分行数据爬取成功"); // 49934 61957
////        System.out.println("济南分行数据爬取成功"); // 51425 63448
////        System.out.println("北京分行数据爬取成功"); // 51728 63757
////        System.out.println("长春分行数据爬取成功"); // 51955 64225
////        System.out.println("郑州分行数据爬取成功"); // 52257 64527
////        System.out.println("昆明分行数据爬取成功"); // 52900 65232
////        System.out.println("银川分行数据爬取成功"); // 52928 65260
////        System.out.println("宁波分行数据爬取成功"); // 53228 65560
//        System.out.println("上海分行数据爬取成功");
//    }
//
//}
