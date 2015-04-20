<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<body>

<form action="http://localhost:8080/wx/uploadImage.action"  enctype="multipart/form-data" method="post">

    <label for="filePath">选择图片</label>
    <input type="file" name="fileName">

 
  <button type="submit" class="btn btn-primary">上传</button>
</form>

    <c:forEach items="${imageList}" var="user" varStatus="vs">
		<tr>
			 <td align = "center">${user.id}</td>
			 <td align = "center">${user.desc}</td>	
		 </tr>
	</c:forEach>

</body>
</html>
