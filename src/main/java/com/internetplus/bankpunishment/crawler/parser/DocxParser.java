package com.internetplus.bankpunishment.crawler.parser;

import com.internetplus.bankpunishment.crawler.util.DataFieldHelper;
import com.internetplus.bankpunishment.entity.BankPunishment;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/22
 */
public class DocxParser {

    public static List<BankPunishment> parseExcel2DataEntity(String filePath) {
        List<BankPunishment> dataEntityList = new ArrayList<>(); // 待返回的结果
        List<String> fieldNameList = new ArrayList<>(12); // 表头字段的名称列表
        boolean findHeader = false; // 是否已经找到表头（防止遇到列表前几行不是表头行）
        try {
            // 处理 docx 格式 即 office2007 以后版本
            Iterator<XWPFTable> it = new XWPFDocument(new FileInputStream(filePath)).getTablesIterator();//得到word中的表格
            // 遍历 docx 中的每一个表格
            while (it.hasNext()) {
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();
                //读取表格每一行数据
                for (XWPFTableRow xwpfTableRow : rows) {
                    BankPunishment dataEntity = new BankPunishment();
                    //读取每一列数据
                    List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
                    for (int cIndex = 0; cIndex < cells.size(); ++cIndex) {
                        String cellText = cells.get(cIndex).getText(); // rIndex 行 cIndex 列的单元格

                        if (!findHeader) {
                            // 字段表头
                            if (DataFieldHelper.typeJudge(cellText) != null) {
                                findHeader = true;
                                // 记录每个字段的出现顺序，读完这一行
                                for (; cIndex < cells.size(); ++cIndex) {
                                    cellText = cells.get(cIndex).getText();
                                    fieldNameList.add(cellText);
                                }
                            }
                        } else {
                            // 接下来每一行都是表格数据
                            // 对日期需要特殊处理一下
                            if (DataFieldHelper.isPunishDate(fieldNameList.get(cIndex))) {
                                cellText = buildDate(cellText);
                            }
                            DataFieldHelper.setRegardingValue(fieldNameList.get(cIndex), cellText, dataEntity);
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
        } catch (Exception e) {
            e.printStackTrace();
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

}
