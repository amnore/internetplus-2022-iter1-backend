package com.internetplus.crawler.parser;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.ocr.AipOcr;
import com.internetplus.crawler.parser.imgParserPojo.ImgParseResult;
import com.internetplus.crawler.parser.imgParserPojo.ImgParseResultFileUrl;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/23
 */
public class ImgParser {
    //设置APPID/AK/SK
    public static final String APP_ID = "25042206";
    public static final String API_KEY = "9ikZdsTWnhS5HN9Y6XDAlQAj";
    public static final String SECRET_KEY = "PwlB46OcrBn2QlGS1vmQHdMiEe6bZZ7f";
    public static ExecutorService executor = Executors.newCachedThreadPool(); // 线程池

    public static void main(String[] args) {
        baiduOcr("G:\\win快捷方式\\Downloads\\b.png");
    }

    /**
     * 将 tif 文件转为 png 格式的文件
     * 返回转化后的文件的路径
     */
    private static String tif2Png(String tifPath) {
        String pngPath = tifPath.substring(0, tifPath.lastIndexOf(".")) + ".png";
        try {
            final BufferedImage tif = ImageIO.read(new File(tifPath));
            ImageIO.write(tif,"png", new File(pngPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pngPath;
    }

    /**
     * 调用百度 ocr 获得数据
     */
    private static void baiduOcr(String imgPath) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        Future<String> future = executor.submit(() ->{
            JSONObject res = client.tableRecognitionAsync(imgPath, new HashMap<String, String>());
            ImgParseResult result = JSON.parseObject(res.toString(), ImgParseResult.class);
            if (result == null || result.getResult() == null || result.getResult().get(0) == null) return null;
            return result.getResult().get(0).getRequest_id();
        });

        try {
            String requestId = future.get();
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("result_type", "excel");
            // 表格识别结果
            JSONObject res = client.tableResultGet(requestId, options);
            String excelFileUrl = (res.getJSONObject("result").getString("result_data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
