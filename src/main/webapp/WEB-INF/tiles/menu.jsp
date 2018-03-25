<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <head>
   <style>
    	p{
         text-align:center;
         }
       .menu-main{
          font-size:20px;
         }
          .menu-sub{
          font-size:18px;
         }
          .menu-sub-sub{
          font-size:15px;
         }
   </style>      
   </head>
   <body>

     <p class = "menu-main"><a href = "index" >HOME</a> </p>
        <p class = "menu-main"><a href = "products?cat=" >PRODUCTS</a>  </p>
        <p class = "menu-sub"><a href = "products?cat=Yerba" >Yerba Mate</a> </p>
          <p class = "menu-sub-sub"><a href = "products?cat=classic" >Classic</a> </p>
         <p class = "menu-sub-sub"><a href = "products?cat=flavoured" >Flavoured</a>  </p>
         <p class = "menu-sub"><a href = "products?cat=Accesories" >Accesories</a> </p>
         <p class = "menu-sub-sub"> <a href = "products?cat=gourds" >Gourds</a> </p>
         <p class = "menu-sub-sub"> <a href = "products?cat=bombillas" >Bombillas</a> </p>
         
         
   </body>
</html>