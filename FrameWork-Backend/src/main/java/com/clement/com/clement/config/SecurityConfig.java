package com.clement.com.clement.config;

import com.alibaba.fastjson.JSONObject;
import com.clement.pojo.RestBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Locale;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 14:49
 * @Desc
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure)
                ).logout(logout -> logout.
                        logoutUrl("/api/auth/logout")

                ).exceptionHandling(entry->entry
                        .authenticationEntryPoint(this::onAuthenticationFailure)
                ).csrf(csrf -> csrf.disable())
                .build();
    }

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String successLogin = JSONObject.toJSONString(RestBean.succcess("Success login"));
        response.getWriter().write(successLogin);
    }
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        String failureLogin = JSONObject.toJSONString(RestBean.failure(401,exception.getMessage()));
        response.getWriter().write(failureLogin);
    }

}
