<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>购物车</title>
<link href="${pageContext.request.contextPath}/css/common.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/cart.css"
	rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/css/jquery-ui.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#span24 .readonly").each(function() {

			$(this).attr("width", $(this).val().length);
		});
		$("tbody>tr .increment").click(
				function() {
					checkedWhenIncOrDec($(this));
					var $count = $(this).parent().find("input");
					var $secondtd = $(this).parent().parent().find(
							"td:eq(1) input[name='id']");
					//alert($count.val());
					var pid = $secondtd.val();
					var numStr = $count.val();
					var num = parseInt(numStr);
					if (num == 1) {
						return false;
					}
					$count.val(num - 1);
					var $subtotal = $(this).parent().next().find("input");
					changeCount($count.val(), pid, $subtotal);

				});
		$("tbody>tr .decrement").click(
				function() {
					checkedWhenIncOrDec($(this));
					var $count = $(this).parent().find("input");
					var $secondtd = $(this).parent().parent().find(
							"td:eq(1) input[name='id']");
					var numStr = $count.val();
					var pid = $secondtd.val();
					var num = parseInt(numStr);
					$count.val(num + 1);
					var $subtotal = $(this).parent().next().find("input");
					changeCount($count.val(), pid, $subtotal);
				});
		function changeCount(count, pid, $subtotal) {
			$.ajax({

				url : "${pageContext.request.contextPath}/changeCount",
				data : {
					"pid" : pid,
					"count" : count
				},
				async : false,
				success : function(data) {
					if (data.status == 1) {
						$subtotal.val("￥" + data.content.subTotal);
						$("#effectivePrice input.readonly").val(
								data.content.total);
						$("#effectivePoint input.readonly").val(
								data.content.selectedCount);
					}
				}

			})
		}
		$("#checkedAll").click(function() {//全选
			$("[name='cartItems']:checkbox").prop("checked", this.checked);//购物项的选中状态和全选的选中状态一致
			selectAll(this.checked);//与后台交互

		});
		$("[name='cartItems']:checkbox").click(function() {//购物项checkbox
			var flag = true;
			selectedStatus(this);//选中状态传给后台
			$("[name='cartItems']:checkbox").each(function() {
				if (!this.checked) {
					flag = false;//购物项中有一个未选中,则全选标志为未选中
				}
			});

			$("#checkedAll").prop("checked", flag);
		});

		$("tr>td .delete")
				.click(
						function() {
							var $tr = $(this).parent().parent();
							var pid = $tr.find("td:eq(1) input[name='id']")
									.val();
							$
									.ajax({
										url : "${pageContext.request.contextPath}/deleteItem",
										data : {
											"pid" : pid
										},
										success : function(data) {
											if (data.status == 1) {

												$(
														"#effectivePrice input.readonly")
														.val(data.content.total);
												$(
														"#effectivePoint input.readonly")
														.val(
																data.content.selectedCount);
												if (data.content.emptyOrNot == "true") {
													$tr
															.parent()
															.parent()
															.replaceWith(
																	"<div class='step'>"
																			+ "<center><span  class='info'><a "+
									"href='${pageContext.request.contextPath}/index'><strong>亲!您还没有购物!请先去购物! </strong></a> </span>"
																			+ "</center></div>");
													$("div").remove(".total");
													$("div").remove(".bottom");
												} else {
													$tr.remove();
												}
											}
										}
									});
						});
		$( "#dialog" ).dialog({
		      autoOpen: false,
		      height: "100",
		      width:"250",
		      modal:true,
		      show: {
		          effect: "blind",
		          duration: 1000
		        },
		        hide: {
		          effect: "explode",
		          duration: 1000
		        }
		      
		    });
		$("div.bottom #balanceCart")
				.click(
						function() {
							var $selectedItems = $("[name='cartItems']:checkbox:checked");
							if ($selectedItems.length == 0) {
								$( "#dialog" ).dialog( "open" );
								return false;
							} else {
								window.location.href = "${pageContext.request.contextPath}/order/balanceCart";
							}

						});
		function getSubTotal(param) {
			var subTotalStr = $(param).parent().parent().find("td:eq(5) input")
					.val();
			var subTotal = parseFloat(subTotalStr);

		}
		function selectAll(obj) {//全选按钮选中时
			$.ajax({
				url : "${pageContext.request.contextPath}/selectAll?selectAll="
						+ obj,
				success : function(data) {
					if (data.status == 1) {
						$("#effectivePrice input.readonly").val(
								data.content.total);
						$("#effectivePoint input.readonly").val(
								data.content.selectedCount);
					}
				}

			});
		}
		function checkedWhenIncOrDec($obj) {//购物项商品数量修改时对商品选中
			var $cartItemCheckbox = $obj.parent().parent().find(
					"td:eq(0) input[name='cartItems']");
			if (!$cartItemCheckbox.is(":checked")) {
				$cartItemCheckbox.prop("checked", "checked");
			}
			var $selectedItems = $("[name='cartItems']:checkbox:checked");
			var selectedCounts = $selectedItems.length;
			var tableLength = $("#cartItemsBody>tr").length;
			if (selectedCounts == tableLength) {//当所有商品被选中时，全选按钮选中
				$("#checkedAll").prop("checked", "checked");
			}
		}
		function selectedStatus(obj) {//更改每个商品的选中状态
			var pid = $(obj).parent().next().find("input").val();
			var isSelected = false;
			if (obj.checked) {
				isSelected = true;
			}
			$.ajax({
				url : "${pageContext.request.contextPath}/selectedStatus",
				data : {
					"isSelected" : isSelected,
					"pid" : pid
				},
				success : function(data) {
					if (data.status == 1) {
						$("#effectivePrice input.readonly").val(
								data.content.total);
						$("#effectivePoint input.readonly").val(
								data.content.selectedCount);
					}
				}
			})
		}
	});
