
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
	<jsp:directive.page language="java" contentType="text/html" />
	<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<style>
.error {
	color: red;
	text-align: center;
	font-size: 30px;
	padding: 15px;
}

#loginForm {
	margin: auto;
}

.field {
	padding: 10px;
}
</style>
</head>
<body>
	<c:url value="/login" var="loginUrl" />
	<form:form name="f" action="${loginUrl}" method="post">

		<c:if test="${param.error != null}">
			<div class="error">Invalid username and/or password.</div>
		</c:if>
		<table id="loginForm">
			<tr>
				<td class="label"><label for="username">Username</label></td>
				<td class="field"><input type="text" id="username"
					name="username" value="${username}" /></td>
			</tr>
			<tr>
				<td class="label"><label for="password">Password</label></td>
				<td class="field"><input type="password" id="password"
					name="password" /></td>
			</tr>
			<tr>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<div class="form-actions">
					<td class="field"><button type="submit" class="btn">Log
							in</button></td>
				</div>
			</tr>
		</table>
	</form:form>
</body>
	</html>
</jsp:root>