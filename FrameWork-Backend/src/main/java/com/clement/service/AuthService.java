package com.clement.service;


import org.apache.ibatis.ognl.BooleanExpression;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    String sendValidEmail(String email, String sessionId, boolean hasAccount);
    String validateAndRegister(String username,String password,String email,String code,String sessionId);
    String validateOnly(String email,String code,String sessionId);
    boolean resetPassword(String email,String password);


}
