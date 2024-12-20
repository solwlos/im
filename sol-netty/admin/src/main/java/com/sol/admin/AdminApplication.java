package com.sol.admin;

import com.groupdocs.viewer.Viewer;
import com.groupdocs.viewer.options.HtmlViewOptions;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.sol.admin"})
@MapperScan({"com.sol.admin.modules.system.mapper", "com.sol.admin.modules.chat.mapper"})
//@MapperScan("com.sol.admin.modules.{system, chat}.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);


    }




}
