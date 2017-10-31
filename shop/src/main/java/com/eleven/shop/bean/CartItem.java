package com.eleven.shop.bean;

public class CartItem {
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
		return "CartItem [product=" + product + ", count=" + count + ", subTotal=" + subTotal + ", isSelected="
				+ isSelected + "]";
	}
	
    
}
