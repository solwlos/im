package com.sol.admin.modules.security.config;

/**
 * @author sol
 * @date 2023/12/22 16:26
 * @Version 1.0
 */

import com.sol.admin.modules.security.filter.JwtTokenFilter;
import com.sol.admin.modules.security.handler.MyAuthenticationHandler;
import com.sol.admin.modules.security.service.AccountAuthenticationImpl;
import com.sol.admin.modules.security.service.AuthorizationManagerImpl;
import com.sol.admin.modules.security.service.MailAuthenticationImpl;
import com.sol.admin.modules.security.filter.MenuFilterInvocationSecurityMetadataSource;
import com.sol.admin.modules.security.service.MenuAccessDecisionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerJwtAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //                .authenticationProvider(authenticationProvider(new AuthenticationConfiguration()))
//                .authenticationManager(authenticationManager(new AuthenticationConfiguration()))

                // security 6.0 之后的写法
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .anyRequest().access(authorizationManager());
                })


//                .authorizeRequests(authorize -> {
//                    authorize
//                            .withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor());
//                })

                .exceptionHandling(exceptions ->
                    exceptions
                            .authenticationEntryPoint(new MyAuthenticationHandler())
                            .accessDeniedHandler(new MyAuthenticationHandler())
                )
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**
     * 自定义 FilterSecurityInterceptor  ObjectPostProcessor 以替换默认配置达到动态权限的目的
     * @return ObjectPostProcessor
     */
    private ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor() {
        return new ObjectPostProcessor<>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setAccessDecisionManager(accessDecisionManager());
                object.setSecurityMetadataSource(filterInvocationSecurityMetadataSource());
                return object;
            }
        };
    }

    public AuthorizationManager authorizationManager(){
        return new AuthorizationManagerImpl();
    }


    @Primary
    @Bean
    public AuthenticationManagerBuilder authenticationProvider(AuthenticationManagerBuilder auth){
        return auth
                .authenticationProvider(accountAuthentication())
                .authenticationProvider(mailAuthentication());
    }




    @Bean
    @Primary
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource(){
        return new MenuFilterInvocationSecurityMetadataSource();
    }
    @Bean
    @Primary
    public AccessDecisionManager accessDecisionManager(){
        return new MenuAccessDecisionManager();
    }


    @Bean
    public AccountAuthenticationImpl accountAuthentication(){
        return new AccountAuthenticationImpl();
    }

    @Bean
    public MailAuthenticationImpl mailAuthentication(){
        return new MailAuthenticationImpl();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    /**
     * 密码加密方式,默认
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(List.of("**"));
//        //是否发送Cookie信息
//        configuration.setAllowCredentials(false);
//        //放行哪些原始域(头部信息
//        configuration.addAllowedHeader("*");
//        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//        configuration.addExposedHeader("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}

