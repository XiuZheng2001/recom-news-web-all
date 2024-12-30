package com.spring.service;

import com.spring.pojo.Category;
import java.util.List;
import java.lang.reflect.Array;

public interface CategoryService {
    void add(Category category);

    List<Category> list();

    Category findById(int id);

    void update(Category category);

    void deleteById(Integer id);
}
