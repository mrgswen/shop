package com.eleven.shop.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 导航栏树形菜单异步加载所需的bean
 * @author shangwengan
 *
 */
@Table
@Entity
public class NaviTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//节点id
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false)
	private String text;
	@Column(nullable=false)
	private String state;
	
	private String iconCls;
	@Column(nullable=false)
	private String url;
	
	@Column(nullable=false)
	private Integer parentId;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "NaviTree [id=" + id + ", text=" + text + ", state=" + state + ", iconCls=" + iconCls + ", url=" + url
				+ "]";
	}
	
	

}
