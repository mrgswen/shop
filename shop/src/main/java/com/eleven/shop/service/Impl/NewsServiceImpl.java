package com.eleven.shop.service.Impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.News;
import com.eleven.shop.dao.NewsDao;
import com.eleven.shop.service.NewsService;
@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsDao newsDao;
	public void add(News news) {
		// TODO Auto-generated method stub
		newsDao.add(news);
	}

	public News get(Integer id) {
		// TODO Auto-generated method stub
		return newsDao.get(News.class, id);
	}

	public List<News> findAll() {
		// TODO Auto-generated method stub
		return newsDao.find("from News news");
	}

	public News getNews(Class<News> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return newsDao.get(clazz, id);
	}

}
