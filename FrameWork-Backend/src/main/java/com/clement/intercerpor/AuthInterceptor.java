package com.clement.intercerpor;

import com.clement.dao.AccountMapper;
import com.clement.pojo.AccountDetail;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/27 23:01
 * @Desc
 * @Version 1.0
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Resource
    AccountMapper accountMapper;

    /**
     * 1. from spring security get user object
     * 2. use username to find AccountDetail
     * 3. put accountDetail into session
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //from spring security get user object
        SecurityContext context= SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        //UserDetail User from security
        User user    = (User) authentication.getPrincipal();
        String username = user.getUsername();
        AccountDetail accountDetail = accountMapper.findAccountDetailByUsernameOrEmail(username);
        request.getSession().setAttribute("account",accountDetail);


        return true;
    }
}
