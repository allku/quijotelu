<%-- 
    Document   : index
    Created on : 20/11/2014, 9:25:43
    Author     : jorjoluiso
--%>
<%@page import="com.jorge.propiedades.General"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    General general = new General();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=general.Nombre%> Electrónico</title>
        <!-- Bootstrap core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>

        
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value="Inicio"/>
        </jsp:include>

        <div class="container">
            <h1>Aplicación para firmar y emitir documentos electrónicos</h1>
            <div class="panel panel-default">
                <div class="panel-heading">Menú principal</div>
                <div class="panel-body">
                    <a href="factura_autorizada.jsp">Facturas autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="factura_no_autorizada.jsp">Facturas no autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="nota_credito_autorizada.jsp">Notas de crédito autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="nota_credito_no_autorizada.jsp">Notas de crédito no autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="retencion_autorizada.jsp">Retenciones autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="retencion_no_autorizada.jsp">Retenciones no autorizadas</a>    
                </div>
                <div class="panel-body">
                    <a href="guia_remision_autorizada.jsp">Guías de remisión autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="guia_remision_no_autorizada.jsp">Guías de remisión no autorizadas</a>
                </div>
                <div class="panel-body">
                    <a href="autorizar_facturas.jsp">Autorizar facturas</a>
                </div>
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>        
    </body>
</html>
