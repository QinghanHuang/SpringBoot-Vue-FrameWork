package com.clement.dao;

import com.clement.pojo.Account;
import com.clement.pojo.AccountDetail;
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

    @Insert("insert into account(username,password,email) values(#{username},#{password},#{email}) ")
    int creatAccount(@Param("username") String username,@Param("password") String password, @Param("email") String email);

    @Insert("insert into account_detail(username,email) values(#{username},#{email}) ")
    int creatAccountDetail(@Param("username") String username, @Param("email") String email);

    @Select("select * from account_detail where username=#{text} or email=#{text}")
    AccountDetail findAccountDetailByUsernameOrEmail(@Param("text") String  text);

    @Update("update account set password=#{password}  where email=#{email}")
    int resetPassword( @Param("email") String email,@Param("password") String password);
}
