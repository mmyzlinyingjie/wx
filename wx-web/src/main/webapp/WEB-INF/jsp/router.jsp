<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css"
	href="../../wx/static/css/router.css">
<link rel="stylesheet" type="text/css"
	href="../../wx/static/lib/css/bootstrap/bootstrap.min.css">
<title>彩蛋区路由编辑</title>
</head>
<body>
	<div class="frame" id="list">
	<header class="head">
		<h1>路由管理</h1>
		<!-- Button trigger modal -->
		<button type="button" class="btn btn-primary c-btn"
			data-toggle="modal" data-target="#myModal" id="add">添加</button>
	</header>
		<div class="table-responsive">
			<table class="table table-striped">
				<tr>
					<th>#</th>
					<th>拦截内容</th>
					<th>回复类型</th>
					<th>回复内容</th>
					<th>拦截序列</th>
					<th>操作</th>
				</tr>

				<!-- c 标签 -->
				<%
					int index = 1;
				%>
				<c:forEach items="${routerList}" var="router" varStatus="vs">
					<tr class="disabled">
						<td data-id="${router.id}" name="id"><%=index++%></td>
						<td><input type="text" class="form-control"
							value="${router.intercept}" disabled name="intercept"></td>
						<td><select name="selectType" class="form-control" disabled>
								<c:choose>
									<c:when test="${router.type==1}">
										<option value="1" selected>文本内容</option>
										<option value="2">图片</option>
										<option value="3">语音</option>
									</c:when>
									<c:when test="${router.type==2}">
										<option value="1">文本内容</option>
										<option value="2" selected>图片</option>
										<option value="3">语音</option>
									</c:when>
									<c:when test="${router.type==3}">
										<option value="1">文本内容</option>
										<option value="2">图片</option>
										<option value="3" selected>语音</option>
									</c:when>
								</c:choose>
						</select></td>
						<td name="type_id"><select name="content"
							class="form-control" disabled>
								<option value="${router.type_id}" selected>${router.desc}</option>
						</select></td>
						<td>
						<input type="text" class="form-control"	value="${router.sortord}" disabled></td>
						<td><input type="button" value="编辑" name="edit"
							class="btn btn-primary"> <input type="button" value="删除"
							name="delete" class="btn btn-primary"> <input
							type="button" value="保存" name="save" class="btn btn-primary">
							<input type="button" value="取消" name="cancel"
							class="btn btn-primary"></td>
					</tr>
				</c:forEach>

			</table>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">添加文本</h4>
					</div>
					<div class="modal-body">
						<ul class="form-inline">
							<li><label> 拦截字段： <input type="text"
									class="form-control" id="intercept">
							</label></li>
							<li><label> 拦截序列： <input type="number" min="0"
									class="form-control" id="sortord">
							</label></li>
							<li><label> 回复类型： <select name="selectType"
									class="form-control" id="type">
										<option value="1">文本内容</option>
										<option value="2">图片</option>
								</select>
							</label></li>
							<li><label data-typeid=""> 回复内容： <select
									name="content" class="form-control" id="type_id">
								</select>
							</label></li>
						</ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="button" value="添加" name="add" class="btn btn-primary"
							id="submit">
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->

	</div>
	<script type="text/javascript"
		src="../../wx/static/lib/js/jquery/jquery-1.10.1.min.js"></script>
	<script type="text/javascript"
		src="../../wx/static/lib/js/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="../../wx/static/js/router.js"></script>
</body>
</html>

