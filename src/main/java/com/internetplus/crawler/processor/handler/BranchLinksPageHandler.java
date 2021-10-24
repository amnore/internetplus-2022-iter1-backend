package com.internetplus.crawler.processor.handler;

import com.internetplus.crawler.target.PageTarget;
import com.internetplus.crawler.util.CrawlerHelper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 *
 * 对分支银行链接页面进行解析
 * 把所有分支银行的链接解析出来，并对其进行访问
 *
 */
public class BranchLinksPageHandler implements ProcessHandler{
    public void process(Page page) {
        Html html = page.getHtml();
        // 获取所有分支银行的链接，并对每个进行访问
        List<String> branchPageLinks = html.xpath("//div[@class='portlet']/div[2]/table[11]/tbody/tr[2]/td/table[6]/tbody//a").links().all();
        for (String branchPageLink : branchPageLinks) {
            Request request = new Request(branchPageLink);
            request.putExtra("target", PageTarget.BRANCH_HOME);
            page.addTargetRequest(request);
        }
    }
}
