package com.clement.dao;

import com.clement.pojo.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author Qinghan Huang
 * @Date 2023/6/22 17:16
 * @Desc
 * @Version 1.0
 */
@Mapper
@Repository
public interface AccountMapper {
    @Select("select * from account where username=#{text} or email=#{text}")
    Account findAccountByUsernameOrEmail(@Param("text") String  text);
}
