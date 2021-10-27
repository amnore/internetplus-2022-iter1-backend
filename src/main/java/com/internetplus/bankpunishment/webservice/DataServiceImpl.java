package com.internetplus.bankpunishment.webservice;

import com.internetplus.bankpunishment.entity.BankPunishment;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.Arrays;
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
public class DataServiceImpl implements DataService{

    /**
     * 根据 pageSize 确定数据库内的数据能够被拆分的页数
     * @param pageSize 一页多少条数据
     * @return 共有多少页
     */
    @Override
    public long getDataPageNum(int pageSize) {
        return 10;
    }


    /**
     * 根据页的大小和页号获取数据库内的数据
     * @return 数据项列表
     */
    @Override
    public List<BankPunishment> getDataListByPageNo(int pageSize, long pageNo) {
        return Arrays.asList(
                new BankPunishment(),
                new BankPunishment()
        );
    }
}
