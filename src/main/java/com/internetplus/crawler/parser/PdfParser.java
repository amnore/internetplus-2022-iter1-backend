package com.internetplus.crawler.parser;

import com.alibaba.fastjson.JSON;
import com.internetplus.crawler.parser.pdfParserPojo.PdfTable;
import com.internetplus.crawler.parser.pdfParserPojo.PdfTableCell;
import com.internetplus.crawler.pojo.DataEntity;
import com.internetplus.crawler.util.pojo.DataFieldHelper;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import technology.tabula.CommandLineApp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/22
 */
public class PdfParser {

    public static void main(String[] args) {
        String pdfPath = "G:\\win快捷方式\\Downloads\\2021070118490537384.pdf";
        parsePdf2DataEntity(pdfPath);
    }

    /**
     * 给定 pdf 文件对其进行解析，返回 List<DataEntity>
     */
    public static List<DataEntity> parsePdf2DataEntity(String pdfPath) {
        // pdf 识别成 jsonString
        String jsonString = parsePdf2Json(pdfPath);
        // 使用 fastJson 将 Json 转换成 PdfTableParseResult
        List<PdfTable> tableList = JSON.parseArray(jsonString, PdfTable.class);
        return extractDataEntityFromTableList(tableList);
    }

    /**
     * 将解析出来的 tableList 里面的数据抽取出来封装成 List<DataEntity> 进行返回
     */
    private static List<DataEntity> extractDataEntityFromTableList(List<PdfTable> pdfTableList) {
        List<DataEntity> dataEntityList = new ArrayList<>(); // 待返回的结果列表
        List<String> fieldNameList = new ArrayList<>(); // 表头字段的名称列表
        boolean findHeader = false; // 是否已经找到表头
        for (PdfTable pdfTable : pdfTableList) {
            for (List<PdfTableCell> tableRow : pdfTable.getData()) {
                if (!findHeader) {
                    // 还没有找到表头先找表头
                    if (DataFieldHelper.typeJudge(tableRow.get(0).getText()) != null) {
                        // 说明这一行是表头行
                        findHeader = true;
                        for (PdfTableCell pdfTableCell : tableRow) {
                            fieldNameList.add(pdfTableCell.getText());
                        }
                    }
                } else {
                    DataEntity dataEntity = new DataEntity();
                    // 已经找到表头，接下来每一行都是表格数据
                    for (int cIndex = 0; cIndex < tableRow.size(); ++cIndex) {
                        String value = tableRow.get(cIndex).getText().replaceAll("\r", "");
                        // 对日期需要特殊处理一下
                        if (DataFieldHelper.isPunishDate(fieldNameList.get(cIndex))) {
                            value = buildDate(value);
                        }
                        DataFieldHelper.setRegardingValue(fieldNameList.get(cIndex), value, dataEntity);
                        if (cIndex == fieldNameList.size() - 1) {
                            // 到最后一个字段了，还要把 dataEntity 添加到列表中
                            if (!DataFieldHelper.isAllFieldNull(dataEntity)) {
                                dataEntityList.add(dataEntity);
                            }
                        }
                    }
                }
            }
        }
        return dataEntityList;
    }

    /**
     * 将 pdf 中的表格内容以 json 字符串的方式解析后返回
     */
    private static String parsePdf2Json(String pdfPath) {
        StringBuilder jsonResultStringBuilder = new StringBuilder();
        String[] args = {pdfPath, "-l", "-f=JSON", "-p=all"};
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(CommandLineApp.buildOptions(), args);
            new CommandLineApp(jsonResultStringBuilder, cmd).extractTables(cmd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonResultStringBuilder.toString();
    }

    /**
     * 格式化 word 中的日期
     * 变成 2021-10-20 的格式
     */
    private static String buildDate(String cellText) {
        return cellText.replaceAll(" ", "").replaceAll("\\.|,|年|月|/", "-").replaceAll("日", "");
    }

}
