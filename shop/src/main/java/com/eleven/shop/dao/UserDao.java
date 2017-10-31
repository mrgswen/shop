package com.eleven.shop.dao;

import java.util.List;
import java.util.Map;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.User;
import com.eleven.shop.dao.BaseDao;

public interface UserDao extends BaseDao<User>{

	public User findByUsername(String userName);

	
	public User findByUsernameAndPassword(String username, String password);

	
	public User findByCode(String code);

	
	public List<User> findFuzzyByUserName(int pageNow, int pageSize, String userName);

	public List<User> findAll(int pageNow, int pageSzie);

	
	public User findOne(Integer id);
	
	public PagingList<User> getUsers(String queryStr,String field,int page,int rows,Map<String, Object[]> params);


	public int deleteUser(String ids);
}
