package com.eleven.shop.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.User;
import com.eleven.shop.dao.UserDao;

@SuppressWarnings("all")
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public User findByUsername(String userName) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.userName=?";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, userName);
		return (User) query.uniqueResult();
	}

	public User findByUsernameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.userName=? and u.password=? and state=1";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, userName);
		query.setParameter(1, password);
		return (User) query.uniqueResult();
	}

	public User findByCode(String code) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.code=?";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, code);
		return (User) query.uniqueResult();
	}

	public List<User> findAll(int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from User";
		return find(hql, pageNow, pageSize);
	}

	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.id = ?";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter(0, id);
		return (User) query.uniqueResult();
	}

	public List<User> findFuzzyByUserName(int pageNow, int pageSize, String userName) {
		// TODO Auto-generated method stub
		String hql = "select * from User u where u.userName like :userName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("userName", "%" + userName + "%");
		return query.list();
	}

	public PagingList<User> getUsers(String queryStr, String field, int page, int rows, Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		if (params.isEmpty()) {
			return getPagingListWhetherQuerystrNull(criteria, field, queryStr, page, rows);
		} else {
			return getPagingListWithParams(criteria, field, queryStr, page, rows, params);
		}
	}

	public int deleteUser(String ids) {
		// TODO Auto-generated method stub
		String[] idStrings = ids.split(",");
		for (String id : idStrings) {
			id = "\'" + id + "\'";
		}
		String hql = "delete from User  where id in (" + ids + ")";
		Query query = getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	
}
