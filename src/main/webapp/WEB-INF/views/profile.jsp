<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
td{
padding:5px;
}
.body {
	text-align: left;
	padding: 10px;
}
#info{
width:95%;
margin:auto;
font-size:20px;
}
</style>
</head>
<body>
	<div class="body">
		<table id="info">
			<tr>
				<td>Login:</td>
				<td>${username}</td>
			</tr>
			<tr>
				<td>Name:</td>
				<td>${firstname}</td>
			</tr>
			<tr>
				<td>Lastname:</td>
				<td>${lastname}</td>
			</tr>
			<tr>
				<td>Email:</td>
				<td>${email}</td>
			</tr>
			<tr>
				<td>Home adress:</td>
				<td>${adress}</td>
			</tr>
			<tr>
				<td>Phone number:</td>
				<td>${phoneNr}</td>
			</tr>
		</table>
		<h3>Your Orders:</h3>
		<c:forEach var="order" items="${orderList}">
			<table>
				<tr>
					<td><strong>ID:</strong> ${order.id}</td>
					<td><strong>Date:</strong> ${order.orderDate}</td>
				</tr>
			</table>
			<ul>
				<c:forEach var="product" items="${order.productsList}">
					<li>${product.name}</li>
				</c:forEach>
			</ul>
		</c:forEach>
	</div>
</body>
</html>