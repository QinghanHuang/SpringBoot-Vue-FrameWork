package com.clement.service.impl;

import com.clement.dao.AccountMapper;
import com.clement.pojo.Account;
import com.clement.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 17:15
 * @Desc
 * @Version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {
    @Resource
    AccountMapper accountMapper;
    @Override
    public UserDetails loadUserByUsername(String username) {

        if(username==null) throw new UsernameNotFoundException("Username can't be null");
        Account account= accountMapper.findAccountByUsernameOrEmail(username);
        if(account==null){
            throw new UsernameNotFoundException("Username or password is not found");
        }

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}
