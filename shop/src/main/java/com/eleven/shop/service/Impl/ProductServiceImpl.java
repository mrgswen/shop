package com.eleven.shop.service.Impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;
import com.eleven.shop.dao.ProductDao;
import com.eleven.shop.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductDao productDao;
	public List<Product> findHot(int size) {
		// TODO Auto-generated method stub
		return productDao.findHot(size);
	}
	public List<Product> findNew(int size) {
		// TODO Auto-generated method stub
		return productDao.findNew(size);
	}
	public Product findByPid(int pid) {
		// TODO Auto-generated method stub
		return productDao.findByPid(pid);
	}

	public PagingList<Product> getProductsByCid(Integer cid, Integer pageNow, int pageSize) {
		// TODO Auto-generated method stub
		 PagingList<Product> pages= new PagingList<Product>();
		 int totalSize=productDao.getProNumberByCid(cid);
		 List<Product> products = productDao.getProductsByCid( cid, pageNow, pageSize);
		 pages.setPagingList(pageNow, pageSize, totalSize, products);
		 return pages;
	}
	public PagingList<Product> getProductsByCsid(Integer csid, Integer pageNow, int pageSize) {
		// TODO Auto-generated method stub
		PagingList<Product> pages = new PagingList<Product>();
		int totalSize = productDao.getProNumberByCsid(csid);
		List<Product> products =productDao.getProductsByCsid(csid, pageNow, pageSize);
		pages.setPagingList(pageNow, pageSize, totalSize, products);
		return pages;
	}
	public PagingList<Product> getProducts(String queryStr, String field, int page, int rows,
			Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		return productDao.getProducts(queryStr, field, page, rows, params);
	}
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		productDao.add(product);
	}
	public int deleteProducts(String ids) {
		// TODO Auto-generated method stub
		return productDao.deleteProducts(ids);
	}
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		productDao.update(product);
	}
	public void updateProductNumber() {
		// TODO Auto-generated method stub
		productDao.updateNumber();
	}
    
}
