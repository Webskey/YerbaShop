<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
strong {
	color: RGB(90, 10, 150);
}

h2 {
	margin-left: 20px;
	margin-right: 20px;
	padding: 5px;
}
</style>
</head>

<body>
	<h2>
		Added <strong>${productAdded.name}</strong> with price of <strong>${productAdded.price}$</strong>
		to your basket.
	</h2>
	<button onclick="goBack()">Continue Shopping</button>
	<button onclick="goBasket()">Basket</button>
</body>
<script>
	function goBack() {
		window.history.go(-1);
	}
	function goBasket() {
		window.location.href = "basket";
	}
</script>
</html>