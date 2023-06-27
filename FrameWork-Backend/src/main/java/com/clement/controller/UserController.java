package com.clement.controller;

import com.clement.pojo.AccountDetail;
import com.clement.pojo.RestBean;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/27 22:56
 * @Desc
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public RestBean<AccountDetail> me(@SessionAttribute("account") AccountDetail accountDetail){
        return RestBean.succcess(accountDetail);
    }
}
