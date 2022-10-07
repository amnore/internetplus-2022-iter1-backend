package com.internetplus.bankpunishment.crawler.downloader;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Yunthin.Chow
 * @version 1.0 Created by Yunthin.Chow on 2021/10/20 webmagic 爬虫工具的 downloader
 *          组件
 */
public class PunishmentDownloader implements Downloader {
    public static String downloadPath;

    static {
        try {
            downloadPath = Files.createTempDirectory("internetplus2021-crawler").toString();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static final ThreadLocal<RemoteWebDriver> driver = ThreadLocal.withInitial(() -> {
        // 添加chrome的配置信息
        ChromeOptions chromeOptions = new ChromeOptions();
        Map<String, Object> options = new HashMap<>();
        options.put("download.default_directory", downloadPath);
        options.put("download.prompt_for_download", false);
        options.put("download.directory_upgrade", true);
        options.put("plugins.always_open_pdf_externally", true);
        // 设置为无头模式
        chromeOptions.addArguments("--headless", "--window-size=800,600", "--disable-gpu");
        chromeOptions.setExperimentalOption("prefs", options);
        // 使用配置信息 创建driver对象
        return new ChromeDriver(chromeOptions);
    });

    /**
     * Selenium 的一些驱动配置
     */
    public PunishmentDownloader() {
        // 加载chromedriver 是使用chorme的必要条件
        Properties properties = new Properties();
        try {
            properties.load(PunishmentDownloader.class.getClassLoader().getResourceAsStream("chromeDriver.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String chromedriverPath = properties.getProperty("chrome-driver-path");
        // System.setProperty("webdriver.chrome.driver",chromedriverPath);
    }

    @Override
    public Page download(Request request, Task task) {
        // 获取 url ，用 driver 进行访问
        String url = request.getUrl();
        RemoteWebDriver driver = this.driver.get();
        driver.get(url);
        // 将访问的结果封装成 page 对象返回
        Page page = createPage(driver.getPageSource(), driver.getCurrentUrl(), request);

        return page;
    }

    /**
     * 将 driver 爬取的结果封装成 page 对象
     */
    private Page createPage(String html, String url, Request request) {
        Page page = new Page();
        // 设置回去html
        page.setRawText(html);
        // 把url包装成selectable对象
        page.setUrl(new PlainText(url));
        // 下载成功
        page.isDownloadSuccess();
        // 还要设置request对象
        page.setRequest(request);

        return page;
    }

    @Override
    public void setThread(int i) {
    }
}
