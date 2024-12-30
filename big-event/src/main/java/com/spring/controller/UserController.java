package com.spring.controller;
import com.spring.utils.JwtUtil;
import com.spring.utils.Md5Util;
import com.spring.utils.ThreadLocalUtil;
import jakarta.validation.Validation;
import com.spring.pojo.User;
import com.spring.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.ValueOperations;

import com.spring.pojo.Result;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userservice;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(String username, String password) {
        // 查询用户
        User u = userservice.findByName(username);
        if (u == null) {
            // 没被占用
            userservice.register(username, password);
            return Result.success();
        } else {
            // 已被占用
            return Result.error("已被占用");
        }
    }

    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 查询user看是否在数据库中
        User u = userservice.findByName(username);
        if (u == null) return Result.error("用户名错误");
        // 如果密码一致，进入下一个页面。不一致报错
        if (!u.getPassword().equals(Md5Util.getMD5String(password))) return Result.error("密码不正确");
        else {
            // 登陆成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("username", u.getUsername());
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
    }
    @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization")String token) {
        // 根据用户名查询用户，token中已经携带了用户名，直接解析token
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User u = userservice.findByName(username);
        return Result.success(u);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        // RequestBody将前端传来的json转换成实体类对象
        userservice.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userservice.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        String newPwd = params.get("new_pwd");
        String oldPwd = params.get("old_pwd");
        String rePwd = params.get("re_pwd");
        // 做参数校验，参数是否缺少
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要的参数");
        }
        // 再看原密码和oldPwd是否一样
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User u = userservice.findByName(username);
        if (!u.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码不正确");
        }
        // 看rePwd和newPwd是否一样
        if (!rePwd.equals(newPwd))
            return Result.error("两次输入的密码不一致");

        // 更新密码后，删除redis中原有的token，换成新的
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(token);

        // 完成更新
        userservice.updatePwd(newPwd);
        return Result.success();
    }

}

