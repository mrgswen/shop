package com.eleven.shop.service.Impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.User;
import com.eleven.shop.dao.UserDao;
import com.eleven.shop.service.UserService;
import com.eleven.shop.util.MailUtil;
import com.eleven.shop.util.UUIDUtil;


@Service("userService")

public class UserServiceImpl implements UserService {
	@Resource(name="userDaoImpl")
    private UserDao userDao; 
	@Resource(name="userDaoImpl2")
	private UserDao userDao2;
	public void testUserDaoImple2(){
		System.out.println(userDao2.findByUsername("123").getAddress());
	}
	public User findByUsername(String userName) {
		// TODO Auto-generated method stub
		return (User) userDao.findByUsername(userName);
	}

	public User findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return (User) userDao.findByUsernameAndPassword(username, password);
	}

	public User findByCode(String code) {
		// TODO Auto-generated method stub
		return (User) userDao.findByCode(code);
	}

	public List<User> findFuzzyByUserName(int pageNow, int pageSize, String userName) {
		// TODO Auto-generated method stub
		return (List<User>) userDao.findFuzzyByUserName(pageNow, pageSize, userName);
	}

	public List<User> findAll(int pageNow, int pageSzie) {
		// TODO Auto-generated method stub
		return (List<User>) userDao.findAll(pageNow, pageSzie);
	}

	public User findOne(Integer id) {
		// TODO Auto-generated method stub
		return (User) userDao.findOne(id);
	}

	public void register(User user) {
		// TODO Auto-generated method stub
		user.setState(0);
		user.setCode(UUIDUtil.getUUID()+UUIDUtil.getUUID());
		System.out.println(user);
		userDao.add(user);
		try {
			MailUtil.SendMail(user.getEmail(), user.getCode());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(User user) {
		// TODO Auto-generated method stub
		userDao.update(user);
	}
    
	public PagingList<User> getUsers(String queryStr,String field,int page,int rows,Map<String, Object[]> params){
		return userDao.getUsers(queryStr, field, page, rows,params);
	}

	public int deleteUser(String ids) {
		// TODO Auto-generated method stub
		return userDao.deleteUser(ids);
	}

	
}
