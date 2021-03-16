package xk.baseinfo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"xk.baseinfo.mapper"})
public class XinKongBaseInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(XinKongBaseInfoApplication.class, args);
    }

}
