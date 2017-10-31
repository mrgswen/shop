package com.eleven.shop.service;

import java.io.Serializable;
import java.util.List;

import com.eleven.shop.bean.News;

public interface NewsService {
     public void add(News news);
     public News get(Integer id);
     public List<News> findAll();
     public News getNews(Class<News> clazz,Serializable id);
}
