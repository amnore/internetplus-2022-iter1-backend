package com.internetplus.crawler.downloader;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/20
 * webmagic 爬虫工具的  downloader 组件
 */
public class PunishmentDownloader implements Downloader {

    private final RemoteWebDriver driver;

    /**
     * Selenium 的一些驱动配置
     */
    public PunishmentDownloader() {
        // 加载chromedriver 是使用chorme的必要条件
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        // 添加chrome的配置信息
        ChromeOptions chromeOptions = new ChromeOptions();
        // 设置为无头模式
        //chromeOptions.addArguments("--headless");
        // 设置打开的窗口大小 非必要属性
        chromeOptions.addArguments("--window-size=800,600");
        // 使用配置信息 创建driver对象
        this.driver = new ChromeDriver(chromeOptions);
    }

    @Override
    public Page download(Request request, Task task) {
        // 获取 url ，用 driver 进行访问
        String url = request.getUrl();
        driver.get(url);
        // 将访问的结果封装成 page 对象返回
        return createPage(driver.getPageSource(), driver.getCurrentUrl(), request);
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
    public void setThread(int i) {}
}
