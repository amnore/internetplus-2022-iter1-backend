package com.internetplus.crawler.parser.imgParserPojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/23
 *
 * 调用 百度 Ocr 对图片进行解析需要使用到的实体类
 *  该类表示获取到的结果封装类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgParseResult {
    List<ImgParseData> result;
    String log_id;
}
