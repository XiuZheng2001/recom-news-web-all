package com.spring.service;

import com.spring.pojo.Article;
import com.spring.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(int pageNum, int pageSize, String categoryId, String state);
}
