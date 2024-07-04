package com.example.authority.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * 代码生成器
 */
public class CodeGenerator {


    private static final String database = "project-subjectcontest";
    private static final String url = "jdbc:mysql://localhost:3306/" + database;
    private static final String username = "root";
    private static final String password = "root";

    private static final String tableName = "awards";

    //项目根路径
    private static final String basePath =  System.getProperty("user.dir");

    public static void main(String[] args) {
        generate(tableName);

    }

    /**
     * 创建后端代码
     * @param tableName
     */
    public static void generate(String tableName){
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("程序员云翼") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir()  //不打开文件夹目录
                            .outputDir(basePath + "/src/main/java/"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.authority") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, basePath + "\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();
                    builder.serviceBuilder().formatServiceFileName("%sService");
                    builder.controllerBuilder()
//                            .enableHyphenStyle().  //开启驼峰转连字符
                            .enableRestStyle();   //开启@RestController 控制器
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
