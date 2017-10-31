package com.eleven.shop.service.Impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.Admin;
import com.eleven.shop.bean.NaviTree;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.AdminDao;
import com.eleven.shop.dao.NaviTreeDao;
import com.eleven.shop.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;
    @Resource
    private NaviTreeDao naviTreeDao;
	public Admin findByNameAndPassword(String adminName, String password) {
		// TODO Auto-generated method stub
		return adminDao.findByNameAndPassword(adminName, password);
	}
	public List<NaviTree> geNaviTree(int parentId) {
		// TODO Auto-generated method stub
		return naviTreeDao.getNaviTree(parentId);
	}
	public PagingList<Admin> getAdmins(String queryStr ,int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		return adminDao.getAdmins(queryStr, pageNow, pageSize);
	}
	public void addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		 adminDao.add(admin);
	}
	public void updateAdmin(Admin admin) {
		// TODO Auto-generated method stub
		adminDao.update(admin);
	}
	public int deleteAdmins(String ids) {
		// TODO Auto-generated method stub
		return adminDao.deleteAdmins(ids);
	}
	public PagingList<Admin> getAdminsWithDateFrom(String queryStr,Date dateFrom, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		return adminDao.getAdminsWithDateFrom(queryStr, dateFrom, pageNow, pageSize);
	}
	public PagingList<Admin> getAdminsWithDateTo(String adminName, Date dateTo, int page, int rows) {
		// TODO Auto-generated method stub
		return adminDao.getAdminsWithDateTo(adminName, dateTo, page, rows);
	}
	public PagingList<Admin> getAdminsBetWeenDate(String adminName,Date dateFrom, Date dateTo, int page, int rows) {
		// TODO Auto-generated method stub
		return adminDao.getAdminsBetweenDate(adminName, dateFrom, dateTo, page, rows);
	}
	public Admin isAdminExist(String adminName) {
		// TODO Auto-generated method stub
		return adminDao.isAdminExist(adminName);
	}
    
}
