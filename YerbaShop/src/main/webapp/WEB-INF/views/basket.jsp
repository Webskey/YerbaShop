<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
<c:forEach var="product" items="${orderList}">
Prod.
  <ul>
  <li>${product.name}</li>
  <li>${product.price}</li>
</ul> 
	<form:form method = "POST" action = "remove-from-basket">
        <BUTTON  id="button" name="submit" value="submit" type="submit">
        <IMG id="buttonImg" src="http://www.clker.com/cliparts/V/S/1/q/m/Q/red-x-small-th.png" alt="wow"></BUTTON>
        <form:hidden  path="name" value="${product.name}" />
    </form:form>
  	</c:forEach>  
  	<h2>Total Price: ${priceSum}</h2>
  	<a href = "order" ><IMG id="buttonImg" src="http://lorenzospizzali.com/wp-content/uploads/2015/04/gold_place_order_cards.png" alt="wow"></a>
    Order will be send to the adress from your profile details.
</body>
</html>