</script>
<body>

	<%@include file="menu.jsp"%>
	<div class="container cart">
		<div id="span24" class="span24">
			<c:if test="${fn:length(sessionScope.cart.cartItems)!=0}">
				<div class="step step1"></div>
				<table>
					<thead>
						<tr>
							<th><c:if test="${cart.selectAll }">
									<input type="checkbox" id="checkedAll" checked="checked" />
								</c:if> <c:if test="${cart.selectAll==false }">
									<input type="checkbox" id="checkedAll" />
								</c:if>全选</th>
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="cartItemsBody">

						<c:forEach items="${cart.cartItems }" var="item">
							<tr>
								<c:if test="${item.isSelected }">
									<td><input type="checkbox" name="cartItems"
										checked="checked" /></td>
								</c:if>
								<c:if test="${item.isSelected==false }">
									<td><input type="checkbox" name="cartItems" /></td>
								</c:if>

								<td width="60"><input type="hidden" name="id"
									value="${item.product.pid }"> <img
									src="${pageContext.request.contextPath}/${item.product.image}">
								</td>
								<td><a target="_blank"
									href="${pageContext.request.contextPath}/findByPid/${item.product.pid }">
										${item.product.pName}</a></td>
								<td>${item.product.shopPrice}</td>
								<td class="quantity"><span class="increment"><a
										href="javascript:void(0);"></a>-</span><input type="text"
									name="count" class="count" value="${item.count}" /><span
									class="decrement"><a href="javascript:void(0);"></a>+</span></td>
								<td width="140"><input type="text"
									value="￥${item.subTotal}" class="readonly" readonly="readonly" /></td>
								<td><a href="javascript:void(0);" class="delete">删除</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<dl id="giftItems" class="hidden" style="display: none;">
				</dl>
				<div class="total">
					<c:if test="${sessionScope.user==null }">
						<em id="promotion"></em>
						<em id="toLogin"> <a
							href="${pageContext.request.contextPath }/login">登录后确认是否享有优惠</a>
						</em>
					</c:if>
					<em id="effectivePoint">已经选择<input type="text"
						value="${cart.selectedCount}" class="readonly" readonly="readonly" />件商品
					</em> 商品金额: <strong id="effectivePrice">￥<input type="text"
						value="${cart.total}" class="readonly" readonly="readonly" />元
					</strong>

				</div>
				<div class="bottom">
					<a href="${pageContext.request.contextPath}/clearCart"
						class="clear"><span id="clear">清空购物车</span></a> <a
						href="javascript:void(0);" id="balanceCart" class="submit">结算</a>
					<div id="dialog" title="提示"><p>请至少选择一件商品</p></div>
					<a href="${pageContext.request.contextPath}/index" class="submit">继续购物</a>
				</div>
			</c:if>
			<c:if test="${fn:length(sessionScope.cart.cartItems) == 0}">
				<div class="step step1"></div>
				<div class="step step1">
					<center>
						<span style="color: red"><a
							style="color: red; font-size: 18px;"
							href="http://localhost:8080/shop/index"><strong>
									亲!您还没有购物!请先去购物! </strong></a> </span>
					</center>
				</div>

			</c:if>
		</div>

	</div>
	<div class="container footer">
		<div class="span24">
			<div class="footerAd">
				<img src="${pageContext.request.contextPath}/image/footer.jpg"
					width="950" height="52" alt="我们的优势" title="我们的优势">
			</div>
		</div>
		<div class="span24">
			<ul class="bottomNav">
				<li><a>关于我们</a> |</li>
				<li><a>联系我们</a> |</li>
				<li><a>招贤纳士</a> |</li>
				<li><a>法律声明</a> |</li>
				<li><a>友情链接</a> |</li>
				<li><a target="_blank">支付方式</a> |</li>
				<li><a target="_blank">配送方式</a> |</li>
				<li><a>服务声明</a> |</li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div class="span24">
			<div class="copyright">Copyright © 2005-2017 网上商城 版权所有</div>
		</div>
	</div>
</body>
</html>