package com.eleven.shop.service;

import java.util.List;
import java.util.Map;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;

public interface ProductService {
	public List<Product> findHot(int size);

	public List<Product> findNew(int size);

	public Product findByPid(int pid);



	public PagingList<Product> getProductsByCid(Integer cid, Integer pageNow, int pageSize);
    
	public PagingList<Product> getProductsByCsid(Integer csid, Integer pageNow, int pageSize);
	
	public PagingList<Product> getProducts(String queryStr,String field,int page,int rows,Map<String, Object[]> params);

	public void addProduct(Product product);
	
	public int deleteProducts(String ids);
	
	public void updateProduct(Product product);
	public void updateProductNumber();
}
