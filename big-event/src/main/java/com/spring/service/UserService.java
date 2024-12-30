package com.spring.service;

import com.spring.pojo.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    // 根据用户名查询用户
    User findByName(String username);
    // 注册新用户
    void register(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String password);
}
