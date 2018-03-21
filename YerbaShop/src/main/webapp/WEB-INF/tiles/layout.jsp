<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title"/> </title>
<style>
         
       body{
        	 background-color:RGB(224,238,185);
         }
         
       #header{
             width:80%;
      	 	 margin:auto;
         }
         
       #menu{
         border: 5px solid RGB(217,162,44);
         background-color:RGB(117,173,36);
         margin-left:10%;
         width:10%;
         
         margin-top: 10px;
        position:fixed;
        }
         
         
         #body {
            margin-left:25%;
            width: 48%;
        	border: 1px solid RGB(217,162,44);
            position:absolute;
          
           }
        #log-in{
         border: 5px solid RGB(217,162,44);
         background-color:RGB(117,173,36);
         margin-left:76%;
         
         width:12%;
         
         height:190px;
         margin-top: 10px;
         
         text-align:center;
         position:fixed;
         }
        
        
          </style>
</head>
<body>
		<div id="header"><tiles:insertAttribute name="header" /></div>
		<div id="menu"><tiles:insertAttribute name="menu" /></div>
		 <div id="body"><tiles:insertAttribute name="body" /></div>
		 <div id="log-in"><tiles:insertAttribute name="login" /></div>
		
    	

</body>
</html>
