package com.eleven.shop.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.SecondCategory;
import com.eleven.shop.dao.CategoryDao;
import com.eleven.shop.dao.SecondCategoryDao;
import com.eleven.shop.service.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService{
	@Resource
    private CategoryDao categoryDao;
	@Resource
	private SecondCategoryDao secondCategoryDao;
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return categoryDao.findAll();
	}
	public PagingList<Category> getCategorys(String queryStr, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		//PagingList<Category> pages=new PagingList<Category>();
		//List<Category> categories=findAll();
		//pages.setPagingList(pageNow, pageSize, categories.size(), categories);
		//return categoryDao.getCategorys(queryStr, pageNow, pageSize);
		return categoryDao.getCategorys(queryStr, pageNow, pageSize);
	}
	public void addCategory(Category category) {
		// TODO Auto-generated method stub
		categoryDao.add(category);
	}
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		categoryDao.update(category);
	}
	public int deleteCategory(String ids) {
		// TODO Auto-generated method stub
		return categoryDao.deleteCategry(ids);
	}
	public PagingList<SecondCategory> getSecondCategorys(String queryStr, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		return secondCategoryDao.getSecondCategorys(queryStr, pageNow, pageSize);
	}
	public void addSecondCategory(SecondCategory categorySecond) {
		// TODO Auto-generated method stub
		secondCategoryDao.add(categorySecond);
	}
	public int deleteSecondCategory(String ids) {
		// TODO Auto-generated method stub
		return secondCategoryDao.deleteSecondCategry(ids);
	}
	public void updateSecondCategory(SecondCategory categorySecond) {
		// TODO Auto-generated method stub
		secondCategoryDao.update(categorySecond);
	}
	public List<SecondCategory> getAllSecondCategory() {
		// TODO Auto-generated method stub
		return secondCategoryDao.getAllSecondCategory();
	}
	public List<SecondCategory> getSecondCategoryWithCid(Integer cid){
		return secondCategoryDao.getSecondCategoryWithCid(cid);
	}
}
