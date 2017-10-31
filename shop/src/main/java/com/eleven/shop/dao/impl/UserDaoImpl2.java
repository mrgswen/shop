package com.eleven.shop.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.User;
import com.eleven.shop.dao.UserDao;
@Repository("userDaoImpl2")
public class UserDaoImpl2 extends BaseDaoImpl<User> implements UserDao {

	public User findByUsername(String userName) {
		// TODO Auto-generated method stub
		User user=new User();
		user.setAddress("sss");
		return user;
	}

	public User findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findFuzzyByUserName(int pageNow, int pageSize, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findAll(int pageNow, int pageSzie) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PagingList<User> getUsers(String queryStr, String field, int page, int rows, Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteUser(String ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	

}
