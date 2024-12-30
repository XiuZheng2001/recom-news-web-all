package com.spring.service.impl;

import com.spring.mapper.UserMapper;
import com.spring.pojo.User;
import com.spring.service.UserService;
import com.spring.utils.Md5Util;
import com.spring.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByName(String username) {
        // 调用mapper，执行sql
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 密码先加密，再添加。使用md5类来进行加密
        String md5String = Md5Util.getMD5String(password);
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        int id = (int)map.get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String password) {
        Map<String, Object> map = ThreadLocalUtil.get();
        int id = (int)map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(password), id);
    }
}
