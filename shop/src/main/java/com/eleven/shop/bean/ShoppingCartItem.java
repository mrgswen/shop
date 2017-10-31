package com.eleven.shop.bean;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Product product;
    private Integer count;
    private double subTotal;
    private boolean isSelected;
    
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public double getSubTotal() {
		subTotal=count*product.getShopPrice();
		return subTotal;
	}
	@Override
	public String toString() {
		return "ShoppingCartItem [product=" + product + ", count=" + count + ", subTotal=" + subTotal + ", isSelected="
				+ isSelected + "]";
	}
	
	public int hashCode(){
		final int prime=31;
		int result=1;
		result =prime*31+product.getPid();
		return result;
	}
	
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj==null)
			return false;
		if(getClass()!=obj.getClass())
			return false;
		ShoppingCartItem item=(ShoppingCartItem) obj;
		if(product==null){
			if(item.product!=null)
				return false;
		}else if(!(product.getPid()==item.product.getPid())){
			return false;
		}
		return true;
	}
    
}
