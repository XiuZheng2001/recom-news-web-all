package com.spring.pojo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Data
public class User {
    // lombok可以在编译阶段自动生成setter和getter和toString，需要引入依赖并添加Data注解
    @NotNull
    private int id;
    private String username;
    @JsonIgnore
    private String password;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    @NotEmpty
    @Email
    private String email;
    // 头像会存放到一个三方服务器上，这里只是服务器地址
    private String user_pic;
    // 驼峰命名在yml文件里配置了
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
