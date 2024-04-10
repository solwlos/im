package com.sol.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @author sol
 * @date 2023/12/8 14:53
 * @Version 1.0
 */
public class CodeGenerator {

    /**
     * 数据源配置
     */
    public static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://172.19.48.1:3306/netty_chat", "sol", "sol=root")
            .dbQuery(new MySqlQuery());

    /**
     * 输出路径
     */
    public static final String outputDir = System.getProperty("user.dir") + "/admin/src/main/java/";

    public static void main(String[] args) {

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                .globalConfig(builder -> {
                    builder.author("sol") // 设置作者
                            .enableSpringdoc()
//                             .enableSwagger() // 开启 swagger 模式
                            .outputDir(outputDir)   // 指定输出目录
                            .disableOpenDir();   //禁止打开输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.sol.admin.modules.system"); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("chat_user_link","chat_message") // 设置需要生成的表名
                            .controllerBuilder().enableFileOverride().enableRestStyle()
                            .serviceBuilder().enableFileOverride()
                            .entityBuilder()
                                .enableLombok()
                                .enableFileOverride()
                            .mapperBuilder()
                                .enableFileOverride(); // 设置需要生成的表名
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
        System.out.println("代码生成成功！");
    }

}
