<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="container header">
		<div class="span5">
			<div class="logo">
				<a href="http://localhost:8080/mango/"> <img
					 alt="onlineshop"></a>
			</div>
		</div>
		<div class="span9">
			<div class="headerAd">
				<img src="${pageContext.request.contextPath }/image/header.jpg" width="320" height="50" alt="正品保障"
					title="正品保障">
			</div>
		</div>
		<div class="span10 last">
			<div class="topNav clearfix">
				<ul>
					<c:if test="${sessionScope.user==null }">
						<li id="headerLogin" class="headerLogin"
							style="display: list-item;"><a
							href="${pageContext.request.contextPath }/login">登录</a>|</li>
						<li id="headerRegister" class="headerRegister"
							style="display: list-item;"><a
							href="${pageContext.request.contextPath }/register">注册</a>|</li>
						<li id="headerUsername" class="headerUsername"></li>
					</c:if>
					<c:if test="${sessionScope.user!=null }">
						<li id="headerLogin" class="headerLogin"
							style="display: list-item;"><c:out value="${user.userName }" />|
						</li>
						<li id="headerLogout" class="headerLogout"
							style="display: list-item;"><a
							href="${pageContext.request.contextPath }/logout">[退出]</a>|</li>
							<li id="headerLogout" class="headerLogout"
							style="display: list-item;"><a
							href="${pageContext.request.contextPath }/order/findOrdersByUserId/1">我的订单</a>|</li>
					</c:if>

					<!--  <li id="headerRegister" class="headerRegister"
						style="display: list-item;"><a
						href="${pageContext.request.contextPath }/userRegister">注册</a>|</li>
					<li id="headerUsername" class="headerUsername"></li>-->
					<li><a>会员中心</a> |</li>
					<li><a>购物指南</a> |</li>
					<li><a>关于我们</a></li>
				</ul>
			</div>
			<div class="cart">
				<a href="${pageContext.request.contextPath }/cart">购物车</a>
			</div>
			<div class="phone">
				客服热线: <strong>15726607618</strong>
			</div>
		</div>
		<div class="span24">
			<ul class="mainNav">
				<li><a href="${ pageContext.request.contextPath }/index">首页</a>|</li>
				<c:forEach items="${sessionScope.categories}" var="category">
					<li><a
						href="${ pageContext.request.contextPath }/findByCid/<c:out value="${category.cid}"/>/1">
							<c:out value="${category.cname}"></c:out>
					</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>