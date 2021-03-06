<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:form="http://www.springframework.org/tags/form" version="2.0">
	<jsp:directive.page language="java" contentType="text/html" />
	<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>Please Login</title>
<style>
.input {
	width: 80%;
}

#logged {
	margin-top: 10%;
}

#loginForm {
	margin: auto;
}

#fieldset {
	margin: auto;
	width: 80%;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${pageContext.request.userPrincipal.name != null}">
			<div id="logged">
				You're logged as: <a href="/YerbaShop/profile/info">
					<h1>${pageContext.request.userPrincipal.name}</h1>
				</a> <a href="/YerbaShop/logout"><h3>Logout</h3></a> <a
					href="/YerbaShop/basket"><h3>Basket</h3></a>
			</div>
		</c:when>
		<c:otherwise>
			<c:url value="/login" var="loginUrl" />
			<form:form name="f" action="${loginUrl}" method="post">
				<fieldset id="fieldset">
					<table id="loginForm">
						<tr>
							<td><label for="username">Login</label></td>
						</tr>
						<tr>
							<td><input class="input" type="text" id="username"
								name="username" value="${username}" /></td>
						</tr>
						<tr>
							<td><label for="password">Password</label></td>
						</tr>
						<tr>
							<td><input class="input" type="password" id="password"
								name="password" /></td>
						</tr>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<tr>
							<td>
								<button type="submit" class="btn">Log in</button>
							</td>
						</tr>
					</table>

				</fieldset>
			</form:form>
			<p></p>
			<a href="/YerbaShop/register">Register</a>
		</c:otherwise>
	</c:choose>
</body>
	</html>
</jsp:root>