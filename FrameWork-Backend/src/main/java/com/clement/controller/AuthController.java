package com.clement.controller;

import com.clement.pojo.RestBean;
import com.clement.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/26 20:41
 * @Desc
 * @Version 1.0
 */
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final String EMAIL_REGEX= "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$";
    private final String USERNAME_REGEX = "^[a-zA-Z0-9一-\u9fa5]+$";
    @Resource
    AuthService authService;

    @PostMapping("/valid-email")
    public RestBean<String> validEmail(@Pattern (regexp =EMAIL_REGEX)@RequestParam("email")String email, HttpSession httpSession){
        String emailResp= authService.sendValidEmail(email,httpSession.getId());
        if(emailResp==null){
            return RestBean.succcess("Email send success");
        }else{
            return RestBean.failure(400,emailResp);
        }
    }
    @PostMapping("/register")
    public RestBean<String> registerAccount(@Pattern(regexp = USERNAME_REGEX)@Length(min=5,max=10) @RequestParam("username") String username,
                                            @Length(min=8,max=20) @RequestParam("password") String password,
                                            @Pattern (regexp =EMAIL_REGEX)@RequestParam("email") String email,
                                            @RequestParam("code") String code,
                                            HttpSession session){
        String s=authService.validateAndRegister(username, password, email, code,session.getId());
        if(s==null) {
            return RestBean.succcess("Register Success");
        }else{
            return RestBean.failure(400,s);
        }
    }

}
