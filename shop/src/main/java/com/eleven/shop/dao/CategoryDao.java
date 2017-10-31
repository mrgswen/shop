package com.eleven.shop.dao;

import java.util.List;

import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.PagingList;

public interface CategoryDao extends BaseDao<Category>{
    public List<Category> findAll();
    
    public PagingList<Category> getCategorys(String queryStr,int pageNow,int pageSize);
    
    public int deleteCategry(String ids);
    
}
