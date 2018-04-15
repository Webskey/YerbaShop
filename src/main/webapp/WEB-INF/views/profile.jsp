<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
td {
	padding: 5px;
}

.body {
	text-align: left;
	padding: 10px;
}

#info {
	width: 95%;
	margin: auto;
	padding: 5%;
	font-size: 20px;
}

#regForm {
	margin: auto;
	margin-top: 10%;
}

.field {
	padding: 10px;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}

.valinfo {
	font-size: 12px;
	padding: 3%;
}

.inputErr {
	border: 2px solid red;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${flag == false}">

			<div class="body">
			<h3>Your details:</h3>
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
					<tr>
						<td colspan=2><form:form method="GET"
								action="/YerbaShop/profile/change">
								<input type="submit" value="Change user details" />
							</form:form></td>
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
		</c:when>

		<c:otherwise>
			<div id="regForm">
				<form:form method="POST" action="/YerbaShop/profile/changed"
					modelAttribute="user">
					<form:errors path="*" cssClass="errorblock" element="div" />

					<table id="regForm">
						<tr>
							<td>Login</td>
							<td class="field">${username}</td>
						</tr>
						<tr>
					<spring:bind path="password">
						<td><form:label path="password">Password <span>(*)</span>
							</form:label></td>
						<td class="field"><form:password
								class="form-group ${status.error ? 'inputErr' : 'inpu'}"
								path="password" /></td>
						<td class="valinfo">5-10 characters.</td>
					</spring:bind>
				</tr>
						<tr>
							<td><form:label path="firstname">Name</form:label></td>
							<td class="field"><form:input class="inpu" path="firstname"
									value="${firstname}" /></td>
						</tr>
						<tr>
							<td><form:label path="lastname">Surname</form:label></td>
							<td class="field"><form:input class="inpu" path="lastname"
									value="${lastname}" /></td>
						</tr>
						<tr>
							<spring:bind path="email">
								<td><form:label path="email">Email
							</form:label></td>
								<td class="field"><form:input
										class="form-group ${status.error ? 'inputErr' : 'inpu'}"
										path="email" value="${email}" /></td>
							</spring:bind>
						</tr>
						<tr>
							<td><form:label path="adress">Adress</form:label></td>
							<td class="field" colspan=2><form:textarea path="adress"
									rows="5" cols="30"/></td>
						</tr>
						<tr>
							<spring:bind path="phoneNr">
								<td><form:label path="phoneNr">Phone Number</form:label></td>
								<td class="field"><form:input
										class="form-group ${status.error ? 'inputErr' : 'inpu'}"
										path="phoneNr" value="${phoneNr}" /></td>
								<td class="valinfo">9 numbers only e.g. 123456789</td>
							</spring:bind>
						</tr>
						<tr>
							<td class="field" colspan=2><input type="submit"
								value="Change" /></td>
						</tr>
					</table>
				</form:form>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>