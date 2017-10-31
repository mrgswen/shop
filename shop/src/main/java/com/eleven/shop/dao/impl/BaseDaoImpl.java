package com.eleven.shop.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.BaseDao;
import com.eleven.shop.util.LogUtil;
import com.eleven.shop.util.StringUtil;

@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource
	private SessionFactory sessionFactory;

	protected Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
		// this.sessionFactory.get
	}

	public void add(T object) {
		// TODO Auto-generated method stub

		 getCurrentSession().save(object);
	}

	public void delete(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		getCurrentSession().delete(this.get(clazz, id));
	}

	public void delete(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().delete(object);
	}

	public void update(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().update(object);
	}

	public void saveOrUpdate(T object) {
		// TODO Auto-generated method stub
		getCurrentSession().saveOrUpdate(object);
	}

	public T get(Class<T> clazz, Serializable id) {
		// TODO Auto-generated method stub
		return (T) getCurrentSession().get(clazz, id);
	}

	public List<T> find(String hql) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);

		return query.list();
	}

	public List<T> find(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		setParams(query, params);
		return query.list();
	}

	public List<T> find(String hql, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		return query.setFirstResult(((pageNow - 1) * pageSize)).setMaxResults(pageSize).list();
	}

	public List<T> find(String hql, Map<String, Object> params, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}
		List<T> lists = query.setFirstResult(((pageNow - 1) * pageSize)).setMaxResults(pageSize).list();
		LogUtil.info(lists);
		return query.setFirstResult(((pageNow - 1) * pageSize)).setMaxResults(pageSize).list();
	}

	// public Integer count(String hql, String id) {
	// // TODO Auto-generated method stub
	// Query query = getCurrentSession().createQuery(hql);
	// query.setParameter(0, id);
	// return (Integer) query.uniqueResult();
	// }

	


	public T findOne(String hql, Map<String, Object> params) {
		// TODO Auto-generated method stub
		Query query = getCurrentSession().createQuery(hql);
		setParams(query, params);
		return (T) query.uniqueResult();
	}

	private void setParams(Query query, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
	}

	

	private String getTableName() {
		Class<T> clz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass()))
				.getActualTypeArguments()[0]));
		return clz.getSimpleName();
	}

	public Integer count(String queryStr, String alias) {
		// TODO Auto-generated method stub
		String tableName = getTableName();
		String hql = "";
		if (queryStr == null || queryStr == "")
			hql = "select count(*) from " + tableName;
		else {
			hql = "select count(*) from " + tableName + " " + alias + queryStr;
		}
		Query query = getCurrentSession().createQuery(hql);

		return ((Long) query.uniqueResult()).intValue();
	}

	public PagingList<T> getPagingList(Criteria criteria, int pageNow, int pageSize) {
		PagingList<T> pages = new PagingList<T>();
		List<T> totalRecords = criteria.list();
		int totalSize = 0;
		if (totalRecords != null)
			totalSize=totalRecords.size();
		criteria.setFirstResult((pageNow - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<T> pageData = criteria.list();
		for(T dt:pageData){
			System.out.println(dt);
		}
		pages.setPagingList(pageNow, pageSize, totalSize, pageData);
		return pages;
	}
	
	public PagingList<T> getPagingListWhetherQuerystrNull(Criteria criteria,String field,String queryStr,int pageNow, int pageSize){
		
		if(StringUtil.isNullOrBlank(queryStr)){
			LogUtil.info("basedao");
			return getPagingList(criteria, pageNow, pageSize);
		}else{
			//if(field.contains("id")){
			//criteria.add(Restrictions.eq(field, Integer.parseInt(queryStr)));
			//}else{
				criteria.add(Restrictions.ilike(field, queryStr,MatchMode.ANYWHERE));
			//}
			//criteria.add(Restrictions.)
			//criteria.
			return getPagingList(criteria, pageNow, pageSize);
		}
	}
	
	public PagingList<T> getPagingListWithParams(Criteria criteria, String field, String queryStr, int pageNow,
			int pageSize, Map<String, Object[]> params) {
		for (String fieldName : params.keySet()) {
			Object[] operatorAndValue = params.get(fieldName);
			if (fieldName == "createdTime2") {
				criteria.add(Restrictions.le("createdTime", operatorAndValue[1]));
			} else {
				if (operatorAndValue[0] == Operators.EQ.operator) {
					criteria.add(Restrictions.eq(fieldName, operatorAndValue[1]));
				} else if (operatorAndValue[0] == Operators.LE.operator) {
					criteria.add(Restrictions.le(fieldName, operatorAndValue[1]));
				} else if (operatorAndValue[0] == Operators.GE.operator) {
					criteria.add(Restrictions.ge(fieldName, operatorAndValue[1]));
				}
			}
		}
		return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);
	}
	
}
