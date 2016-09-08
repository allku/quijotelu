<%-- 
    Document   : PruebaConexion
    Created on : 20/11/2014, 17:02:19
    Author     : jorjoluiso
--%>

<%@page import="com.jorge.propiedades.General"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Prueba la conexión con la base de datos</title>
    </head>
    <body>
        <h1>Prueba la conexión con la base de datos</h1>
        <%
            General general = new General();
            MotorConfiguracion configMotor = new MotorConfiguracion();

            DataBaseConnection oc = new DataBaseConnection();
            Connection conn;
            Statement stmt = null;
            ResultSet rs = null;

            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            try {
                stmt = conn.createStatement();
                if (general.BaseDatos.equals("oracle")) {
                    rs = stmt.executeQuery("select sysdate from dual");
                    out.println("Prueba Oracle");
                } else if (general.BaseDatos.equals("sqlserver")) {
                    rs = stmt.executeQuery("select getdate()");
                    out.println("Prueba SqlServer");
                }
                while (rs.next()) {
                    out.println("Fecha: " + rs.getString(1));
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                out.println("Fallo en la ejecución de la consulta");
            }

        %>
    </body>
</html>
