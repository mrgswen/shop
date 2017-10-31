package com.eleven.shop.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adminuser")
public class Admin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer adminId;
	@Column(unique = true)
	private String adminName;
	private String password;
	private byte auth;//1为一级管理员，拥有所有权限，2为2级管理员拥有指定权限
	@Column(columnDefinition="TIMESTAMP  DEFAULT CURRENT_TIMESTAMP")
	private Date createdTime;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminName=" + adminName + ", password=" + password + ", auth=" + auth
				+ ", createdTime=" + createdTime + "]";
	}

	public byte getAuth() {
		return auth;
	}

	public void setAuth(byte auth) {
		this.auth = auth;
	}

	
    
}
