<%-- 
    Document   : contacto
    Created on : May 29, 2015, 7:25:38 PM
    Author     : jorjoluiso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contacto</title>
        <link rel="icon" type="image/x-icon" href="favicon.ico">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value="Contacto"/>
        </jsp:include>
        <div class="container">
            <div class="jumbotron">
                <h1>Contacto</h1>
                <p>Jorge Luis Quiguango</p>
                <p>Correo electrónico: jorgequiguango@icloud.com</p>
                <p>Teléfono: 0969528480</p>
                <!--<p><a href="http://quijotelui.ec" target="_blank">quijotelui.ec</a></p>-->
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
