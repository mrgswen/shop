package com.eleven.shop.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.Admin;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.AdminDao;
import com.eleven.shop.service.AdminService;
@SuppressWarnings("unchecked")
@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao {

	public Admin findByNameAndPassword(String adminName, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("adminName", adminName);
		params.put("password", password);
		String hql="from Admin a where a.adminName =:adminName and a.password=:password";
		Admin admin=(Admin) findOne(hql, params);
		return admin;
	}
    
	public List<Admin> getAdmins(String queryStr,Date dateFrom,Date dateTo,int pageNow,int pageSize){
		List<Admin> admins=new ArrayList<Admin>();
//		String hql="";
//		if(queryStr==""||queryStr==null)
//		 hql="from Admin";
//		else{
//			hql="from Admin a"+queryStr;
//		}
//		admins=find(hql, pageNow, pageSize);
		return admins;
	}

	public int getAdminNumbers(String queryString) {
		// TODO Auto-generated method stub
		return count(queryString,"a");
	}

	public int deleteAdmins(String ids) {
		// TODO Auto-generated method stub
		String hql ="delete from Admin a where a.adminId in ("+ids+")";
		Query query =getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	public List<Admin> getAdminss() {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(Admin.class);
		//criteria.add(Restrictions.)
		//criteria.add(Restrictions.));
		return criteria.list();
	}

	public PagingList<Admin> getAdmins(String queryStr, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(Admin.class);
		String field="adminName";
	    return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);
	}

	public PagingList<Admin> getAdminsWithDateFrom(String queryStr, Date dateFrom, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(Admin.class);
		criteria.add(Restrictions.ge("createdTime", dateFrom));
		String field="adminName";
	    return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);	
	}

	public PagingList<Admin> getAdminsWithDateTo(String queryStr, Date dateTo, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(Admin.class);
		criteria.add(Restrictions.le("createdTime", dateTo));
		String field="adminName";
	    return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);
	}

	public PagingList<Admin> getAdminsBetweenDate(String queryStr, Date dateFrom, Date dateTo, int pageNow, int pageSize) {
		// TODO Auto-generated method stub
		Criteria criteria=getCurrentSession().createCriteria(Admin.class);
		criteria.add(Restrictions.ge("createdTime", dateFrom));
		criteria.add(Restrictions.le("createdTime", dateTo));
		String field="adminName";
	    return getPagingListWhetherQuerystrNull(criteria, field, queryStr, pageNow, pageSize);
	}

	public Admin isAdminExist(String adminName) {
		// TODO Auto-generated method stub
		String hql="from Admin a where a.adminName=?";
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter(0, adminName);
		List<Admin> admins= query.list();
		if(admins!=null&&admins.size()>0)
		return admins.get(0);
		else return null;
	}

	
}
