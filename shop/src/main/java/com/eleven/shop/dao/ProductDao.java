package com.eleven.shop.dao;

import java.util.List;
import java.util.Map;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;

public interface ProductDao extends BaseDao<Product> {
	public List<Product> findHot(int size);

	public List<Product> findNew(int size);

	public Product findByPid(int pid);

	public int getProNumberByCid(Integer cid);

	public List<Product> getProductsByCid(Integer cid, Integer pageNow, int pageSize);
   
	public int getProNumberByCsid(Integer csid);
   
	public List<Product> getProductsByCsid(Integer csid, Integer pageNow, int pageSize);
	
	public PagingList<Product> getProducts(String queryStr,String field,int page,int rows,Map<String, Object[]> params);
	
	public int deleteProducts(String ids);
	
	public void updateNumber();
}
