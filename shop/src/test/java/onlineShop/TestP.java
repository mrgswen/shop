package onlineShop;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eleven.shop.bean.News;
import com.eleven.shop.bean.PagingList;
import com.eleven.shop.bean.Product;
import com.eleven.shop.dao.OrderDao;
import com.eleven.shop.dao.ProductDao;
import com.eleven.shop.service.NewsService;
import com.eleven.shop.service.OrderService;
import com.eleven.shop.service.ProductService;
import com.eleven.shop.service.UserService;
import com.eleven.shop.util.LogUtil;

import sun.print.resources.serviceui;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:applicationContext.xml"})  
public class TestP {
	@Resource
	ProductService productService;
	@Resource 
	ProductDao productDao;
	@Resource
	OrderService orderService;
	@Resource 
	OrderDao orderDao;
	@Resource
	UserService userService;
	@Resource
	NewsService newsService;
	@Resource
	SessionFactory SessionFactory;
//  @Test
//  public void testSysout(){
//	  System.out.println("````````````11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//	  System.out.println(productService.findHot(10).size());
//	  System.out.println("aaaa----------------"+productService.findHot(10));
//	  System.out.println("````````````1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
//  }
//  @Test
//  public void testFindByPid(){
//	  LogUtil.info(productService.findByPid("1"));
//  }
  @Test
  public void testFindByCid(){
	//  LogUtil.info(productService.getProNumberByCid(2));
  }
  @Test
  public void testFindByCsid(){
//	  LogUtil.info(productService.getProductsByCsid(1, 1, 12));
//	 PagingList<Product> pages= productService.getProductsByCsid(1, 1, 12);
//	  List<Product> products =pages.getPageNowDatas();
//	  for(Product p:products){
//		  System.out.println(p);
//	  }
	//  productService.updateProductNumber();
  }
  @Test
  public void testFindOrdersByUserId(){
	  //LogUtil.info("11"+orderService.findOrdersByUserId(1, 5, 2));
	 // userService.testUserDaoImple2();
	  for(int i=0;i<10;i++){
		  News news=new News();
		  news.setTitle("title"+i);
		  news.setContent("content"+i);
		  newsService.add(news);
	  }
  }
  
  @Test
  public void testSecond(){
//	  Session session=SessionFactory.getCurrentSession();
//	  session.beginTransaction();
//	  @SuppressWarnings("unchecked")
//	List<News> list=session.createQuery("from News news").list();
//	  session.getTransaction().commit();
//	  Session session2=SessionFactory.getCurrentSession();
//	  session2.beginTransaction();
//	  News news=(News) session2.load(News.class, 1);
//	  System.out.println(news.getContent());
//	  session2.getTransaction().commit();
	 //  List<News> newss=newsService.findAll();
	   System.out.println("hahahaa=====================================================");
	   News news=newsService.getNews(News.class, 1);
	   System.out.println(news+"================");
	  
  }
}
