<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../wx/static/css/router.css">
<link rel="stylesheet" type="text/css" href="../../wx/static/lib/css/bootstrap/bootstrap.min.css">
<title>彩蛋区图片编辑</title>
</head>
<body>
	<div class="frame">
		<div class="con">
			<form action="http://localhost:8080/wx/uploadImage.action" enctype="multipart/form-data" method="post" id="addFile">
				<input type="hidden" name="md5" id="md5" />
				<div class="row c-row" name="${imageList}">
					<c:forEach items="${imageList}" var="user" varStatus="vs">
						<div class="col-sm-6 col-md-4 c-col">
							<div class="thumbnail">
								<div class="img-box" style="background-image: url(${user.url});"></div>
								<div class="caption">
									<input type="text" value="${user.desc}" class="form-control" name="desc">
									<p>${user.createTime }</p>
									<p>
										<input type="file" class="file-btn" role="button" name="fileName">
										<a href="#" class="btn btn-primary file-a" role="button">选择图片</a>
										<a href="#" class="btn btn-default" role="button" name="save" data-id="${user.id}" data-src="${user.url}">保存</a>
									</p>
								</div>
							</div>
						</div>
					</c:forEach>
					<div class="col-sm-6 col-md-4 c-col">
						<div class="thumbnail">
							<div class="img-box">
								<span class="add-bg"></span>
								<input type="file" name="fileName" id="createFile">
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="../../wx/static/lib/js/jquery/jquery-2.0.3.min.js"></script>
	<script type="text/javascript" src="../../wx/static/lib/js/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="../../wx/static/lib/js/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="../../wx/static/js/photos.js"></script>
</body>