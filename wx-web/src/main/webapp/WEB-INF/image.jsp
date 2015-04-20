<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

page Size : ${pageSize}
<br />
Total Posts: ${totalPosts}
<br />
Total Pages: ${totalPages}
<br />
Current Page: ${pageNumber}
<hr />

<table>
	<thead>
		<tr align="center">
			<td width="10%">Article ID</td>
			<td width="70%">Article Title</td>
			<td colspan="3">Actions</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${entryList}" var="entry">
			<tr align="center">
				<td>${entry.entryID}</td>
				<td>${entry.title}</td>
				<td><a href="viewEntry?entryID=${entry.entryID}">View</a></td>
				<td><a href="editEntry?entryID=${entry.entryID}">Edit</a></td>
				<td><a href="deleteEntry?entryID=${entry.entryID}">Delete</a></td>
			</tr>
		</c:forEach>
	</tbody>
	<tfoot>
		<tr align="center">
			<td colspan="5">
				<jsp:include page="paging_footer.jsp"></jsp:include>
			</td>
		</tr>
	</tfoot>
</table>

<hr/>
