package com.eleven.shop.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.eleven.shop.util.LogUtil;

public class Cart {
	private Map<Integer, CartItem> map = new HashMap<Integer, CartItem>();
	private double total;
	private boolean selectAll;
	private int selectedCount;

	public int getSelectedCount() {
		return selectedCount;
	}

	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	// 网购物车中添加某一项商品ﳵ
	public void addItem(CartItem item) {
		int pid = item.getProduct().getPid();
		if (map.containsKey(pid)) {
			CartItem oldItem = (CartItem) map.get(pid);
			oldItem.setCount(item.getCount() + oldItem.getCount());
			if (oldItem.getIsSelected()) {// 如果购物车已经存在该商品并且被选中，则加上选中的商品数量和价钱
				total += item.getSubTotal();
				selectedCount += item.getCount();
			}
		} else {
			map.put(pid, item);
			setSelectAll(false);// 新加入购物车的商品默认没有选中，则 全选按钮也对应设为未选中
		}
		// this.total += item.getSubTotal();
	}

	// 删除购物车的某一项商品
	public Map<String, String> deleteItem(Integer pid) {
		Map<String, String> newDatas = new HashMap<String, String>();
      //Iterator<Integer> iterator=  map.keySet().iterator();
      //iterator.remove();
		CartItem deleteItem = (CartItem) map.remove(pid);
		if (deleteItem != null) {
			if (deleteItem.getIsSelected()) {// 删除选中状态的商品，则修改商品总额和选中商品数量
				total -= deleteItem.getSubTotal();
				selectedCount -= deleteItem.getCount();
			}
		}
		boolean emptyOrNot = map.isEmpty();
		newDatas.put("emptyOrNot", Boolean.valueOf(emptyOrNot).toString());
		newDatas.put("total", Double.valueOf(total).toString());
		newDatas.put("selectedCount", Integer.valueOf(selectedCount).toString());
		return newDatas;

	}

	// ﳵ清空购物车
	public void clearCart() throws Exception {
		if (map.isEmpty()) {
			throw new Exception("购物车为空");
		}
		map.clear();
		total = 0;
		selectedCount = 0;
	}

	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	// 修改购物项里商品的数量
	public Map<String, String> changCartItemCount(int pid, int newCount) throws Exception {
		CartItem item = map.get(pid);
		if (item == null) {
			throw new Exception("购物车中没有该商品");
		}
		double oldSubtotal = item.getSubTotal();
		int oldSubCount = item.getCount();
		item.setCount(newCount);
		double newSubTotal = item.getSubTotal();
		if (!item.getIsSelected()) {// 改变数量之前的状态为未选定，则设为选定
			item.setIsSelected(true);
			this.total = total + newSubTotal;
			this.selectedCount += item.getCount();
			boolean allHaveSelected = judgeAllhaveSelected();// 新增一个选定的商品后，判断全选状态
			setSelectAll(allHaveSelected);
		} else {
			this.total = total + newSubTotal - oldSubtotal;
			this.setSelectedCount(selectedCount + newCount - oldSubCount);
		}
		Map<String, String> changeDatas = new HashMap<String, String>();
		changeDatas.put("subTotal", Double.valueOf(newSubTotal).toString());
		LogUtil.info("cart sysout" + Double.valueOf(newSubTotal).toString());
		changeDatas.put("total", Double.valueOf(total).toString());
		changeDatas.put("subCount", item.getCount().toString());
		changeDatas.put("selectedCount", Integer.valueOf(selectedCount).toString());
		return changeDatas;
		// LogUtil.info(item.getSubTotal());
	}

	public Map<String, String> changeSeletedStatus(int pid, boolean isSelected) throws Exception {
		CartItem item;
		if (map.containsKey(pid)) {
			item = map.get(pid);
			item.setIsSelected(isSelected);
		} else {
			throw (new Exception("购物车中没有该商品"));
		}
		if (isSelected && item != null) {
			total += item.getSubTotal();
			selectedCount += item.getCount();
			boolean allHaveSelected = judgeAllhaveSelected();// 判断全选状态
			setSelectAll(allHaveSelected);
		} else if (!isSelected && item != null) {
			total -= item.getSubTotal();
			selectedCount -= item.getCount();
			setSelectAll(false);
		}

		Map<String, String> newDatas = new HashMap<String, String>();
		newDatas.put("total", Double.valueOf(total).toString());
		newDatas.put("selectedCount", Integer.valueOf(selectedCount).toString());
		return newDatas;
	}

	public Map<String, String> selectAll(boolean selectAll) {
		setSelectAll(selectAll);
		total = 0.0;
		selectedCount = 0;
		Collection<CartItem> items = map.values();
		if (selectAll) {// 全选
			for (CartItem item : items) {
				total += item.getSubTotal();
				selectedCount += item.getCount();
				item.setIsSelected(selectAll);// 设置各购物项选中
			}
		} else {
			for (CartItem item : items) {
				item.setIsSelected(selectAll);// 设置各购物项未选中
			}
		}
		Map<String, String> newDatas = new HashMap<String, String>();
		newDatas.put("total", Double.valueOf(total).toString());
		newDatas.put("selectedCount", Integer.valueOf(selectedCount).toString());
		return newDatas;
	}

	public boolean judgeAllhaveSelected() {
		boolean flag = true;
		Collection<CartItem> items = map.values();
		for (CartItem item : items) {
			if (!item.getIsSelected())// 如果有一个未选中,则未全部选中
				flag = false;
		}
		return flag;
	}
}
