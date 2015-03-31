<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>All Employees</title>
</head>
<body>
<h1>List Users</h1>
<h3><a href="add.html">Add More users</a></h3>

<c:if test="${!empty users}">
	<table align="left" border="1">
		<tr>
			<th>Employee ID</th>
			<th>Employee Name</th>
			<th>Employee Age</th>
			<th>Employee Balance</th>
		</tr>

		<c:forEach items="${users}" var="user">
			<tr>
				<td><c:out value="${user.id}"/></td>
				<td><c:out value="${user.email}"/></td>
				<td><c:out value="${user.age}"/></td>
				<td><c:out value="${user.account.balance}"/></td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
</html>