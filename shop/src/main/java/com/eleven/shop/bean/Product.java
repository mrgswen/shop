package com.eleven.shop.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//@Id
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pid;

	private String pName;
	private Double marketPrice;
	private Double shopPrice;
	private String productDescribe;
	private String image;
	private Integer isHot;
	private String productNumber;
	
	@Column(columnDefinition="TIMESTAMP  DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;
	@JoinColumn(name = "csid")
	@ManyToOne
	private SecondCategory categorySecond;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Double getShopPrice() {
		return shopPrice;
	}
	public void setShopPrice(Double shopPrice) {
		this.shopPrice = shopPrice;
	}
	public String getProductDescribe() {
		return productDescribe;
	}
	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public SecondCategory getCategorySecond() {
		return categorySecond;
	}
	public void setCategorySecond(SecondCategory categorySecond) {
		this.categorySecond = categorySecond;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pName=" + pName + ", marketPrice=" + marketPrice + ", shopPrice=" + shopPrice
				+ ", productDescribe=" + productDescribe + ", image=" + image + ", isHot=" + isHot + ", uploadDate="
				+ createdTime + ", categorySecond=" + categorySecond + "]";
	}

	

}
