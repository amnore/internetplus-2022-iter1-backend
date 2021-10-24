package com.internetplus.bankpunishment.crawler.parser.pdfParserPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/23
 *
 * 对 pdf 格式的文件进行解析的时候用到的实体类
 * 该类代表 pdf 中一个表格的单元格
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfTableCell {
    double top;
    double left;
    double width;
    double height;
    String text;
}
