package com.eleven.shop.dao;

import java.util.List;

import com.eleven.shop.bean.SecondCategory;
import com.eleven.shop.bean.PagingList;

public interface SecondCategoryDao extends BaseDao<SecondCategory>{
   public PagingList<SecondCategory> getSecondCategorys(String queryStr,int page,int rows);
   public int deleteSecondCategry(String ids);
   public List<SecondCategory> getAllSecondCategory();
   
   public List<SecondCategory> getSecondCategoryWithCid(Integer cid);
}
