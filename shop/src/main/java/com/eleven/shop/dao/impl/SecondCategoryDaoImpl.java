package com.eleven.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.SecondCategory;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.SecondCategoryDao;
@Repository
public class SecondCategoryDaoImpl extends BaseDaoImpl<SecondCategory> implements SecondCategoryDao{

	public PagingList<SecondCategory> getSecondCategorys(String queryStr, int page, int rows) {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(SecondCategory.class);
		String field ="csName";
		return getPagingListWhetherQuerystrNull(criteria, field, queryStr, page, rows);
	}
    
	public int deleteSecondCategry(String ids) {
		// TODO Auto-generated method stub
		String hql="delete from CategorySecond cs where cs.csid in ("+ids+")";
		Query query=getCurrentSession().createQuery(hql);
	    int affected=query.executeUpdate();
	    return affected;
	    
	}

	public List<SecondCategory> getAllSecondCategory() {
		// TODO Auto-generated method stub
		String hql="from SecondCategory";
		return find(hql);
	}

	public List<SecondCategory> getSecondCategoryWithCid(Integer cid) {
		// TODO Auto-generated method stub
		String hql="from  SecondCategory cs where cs.category.cid=:cid";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("cid", cid);
		return find(hql, params);
		
	}
}
