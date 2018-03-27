<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
h2 {
	text-align: center;
	margin: auto;
	padding: 10px;
}
</style>
</head>
<body>
	<h2>Sorry, you do not have access to this page.</h2>
	<button onclick="goBack()">Go Back</button>
</body>
<script>
	function goBack() {
		window.history.go(-1);
	}
</script>
</html>