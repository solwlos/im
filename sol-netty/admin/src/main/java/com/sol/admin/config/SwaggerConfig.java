package com.sol.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration //配置类
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("sol-netty-Api")
                .description("sol_netty 统一接口")
                .version("1.0")
                .contact(new Contact().name("sol").url("").email("solwlos@qq.com"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org")))

            .schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme())
            .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));

    }
    private SecurityScheme securityScheme() {
        SecurityScheme securityScheme = new SecurityScheme();
        //类型
        securityScheme.setType(SecurityScheme.Type.APIKEY);
        //请求头的name
        securityScheme.setName(HttpHeaders.AUTHORIZATION);
        //token所在未知
        securityScheme.setIn(SecurityScheme.In.HEADER);
        return securityScheme;
    }

}