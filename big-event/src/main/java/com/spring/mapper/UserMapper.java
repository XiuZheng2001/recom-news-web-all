package com.spring.mapper;

import com.spring.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username};")
    User findByUserName(String username);

    @Insert("insert into user(username, password, create_time, update_time) " +
            "values (#{username}, #{password}, now(), now());")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname}, email=#{email}, update_time=#{updateTime} where id=#{id};")
    void update(User user);

    // 花括号里放的是下面对应方法的参数名。当传递的是实体类时，花括号里的应该对应pojo的属性名。
    @Update("update user set user_pic=#{avatarUrl}, update_time=now() where id=#{id};")
    void updateAvatar(String avatarUrl, int id);

    @Update("update user set password=#{md5password}, update_time=now() where id=#{id};")
    void updatePwd(String md5password, int id);
}
