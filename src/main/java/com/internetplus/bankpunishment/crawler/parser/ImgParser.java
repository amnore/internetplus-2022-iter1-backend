//package com.internetplus.bankpunishment.crawler.parser;
//
//import com.alibaba.fastjson.JSON;
//import com.baidu.aip.ocr.AipOcr;
//import com.internetplus.bankpunishment.crawler.parser.imgParserPojo.ImgParseResult;
//import com.internetplus.bankpunishment.crawler.util.baiduAPI.Base64Util;
//import com.internetplus.bankpunishment.crawler.util.baiduAPI.FileUtil;
//import com.internetplus.bankpunishment.crawler.util.baiduAPI.HttpUtil;
//import org.json.JSONObject;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * @author Yunthin.Chow
// * @version 1.0
// * Created by Yunthin.Chow on 2021/10/23
// */
//public class ImgParser {
//    //设置APPID/AK/SK
//    public static final String APP_ID = "25042206";
//    public static final String API_KEY = "9ikZdsTWnhS5HN9Y6XDAlQAj";
//    public static final String SECRET_KEY = "PwlB46OcrBn2QlGS1vmQHdMiEe6bZZ7f";
//    public static ExecutorService executor = Executors.newCachedThreadPool(); // 线程池
//
//    public static String dirPath = "G:\\win快捷方式\\Downloads\\";
//
//    public static void main(String[] args) {
////        tif2Png(dirPath + "2015112015572169782.tif");
////        baiduOcr(dirPath + "2015112015572169782.png");
//        form(dirPath + "2015112015572169782.png");
//    }
//
//    /**
//     * 将 tif 文件转为 png 格式的文件
//     * 返回转化后的文件的路径
//     */
//    private static String tif2Png(String tifPath) {
//        String pngPath = tifPath.substring(0, tifPath.lastIndexOf(".")) + ".png";
//        try {
//            final BufferedImage tif = ImageIO.read(new File(tifPath));
//            ImageIO.write(tif,"png", new File(pngPath));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return pngPath;
//    }
//
//    /**
//     * 调用百度 ocr 获得数据
//     */
//    private static void baiduOcr(String imgPath) {
//        // 初始化一个AipOcr
//        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
//
//        // 可选：设置网络连接参数
//        client.setConnectionTimeoutInMillis(2000);
//        client.setSocketTimeoutInMillis(60000);
//
//
//        // 调用接口
//        Future<String> future = executor.submit(() ->{
//            JSONObject res = client.tableRecognitionAsync(imgPath, new HashMap<String, String>());
//            ImgParseResult result = JSON.parseObject(res.toString(), ImgParseResult.class);
//            if (result == null || result.getResult() == null || result.getResult().get(0) == null) return null;
//            return result.getResult().get(0).getRequest_id();
//        });
//
//        try {
//            String requestId = future.get();
//            HashMap<String, String> options = new HashMap<String, String>();
//            options.put("result_type", "excel");
//            // 表格识别结果
//            JSONObject res = client.tableResultGet(requestId, options);
//            String excelFileUrl = (res.getJSONObject("result").getString("result_data"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String form(String imgPath) {
//        // 请求url
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/form";
//        try {
//            // 本地文件路径
//            byte[] imgData = FileUtil.readFileByBytes(imgPath);
//            String imgStr = Base64Util.encode(imgData);
//            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
//
//            String param = "image=" + imgParam;
//
//            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
//            String accessToken = "24.cac38aa0b542f71e80461dfc3db1ef23.2592000.1642042961.282335-25042206";
//
//            String result = HttpUtil.post(url, accessToken, param);
//            System.out.println(result);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
