<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<title>文件上传</title>
</head>
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#btnCheck").bind("click", function() {
			var mydata = {
				'id' : '3',
				'desc' : '描述',
				'content' : '回复的内容'
			};
			var domain = 'http://localhost:8080';
			$.ajax({
				url : domain + '/wx/saveText.action',            
				type : 'post',
				data : {
					data : JSON.stringify(mydata)
				},
				dataType : "json",
				success : function(data) {
					alert("请求成功");
				}
			});
		})
	})
</script>
<body>
	<input type="submit" id="btnCheck" value="提交">
</body>
</html>