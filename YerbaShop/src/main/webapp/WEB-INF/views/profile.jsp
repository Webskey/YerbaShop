<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
	.body{
         text-align:left;
         padding:10px;
        }
        
</style>
</head>
<body>
 <div class = "body">
   <h3>Login: ${username}</h3>
   <h3>Name: ${firstname}</h3>
   <h3>Lastname: ${lastname}</h3>
   <h3>Email: ${email}</h3>
   <h3>Home adress: ${adress}</h3>
   <h3>Phone number: ${phoneNr}</h3>
   
  <c:forEach var="order" items="${orderList}">
   	<h3>Orders: ${order.id}</h3>
   		<c:forEach var="product" items="${order.productsList}">
   			 ${product.name}
   		</c:forEach> 	
  </c:forEach> 	
   </div>
</body>
</html>