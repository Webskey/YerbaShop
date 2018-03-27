<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style>
#yerba-info {
	float: right;
	border: 1px solid RGB(217, 162, 44);
	width: 100%;
	text-align: center;
}

.shop-items {
	text-align: left;
}

#desc {
	margin-right: 70px;
	text-align: left;
	font-size: 13px;
}

#img {
	width: 30%;
	padding: 3%;
	float: left;
	position: relative;
}

#buttonImg {
	width: 70px;
	float: right;
	margin-right: 3%;
	margin-bottom: 3%;
}

#price {
	margin-right: 5%;
	text-align: right;
}
</style>
</head>

<body>
	<c:forEach var="product" items="${products}">
		<div id="yerba-info">
			<h3>${product.name}</h3>
			<p class="shop-items">
				<img id="img" src="${product.image}">
			<p id="desc">${product.description}</p>
			<p id="price">${product.price}.00$</p>
			<form:form method="POST" action="add-to-basket">
				<input type="image" id="buttonImg"
					src="http://www.free-icons-download.net/images/add-to-basket-icon-32057.png"
					alt="Submit Form" />
				<form:hidden path="name" value="${product.name}" />
				<form:hidden path="price" value="${product.price}" />
				<form:hidden path="image" value="${product.image}" />
				<form:hidden path="category" value="${product.category}" />
				<form:hidden path="id" value="${product.id}" />
				<form:hidden path="description" value="${product.description}" />
			</form:form>
		</div>
	</c:forEach>
</body>
</html>