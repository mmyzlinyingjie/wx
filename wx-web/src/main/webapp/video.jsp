<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>彩蛋视频</title>
<style>
body {
	width: 100%;
	height: 100%;
	background: #111;
}

video {
	width: 100%;
	height: auto;
	padding: 50% 0;
	position: relative;
}
</style>

</head>
<body>
	<video controls="" autoplay="" name="media"> 
		<source src="" type="video/mp4" id="source"> 
		<script>
			var url = location.search.split('?url=')[1];
			document.getElementById("source").setAttribute("src", url);
		</script>
	</video>

</body>
</html>