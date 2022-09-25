package com.internetplus.bankpunishment.crawler.processor.handler;

import com.internetplus.bankpunishment.crawler.target.PageTarget;
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
 * 各个分支的“政务公开目录”页面，获取该页面中的“行政处罚”按钮的 url， 并对其进行访问
 */
public class BranchPunishLinksPageHandler implements ProcessHandler{

    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 解析出“行政处罚”的 url
        String dataEntranceLink = null;
        List<Selectable> nodes = html.xpath("//td/a").nodes();
        for (Selectable node : nodes) {
            if (node.xpath("//*/text()").get().equals("行政处罚") || node.xpath("//*/text()").get().equals("行政处罚公示")) {
                dataEntranceLink = node.links().get();
                break;
            }
        }

        // 对其进行访问
        Request request = new Request(dataEntranceLink);
        request.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);
        page.addTargetRequest(request);
    }
}
