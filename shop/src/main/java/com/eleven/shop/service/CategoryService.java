package com.eleven.shop.service;

import java.util.List;

import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.SecondCategory;

public interface CategoryService {
    public List<Category> findAll();
    
    public PagingList<Category> getCategorys(String queryStr, int pageNow, int pageSize);

	public void addCategory(Category category);
	
	public void updateCategory(Category category);
	
	public int deleteCategory(String ids);
	
	public PagingList<SecondCategory> getSecondCategorys(String queryStr, int pageNow, int pageSize);

	public void addSecondCategory(SecondCategory categorySecond);

	public int deleteSecondCategory(String ids);

	public void updateSecondCategory(SecondCategory categorySecond);
	
	public List<SecondCategory> getAllSecondCategory();
	public List<SecondCategory> getSecondCategoryWithCid(Integer cid);
}
