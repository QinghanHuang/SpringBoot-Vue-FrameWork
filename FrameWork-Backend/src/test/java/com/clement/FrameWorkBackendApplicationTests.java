package com.clement;

import com.clement.dao.AccountMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class FrameWorkBackendApplicationTests {
    @Resource
    BCryptPasswordEncoder encoder;
    @Resource
    AccountMapper accountMapper;
    @Test
    void contextLoads() {
        System.out.println(accountMapper.findAccountByUsernameOrEmail("123@gmail.com"));

    }

}
