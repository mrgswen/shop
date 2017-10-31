package com.eleven.shop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.SecondCategory;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.service.CategoryService;
import com.eleven.shop.util.LogUtil;

@Controller
public class CategoryController {
	@Resource
	private CategoryService categoryService;

	@RequestMapping("/admin/getCategorys")
	@ResponseBody
	public PagingList<Category> getCategorys(String cname, int page, int rows) {
		// PagingList<Category> pagingList = new PagingList<Category>();
		// categoryService.getCategorys(cname, page, rows);
		return categoryService.getCategorys(cname, page, rows);
	}

	@RequestMapping("/admin/addCategory")
	@ResponseBody
	public RespData addCategory(Category category) {
		RespData respData = new RespData();
		LogUtil.info(category);
		categoryService.addCategory(category);
			respData.status = 1;
			respData.msg = category.getCname() + "添加成功";
		//} else {
		//	respData.status = 2;
			//respData.msg = "添加失败";
	//	}
	//;
		return respData;
	}

	@RequestMapping("/admin/updateCategory")
	@ResponseBody
	public RespData updateCategory(Category category) {
		RespData respData = new RespData();
		categoryService.updateCategory(category);
		respData.status = 1;
		respData.msg = "修改成功";
		return respData;
	}

	@RequestMapping("/admin/deleteCategory")
	@ResponseBody
	public RespData deleteCategory(String ids) {
		RespData respData = new RespData();
		int affected = categoryService.deleteCategory(ids);
		if (affected != 0) {
			respData.content = affected;
			respData.status = 1;
			respData.msg = affected + "个分类被删除";
		} else {
			respData.status = 2;
			respData.msg = "删除失败";
		}
		return respData;
	}

	@RequestMapping("/admin/getAllCategorys")
	@ResponseBody
	public List<Category> getAllCategorys() {
		List<Category> categories = categoryService.findAll();
		return categories;
	}

	@RequestMapping("/admin/getSecondCategorys")
	@ResponseBody
	public PagingList<SecondCategory> getSecondCategorys(String csName, int page, int rows) {
		// PagingList<Category> pagingList = new PagingList<Category>();
		// categoryService.getCategorys(cname, page, rows);
		return categoryService.getSecondCategorys(csName, page, rows);
	}

	@RequestMapping("/admin/getAllSecondCategorys")
	@ResponseBody
	public List<SecondCategory> getAllSecondCategorys() {
		return categoryService.getAllSecondCategory();
	}

	@RequestMapping("/admin/getSecondCategorysWithCid")
	@ResponseBody
	public List<SecondCategory> getSecondCategorysWithCid(Integer cid) {
		return categoryService.getSecondCategoryWithCid(cid);
	}

	@RequestMapping("/admin/addSecondCategory")
	@ResponseBody
	public RespData addSecondCategory(SecondCategory categorySecond) {
		RespData respData = new RespData();
		LogUtil.info("ceshiyixia" + categorySecond.getCategory());
		categoryService.addSecondCategory(categorySecond);
			respData.status = 1;
			respData.msg = categorySecond.getCsName() + "添加成功";
		//} else {
		//	respData.status = 2;
			//respData.msg = "添加失败";
	//	}
		//;
		return respData;
	}

	@RequestMapping("/admin/updateSecondCategory")
	@ResponseBody
	public RespData updateSecondCategory(SecondCategory categorySecond) {
		RespData respData = new RespData();
		categoryService.updateSecondCategory(categorySecond);
		respData.status = 1;
		respData.msg = "修改成功";
		return respData;
	}

	@RequestMapping("/admin/deleteSecondCategory")
	@ResponseBody
	public RespData deleteSecondCategory(String ids) {
		RespData respData = new RespData();
		int affected = categoryService.deleteSecondCategory(ids);
		if (affected != 0) {
			respData.content = affected;
			respData.status = 1;
			respData.msg = affected + "个分类被删除";
		} else {
			respData.status = 2;
			respData.msg = "删除失败";
		}
		return respData;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
