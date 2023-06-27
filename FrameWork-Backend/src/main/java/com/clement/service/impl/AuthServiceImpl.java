package com.clement.service.impl;

import com.clement.dao.AccountMapper;
import com.clement.pojo.Account;
import com.clement.service.AuthService;
import jakarta.annotation.Resource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 17:15
 * @Desc
 * @Version 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    MailSender mailSender;

    @Resource
    StringRedisTemplate template;

    @Resource
    AccountMapper accountMapper;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) {

        if (username == null) throw new UsernameNotFoundException("Username can't be null");
        Account account = accountMapper.findAccountByUsernameOrEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("Username or password is not found");
        }

        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }

    /**
     * 1.generate code
     * 2.send email
     * 3. put k v to redis
     * 4. compare code with user input
     * 5. if success delete kv in redis
     */
    @Override
    public String sendValidEmail(String email, String sessionId,boolean hasAccount) {
        String key = "email:" + sessionId + ":" + email+":"+hasAccount ;
        // check cache of redis
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            Long expire = Optional.ofNullable(template.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
            if (expire > 120) {
                return "Request too frequent, Please try again later";
            }
        }
        Account account = accountMapper.findAccountByUsernameOrEmail(email);
        // This is for reset password
        if(hasAccount&&account==null){
            return "There is no account associated with this email address";
        }
        // This is for reset password
        if(!hasAccount&&account!=null){
            return "This email has already been registered by another user";
        }


        Random random = new Random();
        int code = random.nextInt(899999) + 100000;
        System.out.println(code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Your verify code for How Busy App");
        message.setText("Your Sign Up Verify Code : " + code);

        try {
            mailSender.send(message);

            //store to redis

            template.opsForValue().set(key, String.valueOf(code), 3, TimeUnit.MINUTES);

            return null;
        } catch (MailException mailException) {
            mailException.printStackTrace();
            return "Please use a valid email address";
        }
    }

    @Override
    public String validateAndRegister(String username, String password, String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email+":"+false;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "The verification code has expired. Please obtain it again";
            if (s.equals(code)) {

                //check if this username is occupied
                Account account=accountMapper.findAccountByUsernameOrEmail(username);
                if(account!=null){
                    return "This username is occupied";
                }

                password = encoder.encode(password);
                // remove this code in redis
                template.delete(key);
                if (accountMapper.creatAccount(username, password, email) > 0&&accountMapper.creatAccountDetail(username,email)>0) {
                    return null;
                } else {
                    return "Server Issue";
                }
            } else {
                return "Incorrect verification code";
            }
        } else {
            return "Please obtain the email verification code first";
        }
    }

    @Override
    public String validateOnly(String email, String code, String sessionId) {
        String key = "email:" + sessionId + ":" + email+":"+true;
        if (Boolean.TRUE.equals(template.hasKey(key))) {
            String s = template.opsForValue().get(key);
            if (s == null) return "The verification code has expired. Please obtain it again";
            if (s.equals(code)) {
                // remove this code in redis
                template.delete(key);
                return null;
            } else {
                return "Incorrect verification code";
            }
        } else {
            return "Please obtain the email verification code first";
        }

    }

    @Override
    public boolean resetPassword(String email, String password) {
        password=encoder.encode(password);
        return accountMapper.resetPassword(email,password)>0;
    }
}
