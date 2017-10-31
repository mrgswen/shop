<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(document).bind("contextmenu",function(e){return false;});
	$("input.text1").val("Enter your serach text here");
	var input=$("input.text1");
	var orignalVal=input.val();
	input.focus(function(){
		if($.trim(input.val())==orignalVal){
			input.val('');
		}
	});
	input.blur(function(){
		if($.trim(input.val())==''){
			input.val(orignal);
		}
	});
})
</script>
</head>
<body>
<h1>hahah</h1>
<input class="text1"> 
</body>
</html>