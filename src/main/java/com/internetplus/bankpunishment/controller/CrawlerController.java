package com.internetplus.bankpunishment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internetplus.bankpunishment.crawler.CrawlerStarter;

@RestController
@RequestMapping("/api/crawler")
public class CrawlerController {
    @GetMapping("/start")
    public void startUpCrawler(@RequestParam(required = false, defaultValue = "0") int nthreads) {
        CrawlerStarter.startCrawler(nthreads);
    }
}
