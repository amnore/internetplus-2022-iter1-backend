package com.internetplus.bankpunishment.crawler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 * <p>
 * 封装一些爬虫经常用的工具方法
 */
public class CrawlerHelper {
    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            downloadUsingStream("http://bj.bcebos.com/v1/ai-edgecloud/336715FB5D044ED0997740627F2EE882.xls?authorization=bce-auth-v1%2Fd9272b4e9a38476db4470c2714e1339a%2F2021-10-23T07%3A08%3A58Z%2F172800%2F%2F2606957bc9b8771b5ab7a0b5e783a0428c7030a6bb8ed3be901326e436050e8b",
            "aaa.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }



    /**
     * 提取字符串中的域名
     *
     * @param str 待提取域名的字符串 url，如http://www.abc.cn/2015/10/hello-world/
     * @return 如 www.abc.cn
     */
    public static String getDomainString(String str) {
        String reg = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String path) {
        File toDeleteFile = new File(path);
        toDeleteFile.delete();
    }

    /**
     * @param urlStr 要下载的文件的 url
     *            下载到了编译后的 classes/downloadFiles 目录下面
     * @return 返回下载的文件的路径
     */
    public static String downloadFromUrl(String urlStr) {
        String dir = CrawlerHelper.class.getClassLoader().getResource("").getPath() + "/downloadFiles/";
        // 没有目录的话需要创建目录
        File dirFile = new File(dir);
        dirFile.mkdir();
        String filePath = dir + getFileNameFromUrl(urlStr);

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            // 建立链接
            URL httpUrl=new URL(urlStr);
            conn=(HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            // post方式不能使用缓存
            conn.setUseCaches(false);
            //连接指定的资源
            conn.connect();
            //获取网络输入流
            inputStream=conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            fileOut = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    private static String getFileNameFromUrl(String url) {
        String name = Long.toString(System.currentTimeMillis()) + ".X";
        int index = url.lastIndexOf("/");
        if (index > 0) {
            name = url.substring(index + 1);
            if (name.trim().length() > 0) {
                return name;
            }
        }
        return name;
    }



}
