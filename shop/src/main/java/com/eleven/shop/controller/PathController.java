package com.eleven.shop.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eleven.shop.service.CategoryService;
import com.eleven.shop.service.ProductService;
import com.eleven.shop.util.LogUtil;
/**
 * 没有相关业务逻辑，只有单纯的页面跳转功能相关控制器
 * 如跳转到登录、注册、主页等
 * @author Eleven Gan
 *
 */
@Controller
public class PathController {
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProductService productService;
	@RequestMapping("/{pageName}")
   public String forwardPage(@PathVariable String pageName,Map<String, Object> map,HttpSession session,HttpServletResponse response) throws IOException{
		if(pageName.equals("index")){
			session.setAttribute("categories", categoryService.findAll());
			map.put("hotProducts", productService.findHot(10));
			LogUtil.info("test");
			map.put("newProducts", productService.findNew(10));
			LogUtil.info("test");
			
		}
	   return pageName;
   }
}
