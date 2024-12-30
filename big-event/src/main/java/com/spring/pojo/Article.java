package com.spring.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    private int id;
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    @URL
    private String coverImg;
    private String state;
    @NotEmpty
    private int categoryId;
    private int createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
