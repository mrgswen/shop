package com.eleven.shop.dao;

import java.util.List;
import java.util.Map;

import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.PagingList;

public interface OrderDao extends BaseDao<Order>{
    /**
     * 获取用户订单数目
     * @param userId
     * @return
     */
	int getOrderCountByUid(Integer userId);
    /**
     * 分页获取用户的订单
     * @param userId
     * @param pageNow
     * @param pageSize
     * @return
     */
	public List<Order> getOrdersByUserId(Integer userId, int pageNow, int pageSize);
	public Order findByOrderNumber(String orderNumber);
	
	public PagingList<Order> getOrders(String queryStr,String field,int page,int rows ,Map<String, Object[]> params);
	
	public int deleteOrders(String ids);
	//void delete(int oid);
}
