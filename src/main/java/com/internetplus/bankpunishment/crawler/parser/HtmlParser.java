package com.internetplus.bankpunishment.crawler.parser;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.crawler.util.pojo.DataFieldHelper;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/23
 * <p>
 * 对 WebMagic 传过来的 Page 对象进行解析
 * 提取出页面中的处罚信息公示表中的数据
 * 返回 List<DataEntity>
 */
public class HtmlParser {

    public static List<DataEntity> parseHtml2DataEntity(Page page) {
        List<DataEntity> dataEntityList = new ArrayList<>(); // 待返回的结果

        // 寻找表头
        List<Selectable> nodes = page.getHtml().xpath("//table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr[1]/td").nodes();
        if (nodes == null || nodes.size() == 0) nodes = page.getHtml().xpath("//div[@class='txt_con']/table/tbody/tr[1]/td").nodes(); //银行总行
        List<String> fieldNameList = new ArrayList<>(12); // 表头字段的名称列表
        for (Selectable node : nodes) {
            String selectableString = getSelectableString(node);
            if (DataFieldHelper.typeJudge(selectableString) != null) {
                fieldNameList.add(selectableString);
            } else {
                break;
            }
        }

        int rowNum = page.getHtml().xpath("//table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr").nodes().size() - 1; // 数据的行数 - 1表头行
        if (rowNum == -1) rowNum =  page.getHtml().xpath("//div[@class='txt_con']/table/tbody/tr").nodes().size() - 1; // 银行总行的数据
        int colNum = fieldNameList.size(); // 数据的列的个数
        String[][] dataArray = new String[rowNum][colNum];

        // 将接下来的数据项访问并且放到二维数组中（注意 rowspan）
        nodes = page.getHtml().xpath("//table/tbody/tr/td/table[2]/tbody/tr/td/table/tbody/tr/td").nodes();
        if (nodes == null || nodes.size() == 0) nodes = page.getHtml().xpath("//div[@class='txt_con']/table/tbody/tr/td").nodes(); //银行总行
        // 首先去掉 colNum 个表头
        for (int i = colNum; i < nodes.size(); ++i) {
            int colIndex = (i % colNum); // 该数据项的列索引
            int rowIndex = (i - colNum) / colNum; // 该数据项的行索引
            Selectable cellNode = nodes.get(i);
            String nodeValue = getSelectableString(cellNode);
            int rowSpan = 0; // 跨过的行数
            if (cellNode.xpath("//*/@rowspan") != null && !cellNode.xpath("//*/@rowspan").get().isEmpty()) {
                rowSpan = Integer.parseInt(cellNode.xpath("//*/@rowspan").get());
            }
            // 判断是不是已经被赋值
            while (dataArray[rowIndex][colIndex] != null) {
                ++colIndex;
                if (colIndex % colNum == 0) {
                    colIndex = 0;
                    ++rowIndex;
                }
            }
            dataArray[rowIndex][colIndex] = nodeValue;
            // 跨行进行赋值
            for (int j = 1; j < rowSpan; ++j) {
                dataArray[rowIndex + j][colIndex] = nodeValue;
            }
        }
        // 现在将 dataArray 中的数据封装成 dataEntity 对象
        for (String[] rowStrings : dataArray) {
            DataEntity dataEntity = new DataEntity();
            for (int i = 0; i < rowStrings.length; i++) {
                String value = rowStrings[i];
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
        return dataEntityList;
    }

    /**
     * 格式化 word 中的日期
     * 变成 2021-10-20 的格式
     */
    private static String buildDate(String cellText) {
        return cellText.replaceAll(" ", "").replaceAll("\\.|,|年|月|/", "-").replaceAll("日", "");
    }

    /**
     * 从 Selectable 中获取所有的文本信息
     */
    private static String getSelectableString(Selectable node) {
        StringBuilder stringBuilder = new StringBuilder();
        node.xpath("//*/text()").all().forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}
