package com.internetplus.bankpunishment.crawler.parser.pdfParserPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/23
 *
 * 对 pdf 格式的文件进行解析的时候用到的实体类
 * 该类封装了工具对 pdf 表格的解析结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfTable {
    String  extraction_method;
    double top;
    double left;
    double width;
    double height;
    List<List<PdfTableCell>> data;
}
