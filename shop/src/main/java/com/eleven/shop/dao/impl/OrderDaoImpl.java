package com.eleven.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.OrderDao;
@Repository

public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

	public int getOrderCountByUid(Integer userId) {
		// TODO Auto-generated method stub
		String hql="select count(*) from Order o where o.user.id=? and o.status=0 ";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter(0, userId);
		int  orderCount=((Long)query.uniqueResult()).intValue();
		return orderCount;
	}

	public List<Order> getOrdersByUserId(Integer userId, int pageNow, int pageSize) {
		// TODO Auto-generated method stub 
		String hql ="from Order o where o.user.id=:userId and o.status=0 order by o.createdTime desc";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		List<Order> orders=find(hql, params, pageNow, pageSize);
		return orders;
	}

	public Order findByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		String hql="from Order o where o.orderNumber=?";
		Query query=getCurrentSession().createQuery(hql);
		query.setParameter(0, orderNumber);
		List<Order> orders=query.list();
		if(orders!=null&&orders.size()>0){
			return orders.get(0);
		}else{
			return null;
		}
		
	}

	public PagingList<Order> getOrders(String queryStr, String field, int page, int rows,
			Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		Criteria criteria = getCurrentSession().createCriteria(Order.class);
		criteria.addOrder(org.hibernate.criterion.Order.desc("createdTime"));
		criteria.add(Restrictions.eq("status", (byte)0));
		if (params.isEmpty()) {
			return getPagingListWhetherQuerystrNull(criteria, field, queryStr, page, rows);
		} else {
			if(params.containsKey("userName")){
				criteria.createAlias("user", "u");
				criteria.add(Restrictions.ilike("u.userName",  (String) params.get("userName")[1],MatchMode.ANYWHERE));
				params.remove("userName");
			}
			return getPagingListWithParams(criteria, field, queryStr, page, rows, params);
		}
	}

	public int deleteOrders(String ids) {
		// TODO Auto-generated method stub
		String hql="update Order o set o.status=1 where o.oid in ("+ids+")";
		Query query=getCurrentSession().createQuery(hql);
		return query.executeUpdate();
	}

	//public void delete(int oid) {
		// TODO Auto-generated method stub
		
	//}

	

}
