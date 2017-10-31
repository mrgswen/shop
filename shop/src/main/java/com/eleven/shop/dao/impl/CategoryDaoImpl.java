package com.eleven.shop.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.CategoryDao;
@Repository
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao{

	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		String hql ="from Category c ";
		Query query= getCurrentSession().createQuery(hql);
		System.out.println(query.list()+"------------------------------");
		return query.list();
	}

	public PagingList<Category> getCategorys(String queryStr, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(Category.class);
		criteria.setFetchMode("categorySeconds", FetchMode.SELECT);
		String field ="cname";
		return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);
		
	}

	public int deleteCategry(String ids) {
		// TODO Auto-generated method stub
		String hql="delete from Category c where c.cid in ("+ids+")";
		Query query=getCurrentSession().createQuery(hql);
	    int affected=query.executeUpdate();
	    return affected;
	    
	}

}
