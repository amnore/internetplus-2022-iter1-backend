package com.internetplus.bankpunishment.crawler.parser;

import com.internetplus.bankpunishment.crawler.pojo.DataEntity;
import com.internetplus.bankpunishment.crawler.util.pojo.DataFieldHelper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/22
 * 处理doc格式 即 word 2003版本
 *
 */
public class DocParser {
    public static void main(String[] args) {
        String filePath = "G:\\win快捷方式\\Downloads\\2021042617214241776.doc";
        parseExcel2DataEntity(filePath);
    }

    /**
     * 给定 doc 文件对其进行解析
     * @param filePath doc 文件的路径
     * @return 返回 DataEntity 的列表
     */
    public static List<DataEntity> parseExcel2DataEntity(String filePath) {
        List<DataEntity> dataEntityList = new ArrayList<>(); // 待返回的结果
        List<String> fieldNameList = new ArrayList<>(12); // 表头字段的名称列表
        boolean findHeader = false; // 是否已经找到表头（防止遇到列表前几行不是表头行）
        try {
            HWPFDocument hwpf = new HWPFDocument(new POIFSFileSystem(new FileInputStream(filePath)));
            Range range = hwpf.getRange();//得到文档的读取范围
            TableIterator it = new TableIterator(range);
            // 迭代文档中的表格
            while (it.hasNext()) {
                Table tb = (Table) it.next();
                //迭代行
                for (int rIndex = 0; rIndex < tb.numRows(); rIndex++) {
                    TableRow tr = tb.getRow(rIndex);
                    DataEntity dataEntity = new DataEntity();
                    //迭代列
                    for (int cIndex = 0; cIndex < tr.numCells(); cIndex++) {
                        String cellText = getTdText(tr.getCell(cIndex));//取得单元格字符串
                        if (!findHeader) {
                            // 字段表头
                            if (DataFieldHelper.typeJudge(cellText) != null) {
                                findHeader = true;
                                // 记录每个字段的出现顺序，读完这一行
                                for (; cIndex < tr.numCells(); ++cIndex) {
                                    cellText = getTdText(tr.getCell(cIndex));
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
     * 获取单元格中的文字
     */
    private static String getTdText(TableCell td) {
        StringBuilder res = new StringBuilder();
        for (int k = 0; k < td.numParagraphs(); k++) {
            Paragraph para = td.getParagraph(k);
            String s = para.text();
            //去除后面的特殊符号
            if (null != s && !"".equals(s)) {
                s = s.substring(0, s.length() - 1);
            }
            res.append(s);
        }
        return res.toString();
    }

    /**
     * 格式化 word 中的日期
     * 变成 2021-10-20 的格式
     */
    private static String buildDate(String cellText) {
        return cellText.replaceAll(" ", "").replaceAll("\\.|,|年|月|/", "-").replaceAll("日", "");
    }
}
