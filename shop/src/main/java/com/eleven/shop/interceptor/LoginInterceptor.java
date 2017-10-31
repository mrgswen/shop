package com.eleven.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.eleven.shop.bean.User;
import com.eleven.shop.util.LogUtil;
public class LoginInterceptor implements HandlerInterceptor {
	private static final String TOKEN = "token";
	// 在spring-mvc.xml初始化
	private String[] excludeUrls;

	public void setExcludeUrl(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		if(uri.contains("/order/")&&!uri.contains("/order/admin/")){//拦截普通用户进入订单相关页面请求
		 User user = (User) session.getAttribute("user");
		if (user == null) {
			StringBuffer url = request.getRequestURL();
			session.setAttribute("url", url.toString());
			response.sendRedirect(request.getContextPath() + "/login");//未登录拦截请求，不放行，重定向到登录页面
			return false;
		} else {
			return true;
		 }
		}else{
			String token = (String) session.getAttribute(TOKEN);
			if (token != null) {// token不为空，已经登录
				for (String excludeUrl : excludeUrls) {
					if (uri.contains(excludeUrl)) {// 登录或者登录校验url都重定向到后台管理主页面
						response.sendRedirect(request.getContextPath() + "/adminIndex");
						return false;
					}
				}
				return true;// 已经登录了，放行对应的请求
			} else {
				for (String excludeUrl : excludeUrls) {
					if (uri.contains(excludeUrl)) {// 登录请求放行
						return true;
					}
				}
				response.sendRedirect(request.getContextPath() + "/adminLogin");// 未登录，不放行，重定向的登录页面
				return false;
			}
		}
	}

}