package com.eleven.shop.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

import com.eleven.shop.bean.PagingList;

/**
 * 
 * @author Eleven Gan dao
 */
public interface BaseDao<T> {
	
	public void add(T object);

	public void delete(Class<T> clazz, Serializable id);

	public void delete(T object);

	public void update(T object);

	public void saveOrUpdate(T object);

	public T get(Class<T> clazz, Serializable id);
	
	public List<T> find(String hql);

	public List<T> find(String hql, Map<String, Object> params);
	
	public T findOne(String hql,Map<String, Object> params);

	public List<T> find(String hql, int pageNow, int pageSize);

	public List<T> find(String hql, Map<String, Object> params, int pageNow, int pageSize);

	public Integer count(String queryStr,String alias);

	public PagingList<T> getPagingList(Criteria criteria, int pageNow, int pageSize);
	
	public PagingList<T> getPagingListWhetherQuerystrNull(Criteria criteria,String field,String queryStr,int pageNow, int pageSize);
	
	public PagingList<T> getPagingListWithParams(Criteria criteria, String field, String queryStr, int pageNow,
			int pageSize, Map<String, Object[]> params);
}
