package com.internetplus.crawler.processor.handler;

import com.internetplus.crawler.parser.DocParser;
import com.internetplus.crawler.parser.HtmlParser;
import com.internetplus.crawler.parser.PdfParser;
import com.internetplus.crawler.pojo.DataEntity;
import com.internetplus.crawler.util.CrawlerHelper;
import com.internetplus.crawler.parser.ExcelParser;
import com.internetplus.crawler.parser.DocxParser;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 *
 * 处罚详情页
 *      如果有链接的话，直接获取链接，下载进行解析
 *      如果没有链接，则对 html 进行解析
 */
public class PunishmentDetailPageHandler implements ProcessHandler{
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String fileUrl = html.xpath("//p/a").links().get();
        if (fileUrl == null || fileUrl.isEmpty()) {
            // 直接解析页面 html
            this.parseHtml(page);
        } else {
            // 下载文件，解析文件
            String filePath = CrawlerHelper.downloadFromUrl(fileUrl);
            // 调用具体的文件解析工具进行解析
            if (fileUrl.toLowerCase().contains(".xls")) {
                this.parseExcel(filePath); // (xls 和 xlsx 共用一个)
            } else if (fileUrl.toLowerCase().endsWith(".doc")) {
                this.parseDoc(filePath);
            } else if (fileUrl.toLowerCase().endsWith(".docx")) {
                this.parseDocx(filePath);
            } else if (fileUrl.toLowerCase().endsWith(".pdf")) {
                this.parsePdf(filePath);
            } else {
                this.parseHtml(page);
            }
            // 解析完了之后删除文件
            CrawlerHelper.deleteFile(filePath);
        }
    }

    /**
     * 直接对  html 页面进行解析
     */
    private void parseHtml(Page page) {
        List<DataEntity> dataEntityList = HtmlParser.parseHtml2DataEntity(page);
        for (DataEntity dataEntity : dataEntityList) {
            System.out.println(dataEntity);
        }
    }

    /**
     * 对 pdf 文件进行解析
     */
    private void parsePdf(String filePath) {
        List<DataEntity> dataEntityList = PdfParser.parsePdf2DataEntity(filePath);
        for (DataEntity dataEntity : dataEntityList) {
            System.out.println(dataEntity);
        }
    }

    /**
     * 对 doc 文件进行解析
     */
    private void parseDoc(String filePath) {
        List<DataEntity> dataEntityList = DocParser.parseExcel2DataEntity(filePath);
        for (DataEntity dataEntity : dataEntityList) {
            System.out.println(dataEntity);
        }
    }

    /**
     * 对 docx 文件进行解析
     */
    private void parseDocx(String filePath) {
        List<DataEntity> dataEntityList = DocxParser.parseExcel2DataEntity(filePath);
        for (DataEntity dataEntity : dataEntityList) {
            System.out.println(dataEntity);
        }
    }

    /**
     * 对 excel 文件进行解析
     */
    private void parseExcel(String filePath) {
        // 是一个 excel 文件 (xls 或 xlsx)
        List<DataEntity> dataEntityList = ExcelParser.parseExcel2DataEntity(filePath);
        for (DataEntity dataEntity : dataEntityList) {
            System.out.println(dataEntity);
        }

    }
}
