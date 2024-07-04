package com.example.authority.config;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
 
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.authority.controller"))  //controller类所在的路径
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("权限管理系统")
                        .description("通用的管理系统基础项目，可以拓展任何管理项目")
                        .version("9.0")
                        .contact(new Contact("程序员云翼","https://space.bilibili.com/56136258?spm_id_from=333.788.0.0","www@gmail.com"))
                        .license("hello")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}