package com.ziru;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot启动类
 */
@EnableSwagger2Doc
@SpringBootApplication
@MapperScan(value = "com.ziru.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
        System.out.println("后端服务器启动完毕，端口:8080");
        System.out.println("Swagger地址:http://localhost:8080/doc.html");
    }
}
