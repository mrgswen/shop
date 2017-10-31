package com.eleven.shop.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.bean.User;
import com.eleven.shop.service.UserService;
import com.eleven.shop.util.CheckCodeUtil;
import com.eleven.shop.util.LogUtil;
import com.eleven.shop.util.StringUtil;

@Controller
public class UserController {
	@Resource
	UserService userService;


	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	//@ResponseBody
	public String register(@Valid User user,BindingResult result,Map<String, Object> map) {
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				System.out.println(error.getField() + "---" + error.getDefaultMessage());
			}
			return "register";
		} else {
			userService.register(user);
			map.put("registerStatus", "success");
			return "msg";
		}
	}

	@RequestMapping("/checkUser")
	public void checkUserExist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userName = request.getParameter("userName");

		LogUtil.info(userName);
		User user = userService.findByUsername(userName);
		System.out.println(user);
		response.setContentType("text/html;charset=utf-8");
		if (user == null) {
			response.getWriter().println(0);

		} else {
			response.getWriter().println(1);
		}
	}

	@RequestMapping(value = { "/userActive/{code}" }, method = RequestMethod.GET)
	public String active(@PathVariable("code") String code, Map<String, Object> map) {
		System.out.println("hahhahahhahahhahahhhh");

		User user = userService.findByCode(code);
		if (user == null) {
			map.put("noUser", "noUser");
			return "msg";
		}
		map.put("activeSuccess", "activeSuccess");
		user.setCode("");
		user.setState(1);
		userService.update(user);
		return "msg";

	}

	
	@RequestMapping("/userLogin")
	@ResponseBody
	public Map<String, Object> login(HttpSession session,  String userName ,String password,String checkCode ) {
		Map<String, Object> map = new HashMap<String, Object>();
		User userExist = userService.findByUsername(userName);
		System.out.println();
		if (userExist == null) {
			map.put("loginStatus", "noUser");
			return map;
		}
		if (userExist.getState() == 0) {
			map.put("loginStatus", "notActive");
			return map;
		}
		userExist = userService.findByUsernameAndPassword(userName, password);
		if (userExist == null) {
			map.put("loginStatus", "incorrectPassword");
			return map;
		}
        String sessionCheckCode=(String) session.getAttribute("checkCode");
        System.out.println(sessionCheckCode);
        if(!sessionCheckCode.equalsIgnoreCase(checkCode)){
        	map.put("loginStatus", "incorrectCheckCode");
        	return map;
        }
		session.setAttribute("user", userExist);
		String previousUrl=(String) session.getAttribute("url");
		if(previousUrl!=null){//如果是因为登录认证跳转过来的，则需要跳转回登录之前的页面
			map.put("loginStatus", "successAndRedirect");
			map.put("previousUrl", previousUrl);
		}else{
			map.put("loginStatus", "success");
		}
		return map;
	}
    
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		System.out.println("dddddddddddddddddddddddddddddddddddddddddddddd"+session.getMaxInactiveInterval());
		session.invalidate();
		return "redirect:index";
	}
	@RequestMapping("/ajaxTest")
	public String  test() {
		System.out.println("test----");
		return "a";
	}
	
	@RequestMapping("/getCheckCodeImage")
	public void getCheckCodeImage(HttpSession session,HttpServletResponse response){
		try {
			CheckCodeUtil.getImage(session, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping("/addUser5")
    public String addUser5( User user,HttpServletRequest request) {
        System.out.println("userName is:"+user.getUserName());
        System.out.println("password is:"+user.getPassword());
        System.out.println(request.getParameter("test"));
        return "a";
    }
	@RequestMapping("/d")
	public String testHaha(){
		return "1";
	}
	
	@RequestMapping("/admin/getUsers")
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

	@RequestMapping("/admin/deleteUser")
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
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
