<%-- 
    Document   : informacion_empresa
    Created on : 06-nov-2015, 9:53:48
    Author     : jorjoluiso
--%>

<%@page import="com.jorge.db.Consultas"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.jorge.db.DataBaseConnection"%>
<%@page import="com.jorge.propiedades.MotorConfiguracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">     
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Información de la Empresa</title>
        <link rel="icon" type="image/x-icon" href="favicon.ico">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value="Empresa"/>
        </jsp:include>
        <div class="container">
            <h1>Información de la Empresa</h1>
            <div class="jumbotron">

                <%
                    MotorConfiguracion configMotor = new MotorConfiguracion();
                    Consultas consultas = new Consultas();
                    DataBaseConnection oc = new DataBaseConnection();
                    Connection conn;

                    ResultSet rs;

                    //out.println("Fecha " + date);
                    conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());

                    try {

                        PreparedStatement preparedStatement;
                        preparedStatement = conn.prepareStatement(consultas.InformacionTributaria);

                        rs = preparedStatement.executeQuery();

                        while (rs.next()) {
                            out.println("<p>");
                            out.println("Razón Social: ");
                            out.println(rs.getString("RAZON_SOCIAL"));
                            out.println("</p>");
                            out.println("<p>");
                            out.println("Nombre Comercial: ");
                            out.println(rs.getString("NOMBRE_COMERCIAL"));
                            out.println("</p>");
                            out.println("<p>");
                            out.println("RUC: ");
                            out.println(rs.getString("RUC"));
                            out.println("</p>");
                            out.println("<p>");
                            out.println("Dirección: ");
                            out.println(rs.getString("DIRECCION"));
                            out.println("</p>");                            
                            out.println("<p>");
                            out.println("Obligado a llevar contabilidad: ");
                            out.println(rs.getString("OBLIGADO_CONTABILIDAD"));
                            out.println("</p>");
                            out.println("<p>");
                            if(rs.getString("CONTRIBUYENTE_ESPECIAL") != null){
                            out.println("Número de Contribuyente Especial: ");                            
                                out.println(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                            }                            
                            out.println("</p>");
                        }

                        rs.close();
                        preparedStatement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        out.println("Fallo en la ejecución de la consulta " + ex.getMessage());
                    }


                %>                    
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
