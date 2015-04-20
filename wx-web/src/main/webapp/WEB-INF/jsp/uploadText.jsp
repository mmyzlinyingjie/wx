<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../../wx/static/css/router.css">
<link rel="stylesheet" type="text/css" href="../../wx/static/lib/css/bootstrap/bootstrap.min.css">
<title>彩蛋区文本编辑</title>
</head>
<body>
	<div class="frame">
	<header class="head">
		<h1>文本管理</h1>
		<!-- Button trigger modal -->
<button type="button" class="btn btn-primary c-btn" data-toggle="modal" data-target="#myModal">
 添加
</button>
</header>
		<div class="table-responsive">
			<table class="table" id="list">
				<tr>
					<th>#</th>
					<th>描述</th>
					<th>内容</th>
					<th>操作</th>
				</tr><!-- c 标签 -->
				<%
					int index = 1;
				%>
				<c:forEach items="${textList}" var="user" varStatus="vs">
					<tr class="disabled">
						<td data-id="${user.id}"><%=index++%></td>
						<td>
							<input type="text" class="form-control" value="${user.desc}" name="desc" disabled>
						</td>
						<td>
							<input type="text" class="form-control" value="${user.content}" name="content" disabled>
							
						</td>
						<td>
							<input type="button" value="编辑" name="edit" class="btn btn-primary">
							<input type="button" value="删除" name="delete-text" class="btn btn-primary">
							<input type="button" value="保存" name="save-text" class="btn btn-primary">
							<input type="button" value="取消" name="cancel" class="btn btn-primary">
						</td>
					</tr>

				</c:forEach>
				
			</table>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">添加文本</h4>
				</div>
				<div class="modal-body">
					<ul class="form-inline">
						<li>
							<label>描述：
								<input type="text" class="form-control" id="desc"></label>
							</li>
							<li>
								<label>内容：
									<input type="text" class="form-control" id="content">
								</label>
							</li>
							<li>

							</li>
						</ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<input type="button" value="添加" name="add" class="btn btn-primary" id="submit-text">
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
	<script type="text/javascript" src="../../wx/static/lib/js/jquery/jquery-1.10.1.min.js"></script>
	<script type="text/javascript" src="../../wx/static/lib/js/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="../../wx/static/js/router.js"></script>


</body>
</html>
