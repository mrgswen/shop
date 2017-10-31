package com.eleven.shop.dao;

import java.util.List;

import com.eleven.shop.bean.NaviTree;

public interface NaviTreeDao {
	 public List<NaviTree> getNaviTree(int parentId);
}
