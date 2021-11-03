package com.internetplus.bankpunishment.webservice;

import com.internetplus.bankpunishment.entity.BankPunishment;
import com.internetplus.bankpunishment.webservice.exceptions.CustomerException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/27
 * webservice 接口，提供数据访问服务
 */
@WebService(targetNamespace = "http://webservice.bankpunishment.internetplus.com")
public interface DataService {

    /**
     * 根据 pageSize 确定数据库内的数据能够被拆分的页数
     *
     * @param pageSize 一页多少条数据
     * @return 共有多少页
     */
    long getDataPageNum(@WebParam(name = "pageSize") int pageSize) throws CustomerException;


    /**
     * 根据页的大小和页号获取数据库内的数据
     *
     * @return 数据项列表
     */
    List<BankPunishment> getDataListByPageNo(@WebParam(name = "pageSize") int pageSize,
                                             @WebParam(name = "pageNo") long pageNo) throws CustomerException;

    /**
     * 启动爬虫
     *  * (需要安装 chromeDriver 驱动，然后在 application.yml 里面配置驱动的路径)
     */
    void startUpCrawler() throws CustomerException;

}
