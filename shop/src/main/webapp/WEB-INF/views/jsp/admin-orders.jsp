<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="orders_table"></table>
<div id='orders_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
			plain="true" id="orders_remove">删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload" plain="true"
			id="orders_reload">刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
		创建日期从：<input type="text" id="orders_date_from" editable="false"
			name="orders_date_from" style="width: 110px"> 到：<input
			type="text" id="orders_date_to" name="orders_date_to"
			editable="false" style="width: 110px"> &nbsp;&nbsp;订单状态：<input
			type=text id="state" name="state" style="width: 60px">
		&nbsp;&nbsp;订单所属用户：<input type=text id="user" name="user.userName"
			style="width: 110px">
	</div>
	<div style="padding: 7px 0 0 7px; color: #333;">
		<input id="orders_search"></input>
		<div id="orders_search_menu" style="width: 120px">
			<div data-options="name:'orderNumber'">订单编号</div>
			<div data-options="name:'ownerName'">收货人</div>
			<div data-options="name:'address'">收货地址</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var $date_from = $('#orders_date_from');
		var $date_to = $('#orders_date_to');
		crud.extendDateBox($date_from);
		crud.extendDateBox($date_to);
		$('#orders_table').datagrid({
			url : 'order/admin/getOrders',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			//fitColumns:true,
			border : false,
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 30, 40, 50, 60 ],
			pageNumer : 1,
			toolbar : '#orders_toolbar',
			columns : [ [ {
				field : 'orderNumber',
				title : '订单编号',
				width : 100,
			}, {
				field : 'phone',
				title : '电话',
				width : 100,
			}, {
				field : 'address',
				title : '收件人地址',
				width : 100,
			}, {
				field : 'createdTime',
				title : '创建时间',
				width : 100,
				formatter : function(value, row, index) {
					var datestamp = new Date(value);
					//alert(JSON.stringify(datestamp));
					//alert(value);
					return crud.formatTimeStamp(datestamp);
				}
			}, {
				field : 'user',
				title : '所属用户',
				width : 100,
				formatter : function(value, row, index) {
					return value.userName;
				}
			}, ] ],
			frozenColumns : [ [ {
				field : 'oid',
				title : 'id',
				width : 100,
				checkbox : true,
			}, {
				field : 'totalPrice',
				title : '总价(元)',
				width : 50
			}, {
				field : 'ownerName',
				title : '收货人',
				width : 100
			}, {
				field : 'state',
				title : '订单状态',
				width : 100,
				formatter : function(value, row, index) {
					if (value == 1)
						return "未付款";
					else if (value == 2)
						return "已付款";
					else if (value == 3)
						return "已发货";
					else if (value == 4)
						return "交易完成";
					else if (value == 5)
						return "订单已取消";
				}
			}, ] ],
			onLoadSuccess : function(data) {
				$('#orders_table').datagrid('clearSelections'); //一定要加上这一句，要不然datagrid会记住之前的选择状态，删除时会出问题    
				//遍历结果  
				for (var i = 0; i < data.rows.length; i++) {
					//当订单还没完成时不可以删除，设置对应记录行不可选定
					if (data.rows[i].state <= 3) {
						$("input[type='checkbox']")[i + 1].disabled = true;
					}

				}
			},
			onClickRow : function(rowIndex, rowData) {
				//根据state值 当订单还没完成时不可以删除， 单击单选行不可用  
				if (rowData.state <= 3) {
					$('#orders_table').datagrid('unselectRow', rowIndex);

				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				//根据state值 双击单选行不可用  
				if (rowData.state <= 3) {
					$('#orders_table').datagrid('unselectRow', rowIndex);
				}
			},
			onSelectAll : function(rows) {
				//根据state值  全选时某些行不选中  
				$.each(rows, function(i, n) {
					if (n.state <= 3) {
						$('#orders_table').datagrid('unselectRow', i);
					}
				});
			},
			
		});
		$('input[name="state"]').combobox({
			panelHeight : 'auto',
			multiple : false,
			valueField : 'value',
			textField : 'label',
			editable : false,
			data : [ {
				label : "未付款",
				value : "1"
			}, {
				label : "已付款",
				value : "2"
			}, {
				label : "已发货",
				value : "3"
			}, {
				label : "交易完成",
				value : "4"
			}, {
				label : "订单已取消",
				value : "5"
			}, ]
		});
		var $target_table = $('#orders_table');

		$('#orders_search')
				.searchbox(
						{
							searcher : function(value, name) {
								var queryStr = $.trim(value);
								var dateFrom = $(
										'input[name="orders_date_from"]').val();
								var dateTo = $('input[name="orders_date_to"]')
										.val();
								var state = $('input[name="state"]').val();
								var userName = $.trim($(
										'input[name="user.userName"]').val());
								if (queryStr == '' && dateFrom == ''
										&& dateTo == '' && state == ''
										&& userName == '' && pName == '') {
									$.messager.alert('提示', "请至少选择一个查询条件",
											'warning');
									return false;
								}
								if ((dateFrom == '' && dateTo != '')
										|| (dateFrom != '' && dateTo == '')) {
									$.messager.alert('提示', "请输入完整的日期区间",
											'warning');
									return false;
								}
								var params = {
									'queryStr' : queryStr,
									'field' : name,
									'dateFrom' : dateFrom,
									'dateTo' : dateTo,
									'state' : state,
									'userName' : userName,
								};
								alert(JSON.stringify(params));
								$("#orders_table").datagrid('load', params);
							},
							menu : '#orders_search_menu',
							prompt : '请输入查询词',
							width : '300px'
						});

		$("#orders_remove").click(function() {

			var url = 'order/admin/deleteOrders';
			var idField = 'oid';
			crud.remove($target_table, url, idField);
		});
		$("#orders_reload").click(function() {
			crud.reload($target_table);
		})

	})
</script>
