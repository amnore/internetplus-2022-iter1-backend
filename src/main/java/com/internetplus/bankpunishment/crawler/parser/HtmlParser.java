package com.internetplus.bankpunishment.crawler.parser;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.crawler.util.pojo.DataFieldHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yunthin.Chow
 * @version 1.0 Created by Yunthin.Chow on 2021/10/23
 *          <p>
 *          对 WebMagic 传过来的 Page 对象进行解析 提取出页面中的处罚信息公示表中的数据 返回 List<DataEntity>
 */
public class HtmlParser {
    static final Logger logger = LoggerFactory.getLogger(HtmlParser.class);

    public static List<DataEntity> parseHtml2DataEntity(Page page) {
        List<DataEntity> dataEntityList = new ArrayList<>(); // 待返回的结果

        for (Selectable table : page.getHtml().$("tbody").nodes()) {
            if (!table.$("table").nodes().isEmpty()) {
                continue;
            }

            // 寻找表头
            List<Selectable> nodes = table.$("tr:first-child > td").nodes();
            List<String> fieldNameList = new ArrayList<>(); // 表头字段的名称列表
            List<String> noMatch = new ArrayList<>();
            for (Selectable node : nodes) {
                String selectableString = node.xpath("/allText()").get();
                if (DataFieldHelper.typeJudge(selectableString) != null) {
                    fieldNameList.add(selectableString);
                } else {
                    noMatch.add(selectableString);
                }
            }
            if (fieldNameList.size() < 3) {
                continue;
            }

            ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
            // 将接下来的数据项访问并且放到二维数组中（注意 rowspan）
            for (Selectable row : table.$("tr:not(:first-child)").nodes()) {
                ArrayList<String> rowData = new ArrayList<>();
                dataArray.add(rowData);
                for (Selectable cell : row.$("td").nodes()) {
                    rowData.add(cell.xpath("/allText()").get());
                }
            }

            try {
                // 现在将 dataArray 中的数据封装成 dataEntity 对象
                for (ArrayList<String> rowStrings : dataArray) {
                    DataEntity dataEntity = new DataEntity();
                    for (int i = 0; i < rowStrings.size(); i++) {
                        String value = rowStrings.get(i);
                        // 接下来每一行都是表格数据
                        // 对日期需要特殊处理一下
                        if (DataFieldHelper.isPunishDate(fieldNameList.get(i))) {
                            value = buildDate(value);
                        }
                        DataFieldHelper.setRegardingValue(fieldNameList.get(i), value, dataEntity);
                        if (i == fieldNameList.size() - 1) {
                            // 到最后一个字段了，还要把 dataEntity 添加到列表中
                            if (!DataFieldHelper.isAllFieldNull(dataEntity)) {
                                dataEntityList.add(dataEntity);
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                logger.warn("could not parse headers for {}, headers are {}", page.getUrl(), noMatch);
            }
        }

        return dataEntityList;
    }

    /**
     * 格式化 word 中的日期 变成 2021-10-20 的格式
     */
    private static String buildDate(String cellText) {
        return cellText.replaceAll(" ", "").replaceAll("\\.|,|年|月|/", "-").replaceAll("日", "");
    }
}
