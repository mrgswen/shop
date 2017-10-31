package com.eleven.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.eleven.shop.bean.NaviTree;
import com.eleven.shop.dao.NaviTreeDao;
@Repository

public class NaviTreeDaoImpl extends BaseDaoImpl<NaviTree> implements NaviTreeDao {

	public List<NaviTree> getNaviTree(int parentId) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("parentId", parentId);
		String hql="from NaviTree n where n.parentId=:parentId";
		return find(hql, params);
	}
}
