<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../wx/static/css/router.css">
<link rel="stylesheet" type="text/css" href="../../wx/static/lib/css/bootstrap/bootstrap.min.css">
<title>彩蛋区编辑</title>
</head>
<body class="bg">
	<header class="hd">
		<h1>1931粉丝公众号后台管理</h1>
	</header>
	<div class="bd">
		<section class="side">
			<ul>
				<li><a href="http://localhost:8080/wx/getRouterList.action" name="menu" target="content"/>路由管理</li>
				<li><a href="http://localhost:8080/wx/getTextList.action" name="menu" target="content"/>文本回复管理</li>
				<li><a href="http://localhost:8080/wx/getImageList.action" name="menu" target="content"/>图片管理</li>
			</ul>
		</section>
		<section class="main">
			<iframe src="" name="content" id="content"></iframe>
		</section>
	</div>
	<script type="text/javascript">
		window.onload = function(){
			document.getElementsByClassName("side")[0].addEventListener('click', function(e){
				if(e.target.name == "menu"){
					//document.getElementById("content").src = e.target.href;
					if(document.getElementsByClassName("active")[0])
					document.getElementsByClassName("active")[0].classList.remove("active");
					e.target.parentElement.className += "active";
				}
			},false);
		}
	</script>
</body>