<%-- 
    Document   : acerca
    Created on : May 29, 2015, 7:25:26 PM
    Author     : jorjoluiso
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.jorge.firma.info.VerifyDateP12"%>
<%@page import="com.jorge.propiedades.SRIConfiguracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acerca de</title>
        <link rel="icon" type="image/x-icon" href="favicon.ico">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value="Acerca"/>
        </jsp:include>
        <div class="container">
            <h1>Acerca de la aplicación:</h1>
            <div class="jumbotron">                
                <p>Aplicación para firmar y emitir documentos electrónicos</p>              
            </div>
        </div>
        <div class="container">
            <h1>Acerca del ambiente de comprobantes electrónicos:</h1>
            <div class="jumbotron">                
                <%!
                    SRIConfiguracion sri = new SRIConfiguracion();
                    String ambiente = "";
                %>
                <%
                    if (sri.getAmbiente().equals("1")) {
                        ambiente = "Pruebas";
                    } else if (sri.getAmbiente().equals("2")) {
                        ambiente = "Producción";
                    } else {
                        ambiente = "No definido";
                    }
                %>
                <p>Ambiente de <%= ambiente%></p>                
            </div>
        </div>
        <div class="container">
            <h1>Acerca de la firma electrónica:</h1>
            <div class="jumbotron">   
                <%!
                    VerifyDateP12 info = new VerifyDateP12();
                    List<String> listaFirmaP12 = new ArrayList();
                %>
                <p>
                    <%
                        listaFirmaP12 = info.getInformacion();
                        int i = 0;
                        for (String str : listaFirmaP12) {
                            i++;
                            out.println(str + "<br>");
                            if (i == 3) {
                                out.println("<br>");
                                i = 0;
                            }
                        }
                    %>
                </p>              
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
