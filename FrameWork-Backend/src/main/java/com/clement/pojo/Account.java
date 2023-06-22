package com.clement.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 17:17
 * @Desc
 * @Version 1.0
 */
@Data
public class Account {
    private int id;
    private String email;
    private String username;
    private String password;
}
