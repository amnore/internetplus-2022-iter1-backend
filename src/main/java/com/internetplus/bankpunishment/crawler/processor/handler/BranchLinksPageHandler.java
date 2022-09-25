package com.internetplus.bankpunishment.crawler.processor.handler;

import com.internetplus.bankpunishment.crawler.target.PageTarget;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yunthin.Chow
 * @version 1.0 Created by Yunthin.Chow on 2021/10/20
 *
 *          对分支银行链接页面进行解析 把所有分支银行的链接解析出来，并对其进行访问
 *
 */
public class BranchLinksPageHandler implements ProcessHandler {
    static final Logger logger = LoggerFactory.getLogger(BranchLinksPageHandler.class);

    public void process(Page page) {
        Html html = page.getHtml();
        // 获取所有分支银行的链接，并对每个进行访问
        List<String> branchPageLinks = html.links().all().stream().filter(
                l -> l.matches("http(?:s?)://\\w+\\.pbc\\.gov\\.cn/") && !l.equals("http://camlmac.pbc.gov.cn/"))
                .distinct().collect(Collectors.toList());
        logger.info("got links: {}", branchPageLinks);
        for (String branchPageLink : branchPageLinks) {
            Request request = new Request(branchPageLink);
            request.putExtra("target", PageTarget.BRANCH_HOME);
            page.addTargetRequest(request);
        }
    }
}
