package com.internetplus.bankpunishment.webservice;

import com.internetplus.bankpunishment.crawler.downloader.PunishmentDownloader;
import com.internetplus.bankpunishment.crawler.pipeline.PunishmentPipeline;
import com.internetplus.bankpunishment.crawler.processor.PunishmentProcessor;
import com.internetplus.bankpunishment.crawler.target.PageTarget;
import com.internetplus.bankpunishment.data.BankPunishmentMapper;
import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.webservice.exceptions.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.jws.WebService;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/27
 * web service接口实现类
 */
@WebService(serviceName = "dataService", //对外发布的服务名
        targetNamespace = "http://webservice.bankpunishment.internetplus.com", // 名称空间
        endpointInterface = "com.internetplus.bankpunishment.webservice.DataService")
@Component
public class DataServiceImpl implements DataService {

    @Autowired
    BankPunishmentMapper bankPunishmentMapper;

    /**
     * 根据 pageSize 确定数据库内的数据能够被拆分的页数
     *
     * @param pageSize 一页多少条数据
     * @return 共有多少页
     */
    @Override
    public long getDataPageNum(int pageSize) throws CustomerException {
        if (pageSize <= 0 || pageSize > 10000) throw new CustomerException("参数错误", "每页数据的大小必须为 1 - 10000 之间的整数");
        try {
            long dataCount = bankPunishmentMapper.getBankPunishmentCount();
            return (long) Math.ceil(dataCount / (double) pageSize);
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }


    /**
     * 根据页的大小和页号获取数据库内的数据
     *
     * @return 数据项列表
     */
    @Override
    public List<BankPunishment> getDataListByPageNo(int pageSize, long pageNo) throws CustomerException {
        if (pageSize <= 0 || pageSize > 10000) throw new CustomerException("参数错误", "每页数据的大小必须为 1 - 10000 之间的整数");
        try {
            long offset = pageSize * pageNo;
            return bankPunishmentMapper.selectBankPunishmentByLimitAndOffset(pageSize, offset);
        } catch (Exception e) {
            throw new CustomerException(e.getMessage());
        }
    }

    @Override
    public void startUpCrawler() throws CustomerException {
        // 所有分行的请求，目标是解析所有分支银行的url
        Request branchRequest = new Request("http://www.pbc.gov.cn/rmyh/105226/105442/index.html");
        branchRequest.putExtra("target", PageTarget.BRANCH_LINKS);
        //总行的请求
        Request headquarterRequest = new Request("http://www.pbc.gov.cn/zhengwugongkai/4081330/4081344/4081407/4081705/index.html");
        headquarterRequest.putExtra("target", PageTarget.PUNISHMENT_LIST_PAGE);
        // 启动
        Spider.create(new PunishmentProcessor()).setDownloader(new PunishmentDownloader())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .addPipeline(new PunishmentPipeline())
                .thread(1)
                .addRequest(headquarterRequest)
                .addRequest(branchRequest);
                //.run(); // 同步启动
    }

    @Override
    public List<BankPunishment> selectBankPunishmentByFuzzyQuery(String queryString) {
        return bankPunishmentMapper.selectBankPunishmentByFuzzyQuery(queryString);
    }
}
