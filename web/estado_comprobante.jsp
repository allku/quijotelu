<%-- 
    Document   : estado_comprobante
    Created on : 30/12/2014, 20:37:31
    Author     : jorjoluiso
--%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.jorge.db.DataBaseConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.jorge.propiedades.MotorConfiguracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estado del comprobante electrónico</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="jumbotron">
                <h2>Estado del comprobante electrónico</h2>
                <p>
                    <%
                        String codigo = request.getParameter("codigo");
                        String numero = request.getParameter("numero");
                        MotorConfiguracion configMotor = new MotorConfiguracion();

                        DataBaseConnection oc = new DataBaseConnection();
                        Connection conn;
                        Statement stmt = null;
                        ResultSet rs;

                        conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
                    %>
                    <%
                        try {
                            stmt = conn.createStatement();

                            rs = stmt.executeQuery("select OBSERVACION from ELE_DOCUMENTO_ELECTRONICO where codigo='" + codigo + "' and NUMERO='" + numero + "'");

                            while (rs.next()) {
                                out.println("" + rs.getString(1) + "");
                            }

                            rs.close();
                            stmt.close();
                            conn.close();
                        } catch (SQLException ex) {
                            out.println("Fallo en la ejecución de la consulta");
                        }
                    %>
                </p>
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
