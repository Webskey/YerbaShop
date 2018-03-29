<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
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

span {
	color: red;
}

h1, h5 {
	text-align: center;
}

.valinfo {
	font-size: 12px;
	padding: 3%;
}

.inpu {
	width: 95%;
}

.inputErr {
	border: 2px solid red;
}
</style>
</head>
<body>

	<h1>Registration</h1>
	<h5>
		Fields marked with <span>(*)</span> are required.
	</h5>

	<div id="regForm">
		<form:form method="POST" action="reg" modelAttribute="user">
			<form:errors path="*" cssClass="errorblock" element="div" />

			<table id="regForm">
				<tr>
					<spring:bind path="username">
						<td class="label"><form:label path="username">Username <span>(*)</span>
							</form:label></td>
						<td class="field"><form:input
								class="form-group ${status.error ? 'inputErr' : 'inpu'}"
								path="username" /></td>
						<td class="valinfo">4-15 characters.</td>
					</spring:bind>
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
					<td class="field"><form:input class="inpu" path="firstname" /></td>
				</tr>
				<tr>
					<td><form:label path="lastname">Surname</form:label></td>
					<td class="field"><form:input class="inpu" path="lastname" /></td>
				</tr>
				<tr>
					<spring:bind path="email">
						<td><form:label path="email">Email <span>(*)</span>
							</form:label></td>
						<td class="field"><form:input
								class="form-group ${status.error ? 'inputErr' : 'inpu'}"
								path="email" /></td>
					</spring:bind>
				</tr>
				<tr>
					<td><form:label path="adress">Adress</form:label></td>
					<td class="field" colspan=2><form:textarea path="adress"
							rows="5" cols="30" /></td>
				</tr>
				<tr>
					<spring:bind path="phoneNr">
						<td><form:label path="phoneNr">Phone Number</form:label></td>
						<td class="field"><form:input
								class="form-group ${status.error ? 'inputErr' : 'inpu'}"
								path="phoneNr" /></td>
						<td class="valinfo">9 numbers only e.g. 123456789</td>
					</spring:bind>
				</tr>
				<tr>
					<td class="field" colspan=2><input type="submit"
						value="Register" /></td>
				</tr>
			</table>
		</form:form>
	</div>
</body>
</html>