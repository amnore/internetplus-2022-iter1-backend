package com.internetplus.bankpunishment.webservice;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/27
 *
 * webservice 接口，提供数据访问服务
 */
import com.internetplus.bankpunishment.entity.BankPunishment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface DataService {

    /**
     * 根据 pageSize 确定数据库内的数据能够被拆分的页数
     * @param pageSize 一页多少条数据
     * @return 共有多少页
     */
    @WebMethod//标注该方法为webservice暴露的方法,用于向外公布，它修饰的方法是webservice方法，去掉也没影响的，类似一个注释信息。
    long getDataPageNum(@WebParam(name = "pageSize") int pageSize);


    /**
     * 根据页的大小和页号获取数据库内的数据
     * @return 数据项列表
     */
    @WebMethod
    List<BankPunishment> getDataListByPageNo(@WebParam(name="pageSize") int pageSize,
                                             @WebParam(name = "pageNo") long pageNo);
}
