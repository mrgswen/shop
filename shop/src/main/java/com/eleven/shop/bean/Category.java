package com.eleven.shop.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  cid;
	@NotEmpty
	private String cname;
	@OrderBy(value="csid")
	@JsonIgnore
	//@Cascade(CascadeType.ALL)
	@OneToMany(fetch=FetchType.EAGER,mappedBy="category")
    private Set<SecondCategory> categorySeconds=new HashSet<SecondCategory>();
	
	public Set<SecondCategory> getCategorySeconds() {
		return categorySeconds;
	}

	public void setCategorySeconds(Set<SecondCategory> categorySeconds) {
		this.categorySeconds = categorySeconds;
	}

	public Integer  getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Category [cid=" + cid + ", cname=" + cname +   "]";
	}

	

}
