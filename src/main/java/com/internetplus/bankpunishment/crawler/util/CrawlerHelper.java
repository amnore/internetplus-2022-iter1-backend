package com.internetplus.bankpunishment.crawler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yunthin.Chow
 * @version 1.0 Created by Yunthin.Chow on 2021/10/20
 *          <p>
 *          封装一些爬虫经常用的工具方法
 */
public class CrawlerHelper {
    static final Logger logger = LoggerFactory.getLogger(CrawlerHelper.class);

    public static void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            throw new Error(e);
        }
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
        if (path == null) {
            return;
        }
        File toDeleteFile = new File(path);
        if (!toDeleteFile.exists()) {
            return;
        }
        toDeleteFile.delete();
    }

    /**
     * @param urlStr 要下载的文件的 url 下载到了编译后的 classes/downloadFiles 目录下面
     * @return 返回下载的文件的路径
     */
    public static String downloadFromUrl(String urlStr) {
        String filePath = PunishmentDownloader.downloadPath + urlStr.substring(urlStr.lastIndexOf('/'));
        logger.info("downloading {} to {}", urlStr, filePath);

        RemoteWebDriver driver =  PunishmentDownloader.driver.get();
        driver.get(urlStr);

        while (!new File(filePath).exists()) {
            sleep(100);
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
