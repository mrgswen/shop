<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<table id="categoryTable"></table>
<div id='category_toolbar' style="padding: 5px">
	<div style="margin-bottom: 5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="category_add"
			>添加</a> <a href="#" class="easyui-linkbutton" id="category_edit"
			iconCls="icon-edit" plain="true" >编辑</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="category_remove"
			>删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="category_reload"
			>刷新</a>
	</div>
	<div style="padding: 0 0 0 7px; color: #333;">
		查询一级分类：<input type="text" class="textbox"  name="search_category"
			style="width:220px">
		<a href="#" class="easyui-linkbutton"  id="category_search" iconCls="icon-search"
			>查询</a>
	</div>

	<form id="category_form" method='post'
		style="margin: 0; padding: 5px 0 0 25px;">
		<input type="hidden" name="cid" class="textbox"
			style="width: 200px;">
		<p>
			分类名称：<input type="text" name="cname" class="textbox"
				style="width: 200px;">
		</p>
	</form>

</div>
<script type="text/javascript">

	$(function() {
		
		
		$('#categoryTable').datagrid({
			url : 'admin/getCategorys',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			border : false,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			pageNumer : 1,
			toolbar : '#category_toolbar',
			columns : [ [ {
				field : 'cid',
				title : 'id',
				width : 100,
				checkbox : true
			}, {
				field : 'cname',
				title : '分类名称',
				width : 100
			}, 

			] ]
		});

		$('#category_form').dialog({
			iconCls : 'icon-category',
			width : 350,
			modal : true,
			closed : true,
			buttons : [ {
				text : '提交',
				iconCls : 'icon-add-new',
				handler : function() {
					crud.save($target_form,$target_table)
				},

			}, {
				text : '取消',
				iconCls : 'icon-redo',
				handler : function() {
					$('#manager_add').dialog('close').form('reset');
				}
			},

			]
		});

		$('input[name="cname"]').validatebox({
			required : true,
			validType : 'length[2,10]',
			missingMessage : '请输入分类名',
			invalidMessage : '分类名称长度为 2-10 位',
		});
		
		var $target_form=$('#category_form')
		var $target_table=$("#categoryTable");
		var $focus_field='input[name="cname"]';
		var add_url='admin/addCategory';
		var edit_url='admin/updateCategory';
		var remove_url='admin/deleteCategory';
		$('#category_add').click(function(){
			crud.add($target_form,$focus_field,add_url)
			});
		$("#category_edit").click(function(){
			crud.edit($target_form,$target_table,edit_url)
		});
		$("#category_remove").click(function(){
			crud.remove($target_table,remove_url,'cid');
		});
		
		$("#category_search").click(function(){
			var queryStr=$.trim($('input[name="search_category"]').val());
			var params={
					'cname':queryStr,
				};
			if(queryStr==''){
				$.messager.alert('提示', "请输入查询条件", 'warning');
				return false;
			}
			crud.search(params,$target_table);
		});
		$("#category_reload").click(function(){
			crud.reload($target_table);
		});
	})
</script>
