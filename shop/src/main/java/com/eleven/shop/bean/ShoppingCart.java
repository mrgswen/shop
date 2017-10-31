package com.eleven.shop.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double total;
	private boolean selectAll;
	private int selectedCount;
	
	private List<ShoppingCartItem> items=new ArrayList<ShoppingCartItem>();
	/**
	 * 添加购物项到购物车
	 * @param item
	 */
	public void addItem(ShoppingCartItem item){
		if(items.contains(item)){
			
		}
	}
	public double getTotal() {
		double result=0;
		for(ShoppingCartItem item:items){
			result+=item.getSubTotal();
		}
		return result;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public boolean isSelectAll() {
		return selectAll;
	}
	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	public int getSelectedCount() {
		int selected=0;
		for(ShoppingCartItem cartItem:items){
			if(cartItem.getIsSelected()){
				selected+=1;
			}
		}
		return selected;
	}
	public void setSelectedCount(int selectedCount) {
		this.selectedCount = selectedCount;
	}
	public List<ShoppingCartItem> getItems() {
		return items;
	}
	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}
    
}
