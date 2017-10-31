package com.eleven.shop.service;

import java.util.List;
import java.util.Map;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.User;

public interface UserService{
	public User findByUsername(String userName);

	public void testUserDaoImple2();
	public User findByUsernameAndPassword(String username, String password);

	
	public User findByCode(String code);

	
	public List<User> findFuzzyByUserName(int pageNow, int pageSize, String userName);

	public List<User> findAll(int pageNow, int pageSzie);

	public User findOne(Integer integer);
   
	public void register(User user);

	public void update(User user);
	
	public PagingList<User> getUsers(String queryStr,String field,int page,int rows,Map<String, Object[]> params);

	public int deleteUser(String ids);

}
