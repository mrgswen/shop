package com.eleven.shop.dao;

import java.util.Date;

import com.eleven.shop.bean.Admin;
import com.eleven.shop.bean.PagingList;

public interface AdminDao extends BaseDao<Admin> {
   public Admin findByNameAndPassword(String adminName,String password);
   
   public PagingList<Admin> getAdmins(String queryStr,int pageNow,int pageSize);
 
   public PagingList<Admin> getAdminsWithDateFrom(String queryStr,Date dateFrom,int pageNow,int pageSize);
  
   public PagingList<Admin> getAdminsWithDateTo(String queryStr,Date dateTo,int pageNow,int pageSize);
  
   public PagingList<Admin> getAdminsBetweenDate(String queryStr,Date dateFrom,Date dateTo,int pageNow,int pageSize);
   
   public int getAdminNumbers(String queryStr);
   
   public int deleteAdmins(String ids);
   
   public Admin isAdminExist(String adminName);
   
}
