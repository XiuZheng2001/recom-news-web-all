package com.spring.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.spring.mapper.ArticleMapper;
import com.spring.pojo.Article;
import com.spring.pojo.PageBean;
import com.spring.service.ArticleService;
import com.spring.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        Map<String, Object> map = ThreadLocalUtil.get();
        int userId = (int) map.get("id");
        article.setUpdateTime(LocalDateTime.now());
        article.setCreateTime(LocalDateTime.now());
        article.setCreateUser(userId);
        articleMapper.add(article);
    }
    @Override
    public PageBean<Article> list(int pageNum, int pageSize, String categoryId, String state) {
        PageBean<Article> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        //3.调用mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId,state);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
