package com.ihrm.company;

import com.ihrm.common.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author xf
 * @create 2020-02-25-19:18
 */
//1.配置springboot包扫描
@SpringBootApplication(scanBasePackages = "com.ihrm.company")
//2.配置jpa包扫描
@EntityScan(value = "com.ihrm.domain.company")
public class CompanyApplication {
    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return  new IdWorker();
    }
}
