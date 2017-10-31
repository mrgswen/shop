package com.eleven.shop.service.Impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.dao.OrderDao;
import com.eleven.shop.service.OrderService;
import com.eleven.shop.util.LogUtil;

@Service

public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderDao orderDao;

	public void save(Order order) {
		// TODO Auto-generated method stub
        orderDao.add(order);
	}

	public PagingList<Order> findOrdersByUserId(int pageNow, int pageSize,int userId) {
		// TODO Auto-generated method stub
		PagingList<Order> pages=new PagingList<Order>();
		int totalSize=orderDao.getOrderCountByUid(userId);
		List<Order> orders=orderDao.getOrdersByUserId(userId,pageNow,pageSize);
		LogUtil.info(orders);
		pages.setPagingList(pageNow, pageSize, totalSize, orders);
		return pages;
	}

	public Order findByOrderNumber(String orderNumber) {
		// TODO Auto-generated method stub
		return orderDao.findByOrderNumber(orderNumber);
	}

	public void update(Order currOrder) {
		// TODO Auto-generated method stub
		orderDao.update(currOrder);
	}

	public void delete(int oid) {
		// TODO Auto-generated method stub
		orderDao.deleteOrders(oid+"");
	}

	public int deleteOrders(String ids) {
		// TODO Auto-generated method stub
		return orderDao.deleteOrders(ids);
	}

	public PagingList<Order> getOrders(String queryStr, String field, int page, int rows, Map<String, Object[]> params) {
		// TODO Auto-generated method stub
		return orderDao.getOrders(queryStr, field, page, rows, params);
	}


}
