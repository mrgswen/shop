package com.eleven.shop.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Cart;
import com.eleven.shop.bean.CartItem;
import com.eleven.shop.bean.Product;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.service.ProductService;
import com.eleven.shop.util.LogUtil;

@Controller
@RequestMapping
public class CartController {
	@Resource
	private ProductService productService;
	
	@RequestMapping("/addToCart")
	@ResponseBody
	public Map<String, Object> addToCart(int pid, int count, HttpSession session) {
		Product product = productService.findByPid(pid);
		Map<String, Object> result = new HashMap<String, Object>();
		if (product == null) {
			result.put("status", "添加失败");
		}
		CartItem item = new CartItem(); 
		item.setCount(count);
		item.setProduct(product);
		Cart cart = getCart(session);
		cart.addItem(item);

		result.put("status", "success");
		return result;
	}

	// @RequestMapping("/clearCart")
	// @ResponseBody
	// public Map<String, Object> clearCart(HttpSession session) {
	// String status = null;
	// try {
	// getCart(session).clearCart();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// status = e.getCause().getMessage();
	// }
	// if(StringUtil.isNullOrEmpty(status)){
	// status="success";
	// }
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("status", status);
	// return map;
	// }

	@RequestMapping("/clearCart")
	public String clearCart(HttpSession session) {
		try {
			getCart(session).clearCart();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return "cart";
	}

	@RequestMapping("/deleteItem")
	@ResponseBody
	public RespData deleteItem( Integer pid,HttpSession session){
		Cart cart =getCart(session);
		RespData respData = new RespData();
		Map<String, String> newDatas=new HashMap<String, String>();
		try {
			 newDatas=cart.deleteItem(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respData.status=2;
			respData.msg=e.getMessage();
			return respData;
		}
		respData.status=1;
		respData.content=newDatas;
		return respData;
		
	}

	@RequestMapping("/changeCount")
	@ResponseBody
	public RespData changeCount(int pid, int count, HttpSession session) {
		Cart cart = getCart(session);
		Map<String, String> newDatas = new HashMap<String, String>();
		RespData respData = new RespData();
		try {
			newDatas = cart.changCartItemCount(pid, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respData.status = 2;
			respData.msg = e.getMessage();
			return respData;
		}
		LogUtil.info(cart.getTotal());
		respData.status = 1;
		respData.content = newDatas;
		return respData;

	}

	@RequestMapping("/selectedStatus")
	@ResponseBody
	public RespData selectedStatus(boolean isSelected, int pid, HttpSession session) {
		Cart cart = getCart(session);
		RespData respData = new RespData();
		Map<String, String> newDatas = new HashMap<String, String>();
		try {
			LogUtil.info("cartController"+isSelected);
			newDatas = cart.changeSeletedStatus(pid, isSelected);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respData.status = 2;
			respData.msg = e.getMessage();
			return respData;
		}
		respData.status = 1;
		respData.content = newDatas;
		return respData;
	}

	@RequestMapping("/selectAll")
	@ResponseBody
	public RespData selectAll(Boolean selectAll, HttpSession session) {
		Cart cart = getCart(session);
		RespData respData = new RespData();
		Map<String, String> newDatas = new HashMap<String, String>();
		newDatas = cart.selectAll(selectAll);
		respData.status = 1;
		respData.content = newDatas;
		return respData;
	}

	private Cart getCart(HttpSession session) {
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}

}
