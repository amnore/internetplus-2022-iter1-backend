package com.internetplus.bankpunishment.crawler.processor.handler;

import com.internetplus.bankpunishment.bl.BankPunishmentBl;
import com.internetplus.bankpunishment.crawler.extracter.Extracter;
import com.internetplus.bankpunishment.crawler.parser.DocParser;
import com.internetplus.bankpunishment.crawler.parser.HtmlParser;
import com.internetplus.bankpunishment.crawler.parser.PdfParser;
import com.internetplus.bankpunishment.crawler.util.CrawlerHelper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.crawler.parser.ExcelParser;
import com.internetplus.bankpunishment.crawler.parser.DocxParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Yunthin.Chow
 * @version 1.0 Created by Yunthin.Chow on 2021/10/20
 *
 *          处罚详情页 如果有链接的话，直接获取链接，下载进行解析 如果没有链接，则对 html 进行解析
 */
@Service
public class PunishmentDetailPageHandler implements ProcessHandler {

    // 直接通过 @Autowired 会出现获取不到 bean 对象(为 null)
    private static BankPunishmentBl bankPunishmentBl;

    static final Logger logger = LoggerFactory.getLogger(PunishmentDetailPageHandler.class);

    static final Map<String, Function<String, List<BankPunishment>>> parseFunc = new HashMap<>();

    static {
        parseFunc.put(".xls", ExcelParser::parseExcel2DataEntity);
        parseFunc.put(".doc", DocParser::parseExcel2DataEntity);
        parseFunc.put(".docx", DocxParser::parseExcel2DataEntity);
        parseFunc.put(".pdf", PdfParser::parsePdf2DataEntity);
    }

    @Autowired
    public void setBankPunishmentBl(BankPunishmentBl bankPunishmentBl) {
        PunishmentDetailPageHandler.bankPunishmentBl = bankPunishmentBl;
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String fileUrl = html.xpath("//p/a").links().get();
        Function<String, List<BankPunishment>> parser = null;

        if (fileUrl != null && !fileUrl.isEmpty()) {
            String suffix = fileUrl.substring(fileUrl.lastIndexOf('.'));
            if (suffix.endsWith("#")) {
                suffix = suffix.substring(0, suffix.length() - 1);
            }

            parser = parseFunc.get(suffix);
        }

        Consumer<List<BankPunishment>> processAndSave = entities -> {
            entities.forEach(Extracter::dataEntity2CaseLibraryEntity);
            saveDataEntityList(entities);
        };

        try {
            if (parser != null) {
                String filePath = null;
                try {
                    // 下载文件，解析文件
                    filePath = CrawlerHelper.downloadFromUrl(fileUrl);
                    // 调用具体的文件解析工具进行解析
                    List<BankPunishment> entities = parser.apply(filePath);
                    processAndSave.accept(entities);
                } finally {
                    // 解析完了之后删除文件
                    CrawlerHelper.deleteFile(filePath);
                }
            } else {
                List<BankPunishment> entities = HtmlParser.parseHtml2DataEntity(page);
                processAndSave.accept(entities);
            }
        } catch (InvalidFormatException e) {
            logger.warn("invalid format when parsing {}: {}", fileUrl, e.getMessage());
        }
    }

    /**
     * 对解析出来的 dataEntity 列表进行持久化
     */
    private void saveDataEntityList(List<BankPunishment> dataEntityList) {
        for (BankPunishment dataEntity : dataEntityList) {
            // 首先通过日期是否合法对数据进行一个筛选
            if (dataEntity.getPunishDate() != null && dataEntity.getPunishDate().contains("-")
                    && dataEntity.getPunishDate().indexOf("-") != dataEntity.getPunishDate().lastIndexOf("-")) {
                try {
                    bankPunishmentBl.insertBankPunishment(dataEntity);
                } catch(Exception e) {
                    throw new Error(e);
                }
            } else {
                throw new InvalidFormatException(String.format("invalid date in \"%s\"", dataEntity));
            }
        }
    }

    static class InvalidFormatException extends RuntimeException {
        InvalidFormatException(String message) {
            super(message);
        }
    }
}
