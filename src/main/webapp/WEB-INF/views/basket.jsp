<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
table {
	width: 95%;
	margin: auto;
}

table, th, td {
	border: 1px solid black;
	text-align: center;
}

h2 {
	margin-left: 10px;
}

th, td {
	padding: 15px;
}

#tableImg {
	width: 40px;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${not empty orderList}">
			<h2>Products in your basket:</h2>
			<table>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Price</th>
					<th>Remove</th>
				</tr>
				<c:forEach var="product" items="${orderList}">
					<tr>
						<td>${product.id}</td>
						<td>${product.name}</td>
						<td>${product.price}.00$</td>
						<td><form:form method="POST" action="remove-from-basket">
								<input type="image" id="tableImg"
									src="http://www.clker.com/cliparts/V/S/1/q/m/Q/red-x-small-th.png"
									alt="Submit Form" />
								<form:hidden path="name" value="${product.name}" />
							</form:form></td>
					</tr>

				</c:forEach>
			</table>
			<h2>Total Price: ${priceSum}.00$</h2>
			<form:form method="POST" action="order" modelAttribute="orderList">
				<input type="image"
					src="http://lorenzospizzali.com/wp-content/uploads/2015/04/gold_place_order_cards.png"
					alt="Submit Form" />
			</form:form>
		</c:when>
		<c:otherwise>
			<h2 align="center">Your basket is empty.</h2>
		</c:otherwise>

	</c:choose>
</body>
</html>