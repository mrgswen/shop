package com.eleven.shop.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;
import com.eleven.shop.dao.ProductDao;
import com.eleven.shop.util.UUIDUtil;
import com.mysql.jdbc.PreparedStatement;
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao{

	public List<Product> findHot(int size) {
		// TODO Auto-generated method stub
		String hql="from Product p where p.isHot =1 order by p.createdTime desc";
		List<Product> hotProducts=find(hql, 0, size);
		return hotProducts;
	}

	public List<Product> findNew(int size) {
		// TODO Auto-generated method stub
		String hql = "from Product p order by p.createdTime desc";
		List<Product> newProducts=find(hql, 0, size);
		return newProducts ;
	}

	public Product findByPid(int pid) {
		// TODO Auto-generated method stub
		String hql ="from Product p where p.pid=?";
		Query query =getCurrentSession().createQuery(hql);
		query.setParameter(0, pid);
		return (Product) query.uniqueResult();
	}

	public int getProNumberByCid(Integer cid) {
		// TODO Auto-generated method stub
		String hql="select count(*) from Product p where p.categorySecond.category.cid=?";
		Query query =getCurrentSession().createQuery(hql);
		query.setParameter(0, cid);
		int  productNumbers=((Long) query.uniqueResult()).intValue();
		return productNumbers;
	}

	public List<Product> getProductsByCid(Integer cid, Integer pageNow, int pageSize) {
		// TODO Auto-generated method stub
		String hql="select distinct p from Product p join p.categorySecond cs join cs.category c where c.cid=:cid order by p.createdTime desc";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("cid", cid);
		List<Product> products=find(hql,params, pageNow, pageSize);
		return products;
	}

	public int getProNumberByCsid(Integer csid) {
		// TODO Auto-generated method stub
		String hql ="select count(*) from Product p where p.categorySecond.csid=?";
		Query query =getCurrentSession().createQuery(hql);
		query.setParameter(0, csid);
		int productNumbers = ((Long)query.uniqueResult()).intValue();
		return productNumbers;
	}

	public List<Product> getProductsByCsid(Integer csid, Integer pageNow, int pageSize) {
		// TODO Auto-generated method stub
		String hql ="select distinct p from Product p join p.categorySecond cs where cs.csid=:csid order by p.createdTime desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("csid", csid);
		List<Product> products = find(hql, params, pageNow, pageSize);
		return products;
	}

	public PagingList<Product> getProducts(String queryStr, String field, int page, int rows,
			Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(Product.class);
		criteria.addOrder(Order.desc("createdTime"));
		if (params.isEmpty()) {
			criteria.addOrder(Order.desc("isHot"));
			return getPagingListWhetherQuerystrNull(criteria, field, queryStr, page, rows);
		} else {
			if(params.containsKey("categorySecond.csid")){
				criteria.add(Restrictions.eq("categorySecond.csid", Integer.parseInt((String) params.get("categorySecond.csid")[1])));
				params.remove("categorySecond.csid");
			}
			if(params.containsKey("cid")){
				criteria.createAlias("categorySecond", "cs");
				criteria.createAlias("cs.category", "c");
				criteria.add(Restrictions.eq("c.cid", Integer.parseInt((String) params.get("cid")[1])));
				params.remove("cid");
			}
			return getPagingListWithParams(criteria, field, queryStr, page, rows, params);
		}
	}

	public int deleteProducts(String ids) {
		// TODO Auto-generated method stub
		String hql = "delete from Product p where p.pid in (" + ids + ")";
		Query query = getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	public void updateNumber() {
		// TODO Auto-generated method stub
		getCurrentSession().doWork(new Work() {
			
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				//String sql="update product set shopPrice=shopPrice+1 where shopPrice>0";
				String sql="update product set productNumber=?";
				java.sql.PreparedStatement pStatement=connection.prepareStatement(sql);
				for(int i=0;i<89;i++){
					pStatement.setString(1, UUIDUtil.getUUID());
					pStatement.addBatch();
				}
				pStatement.executeBatch();
			}
		});
	}

}
