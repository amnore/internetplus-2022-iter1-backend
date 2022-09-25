package com.internetplus.bankpunishment.crawler.processor.handler;

import com.internetplus.bankpunishment.crawler.target.PageTarget;
import com.internetplus.bankpunishment.crawler.util.CrawlerHelper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 *
 * 行政处罚公示的列表页面 http://shanghai.pbc.gov.cn/fzhshanghai/113577/114832/114918/index.html
 * 需要将每个公开条目的链接获取出来
 */
public class PunishmentListPageHandler implements ProcessHandler{
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        // 获取到每一个行政处罚的具体页面 url
        List<String> punishmentLinks = html.xpath("//table/tbody/tr/td/font/a").links().all();
        if (punishmentLinks == null || punishmentLinks.size() == 0) {
            // 说明是总行
            punishmentLinks = html.xpath("//div/ul[@class='txtlist']/li/a").links().all();
        }
        for (String punishmentLink : punishmentLinks) {
            // 把每一个详情页都添加到访问队列中
            Request request = new Request(punishmentLink);
            request.putExtra("target", PageTarget.PUNISHMENT_DETAIL_PAGE);
            page.addTargetRequest(request);
        }

        // 同时，还需要将“下一页”的链接添加到访问队列中
        List<Selectable> nodes = html.xpath("//a[@class='pagingNormal']").nodes();
        for (Selectable node : nodes) {
            if ("下一页".equals(node.xpath("//*/text()").get())) {
                String targetLink = node.links().get();
                if (targetLink == null || targetLink.isEmpty()) {
                    // 没有获取到 href 的 url， 需要使用 tagname 进行拼接
                    String domainString = CrawlerHelper.getDomainString(page.getRequest().getUrl());
                    targetLink = "http://" + domainString + node.xpath("//*/@tagname").get();
                }
                Request request = new Request(targetLink);
                request.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);
                page.addTargetRequest(request);
                break;
            }
        }
    }


}
