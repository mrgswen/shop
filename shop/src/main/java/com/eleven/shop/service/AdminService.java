package com.eleven.shop.service;

import java.util.Date;
import java.util.List;

import com.eleven.shop.bean.Admin;
import com.eleven.shop.bean.NaviTree;
import com.eleven.shop.bean.PagingList;

public interface AdminService {
	public Admin findByNameAndPassword(String adminName, String password);
    public List<NaviTree> geNaviTree(int parentId);
    
    public PagingList<Admin> getAdmins(String queryStr,int pageNow,int pageSize);
    
    public PagingList<Admin> getAdminsWithDateFrom(String queryStr,Date dateFrom,int pageNow,int pageSize);
    
    public void addAdmin(Admin admin);
    
    public void updateAdmin(Admin admin);
    
    public int deleteAdmins(String ids);
	public PagingList<Admin> getAdminsWithDateTo(String adminName, Date dateTo, int page, int rows);
	public PagingList<Admin> getAdminsBetWeenDate(String adminName, Date dateFrom,Date dateTo, int page, int rows);
	public Admin isAdminExist(String adminName);
}
