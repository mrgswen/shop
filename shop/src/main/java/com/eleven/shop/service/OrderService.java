package com.eleven.shop.service;

import java.util.Map;

import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.PagingList;

public interface OrderService {
    /**
     * 保存订单
     * @param order
     */
	void save(Order order);
    /**
     * 分页获取用户的订单
     * @param pageNow
     * @param pageSize
     * @return
     */
	PagingList<Order> findOrdersByUserId(int pageNow, int pageSize,int userId);
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	Order findByOrderNumber(String orderNumber);
	/**
	 * 
	 * @param currOrder
	 */
	void update(Order currOrder);
	/**
	 * 
	 * @param oid
	 */
	void delete(int oid);
    public int deleteOrders(String ids);
    public PagingList<Order> getOrders(String queryStr,String field,int page,int rows,Map<String, Object[]> params);
	
}
