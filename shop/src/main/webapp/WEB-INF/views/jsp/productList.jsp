<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网上商城</title>
<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/product.css"
	rel="stylesheet" type="text/css" />

</head>
<body>
	<%@include file="menu.jsp"%>
	<div class="container productList">
		<div class="span6">
			<div class="hotProductCategory">
				<c:forEach var="category" items="${categories }">
					<dl>
						<dt>
							<a
								href="${pageContext.request.contextPath}/findByCid/${category.cid}/1"><c:out
									value="${category.cname}" /></a>
						</dt>
						<c:forEach var="secondCategory"
							items="${category.categorySeconds }">
							<dd>
								<a
									href="${pageContext.request.contextPath}/findByCsid/${secondCategory.csid}/1">${secondCategory.csName}</a>
							</dd>
						</c:forEach>
					</dl>
				</c:forEach>
			</div>
		</div>
		<div class="span18 last">

			<form id="productForm"
				action="./image/蔬菜 - Powered By Mango Team.htm" method="get">
				<div id="result" class="result table clearfix">
					<ul>
						<c:forEach items="${pages.rows }" var="p">
							<li><a
								href="${pageContext.request.contextPath }/findByPid/${p.pid }">
									<img
									src="${pageContext.request.contextPath }/<c:out value="${p.image}"/>"
									width="170" height="170" style="display: inline-block;">
									<span style='color: green'> <c:out value="${p.pName }"></c:out>
								</span> <span class="price"> 商城价： ￥<c:out
											value="${p.shopPrice }"></c:out></span>

							</a></li>
						</c:forEach>
					</ul>
				</div>
				<div class="pagination">
					<span>第 <c:out value="${pages.pageNow}" />/<c:out
							value="${pages.totalPages}" />页
					</span>
					<c:if test="${cid!=null }">
						<!-- 第一页时不能点击首页和上一页 -->
						<c:if test="${pages.pageNow == 1}">
							<span class="firstPage"></span>
							<span class="previousPage"></span>
						</c:if>

						<c:if test="${pages.pageNow != 1}">
							<span> <a class="firstPage"
								href="${pageContext.request.contextPath}/findByCid/${cid}/1"></a></span>
							<span><a class="previousPage"
								href="${pageContext.request.contextPath}/findByCid/${cid}/<c:out value="${pages.pageNow-1}"/>"></a></span>
						</c:if>

						<c:forEach var="i" begin="${pages.startPage }"
							end="${pages.endPage }">
							<span> <!-- 如果是当前页则不能够点击 --> <c:if
									test="${i == pages.pageNow }">
									<span class="currentPage">${pages.pageNow }</span>
								</c:if> <c:if test="${i != pages.pageNow}">
									<a
										href="${pageContext.request.contextPath}/findByCid/${cid}/<c:out value="${i}"/>">
										<c:out value="${i}" />
									</a>
								</c:if>
							</span>
						</c:forEach>
						<!-- 下一页 -->
						<c:if test="${pages.pageNow <pages.totalPages }">
							<span><a class="nextPage"
								href="${pageContext.request.contextPath}/findByCid/${cid}/<c:out value="${pages.pageNow+1}"/>"></a></span>
							<span><a class="lastPage"
								href="${pageContext.request.contextPath}/findByCid/${cid}/<c:out value="${pages.totalPages}"/>"></a></span>
						</c:if>
						<c:if test="${pages.pageNow ==pages.totalPages }">
							<span class="nextPage"></span>
							<span class="lastPage"></span>
						</c:if>
					</c:if>
					
					
					<c:if test="${csid!=null }">
						<!-- 第一页时不能点击首页和上一页 -->
						<c:if test="${pages.pageNow == 1}">
							<span class="firstPage"></span>
							<span class="previousPage"></span>
						</c:if>

						<c:if test="${pages.pageNow != 1}">
							<span> <a class="firstPage"
								href="${pageContext.request.contextPath}/findByCsid/${csid}/1"></a></span>
							<span><a class="previousPage"
								href="${pageContext.request.contextPath}/findByCsid/${csid}/<c:out value="${pages.pageNow-1}"/>"></a></span>
						</c:if>

						<c:forEach var="i" begin="${pages.startPage }"
							end="${pages.endPage }">
							<span> <!-- 如果是当前页则不能够点击 --> <c:if
									test="${i == pages.pageNow }">
									<span class="currentPage">${pages.pageNow }</span>
								</c:if> <c:if test="${i != pages.pageNow}">
									<a
										href="${pageContext.request.contextPath}/findByCsid/${csid}/<c:out value="${i}"/>">
										<c:out value="${i}" />
									</a>
								</c:if>
							</span>
						</c:forEach>
						<!-- 下一页 -->
						<c:if test="${pages.pageNow <pages.totalPages }">
							<span><a class="nextPage"
								href="${pageContext.request.contextPath}/findByCid/${csid}/<c:out value="${pages.pageNow+1}"/>"></a></span>
							<span><a class="lastPage"
								href="${pageContext.request.contextPath}/findByCid/${csid}/<c:out value="${pages.totalPages}"/>"></a></span>
						</c:if>
						<c:if test="${pages.pageNow ==pages.totalPages }">
							<span class="nextPage"></span>
							<span class="lastPage"></span>
						</c:if>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="./image/footer.jpg" width="950" height="52" alt="我们的优势"
					title="我们的优势">
			</div>
		</div>
		<div class="span24">
			<ul class="bottomNav">
				<li><a>关于我们</a> |</li>
				<li><a>联系我们</a> |</li>
				<li><a>诚聘英才</a> |</li>
				<li><a>法律声明</a> |</li>
				<li><a>友情链接</a> |</li>
				<li><a target="_blank">支付方式</a> |</li>
				<li><a target="_blank">配送方式</a> |</li>
				<li><a>官网</a> |</li>
				<li><a>论坛</a></li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright©2005-2015 网上商城 版权所有</div>
		</div>
	</div>
</body>
</html>