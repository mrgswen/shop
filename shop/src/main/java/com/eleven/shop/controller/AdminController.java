package com.eleven.shop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Admin;
import com.eleven.shop.bean.Category;
import com.eleven.shop.bean.SecondCategory;
import com.eleven.shop.bean.NaviTree;
import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.Order;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.bean.User;
import com.eleven.shop.service.AdminService;
import com.eleven.shop.service.CategoryService;
import com.eleven.shop.service.OrderService;
import com.eleven.shop.service.ProductService;
import com.eleven.shop.service.UserService;
import com.eleven.shop.util.LogUtil;
import com.eleven.shop.util.StringUtil;
import com.eleven.shop.util.TokenUtil;
import com.eleven.shop.util.UUIDUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource
	private AdminService adminService;
	
	@RequestMapping("/checkAdmin")
	@ResponseBody
	public RespData checkAdmin(String adminName, String password, HttpSession session) {
		RespData respData = new RespData();
		Admin admin = adminService.findByNameAndPassword(adminName, password);
		if (admin != null) {
			session.setAttribute("admin", admin);
			session.setAttribute("token", TokenUtil.getToken());
			respData.status = 1;
		} else {
			respData.status = 2;
			respData.msg = "用户名或者密码不正确,请重新输入!";
		}
		return respData;
	}

	@RequestMapping("/adminLogout")
	public String adminLogout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		session.removeAttribute("admin");
		session.removeAttribute("token");
		session.invalidate();
		return "redirect:/adminLogin";
	}

	@RequestMapping("/getNaviTree")
	@ResponseBody
	public List<NaviTree> getNaviTree(Integer id) {
		LogUtil.info(id);
		if (id == null)
			id = 0;
		List<NaviTree> naviTrees = adminService.geNaviTree(id);
		LogUtil.info(naviTrees);
		return naviTrees;
	}

	@RequestMapping("/getAdmins")
	@ResponseBody
	public PagingList<Admin> getAdmins(String adminName, int page, int rows, Date dateFrom, Date dateTo) {
		// String dateFrom = request.getParameter("dateFrom");
		// String dateTo = request.getParameter("dateTo");
		LogUtil.info("queryStr" + adminName + "---" + "from" + dateFrom + "to" + dateTo);
		// if (queryStr != null && queryStr != "") {
		// queryStr = " where a.adminName like " + "'%" + queryStr + "%'";
		// }
		if (dateFrom == null && dateTo == null) {
			return adminService.getAdmins(adminName, page, rows);
		} else if (dateFrom != null && dateTo == null) {
			return adminService.getAdminsWithDateFrom(adminName, dateFrom, page, rows);
		} else if (dateTo != null && dateFrom == null) {
			return adminService.getAdminsWithDateTo(adminName, dateTo, page, rows);
		} else {
			return adminService.getAdminsBetWeenDate(adminName, dateFrom, dateTo, page, rows);
		}
	}

	@RequestMapping("/addAdmin")
	@ResponseBody
	public RespData addAdmin(Admin admin) {
		RespData respData = new RespData();
		LogUtil.info("admin"+admin);
		Admin existAdmin=isAdminExist(admin.getAdminName());	
		if(existAdmin==null){
			adminService.addAdmin(admin);
			respData.status = 1;
			respData.msg = "添加成功";
		}else {
			respData.status = 2;
		    respData.msg = "账户名已经存在，请重新输入";
		}
		return respData;
	}

	@RequestMapping("/updateAdmin")
	@ResponseBody
	public RespData updateAdmin(Admin admin) {
		LogUtil.info("updateadmin" + admin);
		RespData respData = new RespData();
		Admin existAdmin=isAdminExist(admin.getAdminName());	
		if(existAdmin==null){
			adminService.updateAdmin(admin);
			respData.status = 1;
			respData.msg = "修改成功";
		}else {
			respData.status = 2;
		    respData.msg = "账户名已经存在，请重新输入";
		}
		
		return respData;

	}

	@RequestMapping("/deleteAdmins")
	@ResponseBody
	public RespData deleteAdmins(String ids) {
		RespData respData = new RespData();
		int affected = adminService.deleteAdmins(ids);
		if (affected != 0) {
			respData.content = affected;
			respData.status = 1;
			respData.msg = affected + "个管理员被删除";
		} else {
			respData.status = 2;
			respData.msg = "删除失败";
		}
		return respData;
	}
  
   public Admin isAdminExist(String adminName){
	  Admin admin=adminService.isAdminExist(adminName);
	  return admin;
   }
	/*@RequestMapping("/getCategorys")
	@ResponseBody
	public PagingList<Category> getCategorys(String cname, int page, int rows) {
		// PagingList<Category> pagingList = new PagingList<Category>();
		// categoryService.getCategorys(cname, page, rows);
		return categoryService.getCategorys(cname, page, rows);
	}

	@RequestMapping("/addCategory")
	@ResponseBody
	public RespData addCategory(Category category) {
		RespData respData = new RespData();
		if (categoryService.addCategory(category) != null) {
			respData.status = 1;
			respData.msg = category.getCname() + "添加成功";
		} else {
			respData.status = 2;
			respData.msg = "添加失败";
		}
		;
		return respData;
	}

	@RequestMapping("/updateCategory")
	@ResponseBody
	public RespData updateCategory(Category category) {
		RespData respData = new RespData();
		categoryService.updateCategory(category);
		respData.status = 1;
		respData.msg = "修改成功";
		return respData;
	}

	@RequestMapping("/deleteCategory")
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

	@RequestMapping("/getAllCategorys")
	@ResponseBody
	public List<Category> getAllCategorys() {
		List<Category> categories = categoryService.findAll();
		return categories;
	}

	@RequestMapping("/getSecondCategorys")
	@ResponseBody
	public PagingList<CategorySecond> getSecondCategorys(String csName, int page, int rows) {
		// PagingList<Category> pagingList = new PagingList<Category>();
		// categoryService.getCategorys(cname, page, rows);
		return categoryService.getSecondCategorys(csName, page, rows);
	}

	@RequestMapping("/addSecondCategory")
	@ResponseBody
	public RespData addSecondCategory(CategorySecond categorySecond) {
		RespData respData = new RespData();
		LogUtil.info("ceshiyixia" + categorySecond.getCategory());
		if (categoryService.addSecondCategory(categorySecond) != null) {
			respData.status = 1;
			respData.msg = categorySecond.getCsName() + "添加成功";
		} else {
			respData.status = 2;
			respData.msg = "添加失败";
		}
		;
		return respData;
	}

	@RequestMapping("/updateSecondCategory")
	@ResponseBody
	public RespData updateSecondCategory(CategorySecond categorySecond) {
		RespData respData = new RespData();
		categoryService.updateSecondCategory(categorySecond);
		respData.status = 1;
		respData.msg = "修改成功";
		return respData;
	}

	@RequestMapping("/deleteSecondCategory")
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

	@RequestMapping("getUsers")
	@ResponseBody
	public PagingList<User> getUsers(int page, int rows, Integer state, Date dateFrom, Date dateTo,
			HttpServletRequest request) {
		String queryStr = request.getParameter("queryStr");
		String field = request.getParameter("field");
		String gender = request.getParameter("gender");
		Map<String, Object[]> params=new HashMap<String, Object[]>();
		if(!StringUtil.isNullOrBlank(gender)){
		    Object[] operatorAndValue={Operators.EQ.operator,gender};
			params.put("gender", operatorAndValue);
		}
		if(state!=null){
			 Object[] operatorAndValue={Operators.EQ.operator,state};
			 params.put("state", operatorAndValue);
		}
		if(dateFrom!=null&&dateTo!=null){
			 Object[] operatorAndValue={Operators.GE.operator,dateFrom};
			 Object[] operatorAndValue2={Operators.LE.operator,dateTo};
			 params.put("createdTime", operatorAndValue);
			 params.put("createdTime2", operatorAndValue2);
		}
	     return userService.getUsers(queryStr, field,page, rows, params);
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public RespData deleteUser(String ids) {
		RespData respData = new RespData();
		int affected = userService.deleteUser(ids);
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
    @RequestMapping("/getProducts")
    @ResponseBody
    public PagingList<Product> getProducts(int page,int rows, Date dateFrom, Date dateTo,Integer isHot,HttpServletRequest request ){
    	String queryStr = request.getParameter("queryStr");
		String field = request.getParameter("field");
		String csid = request.getParameter("csid");
		String cid = request.getParameter("cid");
		LogUtil.info("csid"+"--"+csid+"--"+"cid"+cid);
		Map<String, Object[]> params=new HashMap<String, Object[]>();
		if(!StringUtil.isNullOrBlank(csid)){
		    Object[] operatorAndValue={Operators.EQ.operator,csid};
			params.put("categorySecond.csid", operatorAndValue);
		}
		if(!StringUtil.isNullOrBlank(cid)){
			 Object[] operatorAndValue={Operators.EQ.operator,cid};
			 params.put("cid", operatorAndValue);
		}
		if(isHot!=null){
			 Object[] operatorAndValue={Operators.EQ.operator,isHot};
			 params.put("isHot", operatorAndValue);
		}
		if(dateFrom!=null&&dateTo!=null){
			 Object[] operatorAndValue={Operators.GE.operator,dateFrom};
			 Object[] operatorAndValue2={Operators.LE.operator,dateTo};
			 params.put("createdTime", operatorAndValue);
			 params.put("createdTime2", operatorAndValue2);
		}
		return productService.getProducts(queryStr, field, page, rows, params);
    }
    
    @RequestMapping("/getAllSecondCategorys")
    @ResponseBody
    public List<CategorySecond> getAllSecondCategorys(){
    	return categoryService.getAllSecondCategory();
    }
    
    @RequestMapping("/getSecondCategorysWithCid")
    @ResponseBody
    public List<CategorySecond> getSecondCategorysWithCid(Integer cid){
    	return categoryService.getSecondCategoryWithCid(cid);
    }
    
    @RequestMapping("/addProduct")
    @ResponseBody
    public RespData addProduct(Product product){
    	RespData respData=new RespData();
    	product.setProductNumber(UUIDUtil.getUUID());
    	productService.addProduct(product);
    	respData.status=1;
    	respData.msg=product.getpName()+"添加成功";
    	return respData;
    }
   
    @RequestMapping("deleteProduct")
    @ResponseBody
    public RespData deleteProducts(String ids){
    	RespData respData=new RespData();
    	int affected=productService.deleteProducts(ids);
    	if(affected>0){
    	respData.status=1;
    	respData.msg=affected+"条记录已被删除";
    	respData.content=affected;
    	}else{
    		respData.status=2;
    		respData.msg="删除失败";
    	}
    	return respData;
    }
    
    @RequestMapping("/updateProduct")
    @ResponseBody
    public RespData updateProduct(Product product){
    	RespData respData=new RespData();
    	productService.updateProduct(product);
    	respData.status=1;
    	respData.msg="商品更新成功";
    	return respData;
    }
    
    @RequestMapping("/getOrders")
    @ResponseBody
    public PagingList<Order> getOrders(int page,int rows,Date dateFrom,Date dateTo,HttpServletRequest request){
    	String queryStr=request.getParameter("queryStr");
    	String field=request.getParameter("field");
    	String userName=request.getParameter("userName");
    	String sstate=request.getParameter("state");
    	byte state=0;
    	if(!StringUtil.isNullOrBlank(sstate))
    	state=Byte.parseByte(sstate);
    	Map<String, Object[]> params=new HashMap<String, Object[]>();
    	LogUtil.info("byte"+state);
    	if(dateFrom!=null&&dateTo!=null){
			 Object[] operatorAndValue={Operators.GE.operator,dateFrom};
			 Object[] operatorAndValue2={Operators.LE.operator,dateTo};
			 params.put("createdTime", operatorAndValue);
			 params.put("createdTime2", operatorAndValue2);
		}
    	if(!StringUtil.isNullOrBlank(userName)){
    		 Object[] operatorAndValue={Operators.LIKE.operator,userName};
    		 params.put("userName", operatorAndValue);
    	}
    	if(state!=0){
    		 Object[] operatorAndValue={Operators.EQ.operator,state};
    		 params.put("state", operatorAndValue);
    	}
  
    	return orderService.getOrders(queryStr, field, page, rows, params);
    	
    }
    @RequestMapping("deleteOrders")
    @ResponseBody
    public RespData deleteOrders(String ids){
    	RespData respData=new RespData();
    	int affected=orderService.deleteOrders(ids);
    	if(affected>=0){
    		respData.content=affected;
    		respData.msg=affected+"条记录已被删除";
    		respData.status=1;
    	}else{
    		respData.status=2;
    		respData.msg="删除失败";
    	}
    	return respData;
    }*/
   @InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
