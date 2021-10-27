package com.internetplus.bankpunishment.config;

import com.internetplus.bankpunishment.webservice.DataService;
import com.internetplus.bankpunishment.webservice.DataServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yunthin.Chow
 * @version 1.0
 * Created by Yunthin.Chow on 2021/10/27
 *
 * webservice 配置类
 */
@Configuration
public class CxfConfig {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public DataService dataService() {
        return new DataServiceImpl();
    }

    /**
     * 发布服务并指定访问 url
     */
    @Bean
    public EndpointImpl dataEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), dataService());
        endpoint.publish("/data");
        return endpoint;
    }
}
