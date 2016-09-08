<%-- 
    Document   : enviar_correo
    Created on : Mar 5, 2015, 1:31:50 PM
    Author     : jorjoluiso
--%>

<%@page import="java.io.File"%>
<%@page import="com.jorge.propiedades.DirectorioConfiguracion"%>
<%@page import="com.jorge.correo.Correo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enviar correo</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="jumbotron">
                <h2>Enviar correo</h2>
                <p>
                    <%
                        if (request.getParameter("claveAcceso") != null) {
                            if (request.getParameter("correo") == null) {
                                out.println("No existe correo electrónico");
                            } else {
                                if (request.getParameter("correo").equals("null")) {
                                    out.println("No existe correo electrónico");
                                } else {
                                    DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
                                    Correo correo = new Correo();
                                    String destinatario = request.getParameter("correo");
                                    String nombreArchivo = request.getParameter("claveAcceso");

                                    correo.enviar(new File(dirConfig.getRutaArchivoAutorizado() + File.separatorChar + nombreArchivo + ".xml"), new File(dirConfig.getRutaArchivoPDF() + File.separatorChar + nombreArchivo + ".pdf"), destinatario);
                                }
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
