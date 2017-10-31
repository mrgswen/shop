$(function() {
		$('#managerTable').datagrid({
			url : 'admin/getAdmins',
			fit : true,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			border : false,
			pagination : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			pageNumer : 1,
			sortName : 'date',
			sortOrder : 'desc',
			columns : [ [ {
				field : 'adminId',
				title : 'id',
				width : 100
			}, {
				field : 'adminName',
				title : 'name',
				width : 100
			}, {
				field : 'password',
				title : 'password',
				width : 100
			}, {
				field : 'createdTime',
				title : '创建时间',
				width : 100
			}

			] ]
		})
})