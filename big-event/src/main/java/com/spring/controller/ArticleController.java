package com.spring.controller;

import com.spring.mapper.ArticleMapper;
import com.spring.pojo.Article;
import com.spring.pojo.Category;
import com.spring.pojo.PageBean;
import com.spring.pojo.Result;
import com.spring.service.ArticleService;
import com.spring.service.CategoryService;
import com.spring.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success("添加文章成功");
    }
    @GetMapping
    public Result<PageBean<Article>> list(int pageNum, int pageSize, @RequestParam(required = false) String categoryId, @RequestParam(required = false) String state) {
        // false表示该值非必需，如果没有就设置为null
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }
}
