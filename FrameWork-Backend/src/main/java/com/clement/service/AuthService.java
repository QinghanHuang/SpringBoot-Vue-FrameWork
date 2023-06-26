package com.clement.service;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    String sendValidEmail(String email,String sessionId);
    String validateAndRegister(String username,String password,String email,String code,String sessionId);


}
