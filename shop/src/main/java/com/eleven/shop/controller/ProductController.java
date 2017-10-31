package com.eleven.shop.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eleven.shop.bean.Operators;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;
import com.eleven.shop.bean.RespData;
import com.eleven.shop.service.ProductService;
import com.eleven.shop.util.LogUtil;
import com.eleven.shop.util.StringUtil;
import com.eleven.shop.util.UUIDUtil;

@Controller
public class ProductController {
  @Resource
  private ProductService productService;
  
  @RequestMapping("/findByPid/{pid}")
  public String findByPid(@PathVariable("pid") int pid,Map<String, Object> map){
	  
	  map.put("product", productService.findByPid(pid));
	  return "product";
  }
  
  @RequestMapping("/findByCid/{cid}/{pageNow}")
  public String findByCid(@PathVariable("cid") Integer cid,@PathVariable("pageNow") Integer pageNow,Map<String, Object> map){
	 int pageSize=12;
	 PagingList<Product> pages= new PagingList<Product>();
	 pages=productService.getProductsByCid(cid, pageNow, pageSize);
	 map.put("pages", pages);
	 return "productList";
  }
  
  @RequestMapping("findByCsid/{csid}/{pageNow}")
  public String findByCsid(@PathVariable Integer csid ,@PathVariable Integer pageNow,Map<String, Object> map ){
	  int pageSize=12;
	  PagingList<Product> pages=new PagingList<Product>();
	  pages =productService.getProductsByCsid(csid, pageNow, pageSize);
	 // Map<String, Object> map = new HashMap<String, Object>();
	  map.put("pages", pages);
	  return "productList";
  }
  
  @RequestMapping("/admin/addProduct")
  @ResponseBody
  public RespData addProduct(Product product){
  	RespData respData=new RespData();
  	product.setProductNumber(UUIDUtil.getUUID());
  	productService.addProduct(product);
  	respData.status=1;
  	respData.msg=product.getpName()+"添加成功";
  	return respData;
  }
 
  @RequestMapping("/admin/deleteProduct")
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
  
  @RequestMapping("/admin/updateProduct")
  @ResponseBody
  public RespData updateProduct(Product product){
  	RespData respData=new RespData();
  	productService.updateProduct(product);
  	respData.status=1;
  	respData.msg="商品更新成功";
  	return respData;
  }
  
  @RequestMapping("/admin/getProducts")
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
  @InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
