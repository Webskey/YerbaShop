<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
     <head>
       <meta charset="utf-8">
         <style>
          #yerba-info{
            float: right;
        	border: 1px solid RGB(217,162,44);
        	width:100%;
        	text-align:center;
          }
        
         .shop-items{
         	text-align:left;
         }
         
         #desc{
         	margin-right:70px;
         	text-align:left;
        	 font-size:13px;
         }
         
         #img{
        	 width:30%;
       		 padding:3%;
       		 float:left;
             position:relative;
         }
              
         #buttonImg{
       		 width:70px;
        }
        
        #price{
       		 margin-right:5%;
       		 text-align:right;
        }
        
        #button{
       		 float:right;
      		 margin-right:3%;
      		 margin-bottom:3%;
        }
       </style>
   </head>
 
   <body>
   	<c:forEach var="gourd" items="${gourd}">
      <div id="yerba-info">
        <h3>${gourd.name}</h3>
        <p class="shop-items"><img id="img" src="${gourd.image}">
        <p id="desc"> ${gourd.description} </p>
        <p id="price">${gourd.price}</p>
        <form:form method = "POST" action = "order">
        <BUTTON  id="button" name="submit" value="submit" type="submit">
        <IMG id="buttonImg" src="http://www.free-icons-download.net/images/add-to-basket-icon-32057.png" alt="wow"></BUTTON>
        <form:hidden path = "name" value = "${gourd.name}"  />
        <form:hidden path = "price"  value= "${gourd.price} "/>
        </form:form>
      </div>
  	</c:forEach>    

<%-- Comment
    <div id="yerba-info">
       <h3>Metal Gourd</h3>
     <p class="shop-items"><img id="img" src="https://www.yerbamarket.com/pol_il_Solidna-i-piekna-tykwa-RIOS-4875.jpg">
      <p id="desc"> Nice and fancy made of apple tree wood bombilla. It's a perfect thing for those who like apples. </p>
      <p id="price">19.99pln</p>
      <form:form method = "POST" action = "order">
       <BUTTON  id="button" name="submit" value="submit" type="submit">
    	<IMG id="buttonImg" src="http://www.free-icons-download.net/images/add-to-basket-icon-32057.png" alt="wow"></BUTTON>
        <form:hidden path = "username" value = "Metal Gourd added to basket" />
        </form:form>
     
    </div> 
    <div id="yerba-info">
       <h3>Metal Gourd</h3>
     <p class="shop-items"><img id="img" src="https://www.yerbamarket.com/pol_il_Matero-gliniane-OPTIMO-2162.jpg">
      <p id="desc"> Nice and fancy made of apple tree wood bombilla. It's a perfect thing for those who like apples. </p>
      <p id="price">29.99pln</p>
      <form:form method = "POST" action = "order">
       <BUTTON  id="button" name="submit" value="submit" type="submit">
    	<IMG id="buttonImg" src="http://www.free-icons-download.net/images/add-to-basket-icon-32057.png" alt="wow"></BUTTON>
        <form:hidden path = "username" value = "Metal Gourd added to basket" />
        </form:form>
     
    </div>
    
    <div id="yerba-info">
       <h3>Metal Gourd</h3>
     <p class="shop-items"><img id="img" src="https://www.yerbamarket.com/pol_il_Tykwa-Calabaza-Caracas-brazowa-11244.jpg">
      <p id="desc"> Nice and fancy made of apple tree wood bombilla. It's a perfect thing for those who like apples. </p>
      <p id="price">19.99pln</p>
      <form:form method = "POST" action = "order">
       <BUTTON  id="button" name="submit" value="submit" type="submit">
    	<IMG id="buttonImg" src="http://www.free-icons-download.net/images/add-to-basket-icon-32057.png" alt="wow"></BUTTON>
        <form:hidden path = "username" value = "Metal Gourd added to basket" />
        </form:form>
     
    </div>
     --%>
   </body>
</html>