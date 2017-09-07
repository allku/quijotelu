<%-- 
    Document   : acerca
    Created on : May 29, 2015, 7:25:26 PM
    Author     : jorjoluiso
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acerca de</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value="Acerca"/>
        </jsp:include>
        <div class="container">
            <div class="jumbotron">
                <h1>Acerca de:</h1>
                <p>Aplicación para firmar y emitir documentos electrónicos</p>              
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
