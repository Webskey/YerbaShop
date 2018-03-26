<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lulz</title>
</head>
<body>
	<a href="index "> Logout</a> ${username}
	<button onclick="goBack()">Go Back 2 Pages</button>


</body>
<script>
function goBack() {
    window.history.go(-2);
}
</script>
</html>