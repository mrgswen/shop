<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<table id="user_table"></table>
<div id='user_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" id="user_remove" iconCls="icon-remove"
			plain="true">删除</a> <a href="#" class="easyui-linkbutton" id="reload"
			iconCls="icon-reload" plain="true">刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
	创建日期从：<input type="text" id="date_from"
			editable="false" name="user_date_from" style="width: 110px"> 到：<input
			type="text" id="date_to" name="user_date_to" editable="false"
			style="width: 110px"> 
			&nbsp;&nbsp;性别：<input type=text id="gender" name="gender" style="width: 110px">
			&nbsp;&nbsp;状态：<input type=text id="state" name="state" style="width: 110px">
		<input id="user_search"></input>
		<div id="user_search_menu" style="width: 120px">
			<div data-options="name:'userName'">用户名</div>
			<div data-options="name:'address'">地址</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		var $date_from=$('#date_from');
		var $date_to=$('#date_to');
		crud.extendDateBox($date_from);
		crud.extendDateBox($date_to);
		$('#user_table').datagrid({
			url : 'admin/getUsers',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			border : false,
			pagination : true,
			pageSize : 20,
			pageList : [ 20, 30, 40, 50, 60 ],
			pageNumer : 1,
			sortName : 'createTime',
			sortOrder : 'desc',
			toolbar : '#user_toolbar',
			columns : [ [ {
				field : 'id',
				title : 'id',
				width : 100,
				checkbox : true
			}, {
				field : 'userName',
				title : '账号',
				width : 100
			}, {
				field : 'password',
				title : 'password',
				hidden : true,
				width : 100
			}, 
			{
				field : 'gender',
				title : '性别',
				width : 100,
				formatter:function(value,row,index){
					if(value=="male"){
						return "男";
					}else if(value="famale"){}
					return "女";
				}
			},
			{
				field : 'address',
				title : '地址',
				width : 100,
			},{
				field : 'birth',
				title : '生日',
				width : 100,
				formatter : function(value, row, index) {
					var date = new Date(value);

					return crud.formatDate(date);
				}
				
			}, {
				field : 'email',
				title : '邮箱',
				width : 100,
			},{
				field : 'phone',
				title : '电话',
				width : 100,
			},{
				field : 'state',
				title : '状态',
				width : 100,
				formatter:function(value,row,index){
					if(value=="0"){
						return "未激活";
					}else if(value="famale"){}
					return "已激活";
				}
			},{
				field : 'createdTime',
				title : '创建时间',
				width : 100,
				formatter : function(value, row, index) {
					var datestamp = new Date(value);

					return crud.formatTimeStamp(datestamp);
				}
			},
			{
				field : 'code',
				title : '激活码',
				width : 100,
			},

			] ]
		});

		

		
		$('input[name="gender"]').combobox({
			valueField : 'value',
			textField : 'label',
			panelHeight : 60,
			editable : false,
			multiple : false,
			data : [ {
				label : '男',
				value : 'male'
			}, {
				label : '女',
				value : 'female'
			} ],
		});
		$('input[name="state"]').combobox({
			valueField : 'value',
			textField : 'label',
			panelHeight : 60,
			editable : false,
			multiple : false,
			data : [ {
				label : '未激活',
				value : '0'
			}, {
				label : '已激活',
				value : '0'
			} ],
		});
		 var $targetTable=$('#user_table');
		$('#user_search').searchbox({
		    searcher:function(value,name){
		    var queryStr=$.trim(value);
		    var dateFrom = $('input[name="user_date_from"]').val();
			var dateTo = $('input[name="user_date_to"]').val();
			var gender=$('input[name="gender"]').val();
			var state=$('input[name="state"]').val();
			if(queryStr == '' && dateFrom == '' && dateTo == ''&&gender==''&&state==''){
				$.messager.alert('提示', "请至少选择一个查询条件", 'warning');
				return false;
			}
			if((dateFrom == '' && dateTo != '')||(dateFrom != '' && dateTo == '')){
				$.messager.alert('提示', "请输入完整的日期区间", 'warning');
				return false;
			}
			var params = {
					'queryStr' : queryStr,
					'field':name,
					'dateFrom' : dateFrom,
					'dateTo' : dateTo,
					 'gender':gender,
					 'state':state,
				};
			//alert(JSON.stringify(params));
			$("#user_table").datagrid('load',params);
		    },
		    menu:'#user_search_menu',
		    prompt:'请输入查询词',
		    width:'300px'
		});
		
	   
		$("#user_remove").click(function(){
			
			var url='admin/deleteUser';
			var idField='id';
			crud.remove($targetTable,url,idField);
		});
		$("#reload").click(function(){
			crud.reload($targetTable);
		})
		

	})
</script>
