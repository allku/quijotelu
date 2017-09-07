<%-- 
    Document   : autorizar_facturas
    Created on : 02/01/2015, 16:54:25
    Author     : jorjoluiso
--%>
<%@page import="com.jorge.db.Reportes"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.jorge.ejecutar.FacturaElectronica"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.jorge.db.DataBaseConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.jorge.propiedades.MotorConfiguracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Autorizar Facturas</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value=""/>
        </jsp:include>
        <div class="container">
            <h2>Autorizar Facturas</h2>
            <form name="autorizar_facturas" action="autorizar_facturas.jsp" class="navbar-form navbar-form" role="search">
                <%--Fecha (dd/mm/yyyy):<input type="text" name="fecha" value="<%= new SimpleDateFormat("dd/MM/yyyy").format(new Date())%>" />--%>
                <%--<input type="submit" value="Autorizar" />--%>
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">Fecha</span>
                    <input type="text" name="fecha" value="<%= new SimpleDateFormat("dd/MM/yyyy").format(new Date())%>" class="form-control" placeholder="dd/mm/aaaa" aria-describedby="basic-addon1">
                </div>
                <button type="submit" class="btn btn-primary">Autorizar</button>
            </form>
            <div class="jumbotron">
                <%
                    MotorConfiguracion configMotor = new MotorConfiguracion();
                    Reportes reporte = new Reportes();
                    DataBaseConnection oc = new DataBaseConnection();
                    Connection conn;
                    String date;
                    ResultSet rs;
                    if (request.getParameter("fecha") == null) {
                        date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    } else {
                        date = request.getParameter("fecha");
                    }
                    //out.println("Fecha " + date);

                    conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());

                    try {

                        PreparedStatement preparedStatement;
                        preparedStatement = conn.prepareStatement(reporte.facturasNoAutorizadas);
                        preparedStatement.setString(1, date);
                        rs = preparedStatement.executeQuery();
                        while (rs.next()) {
                            out.println("<p>");
                            out.println(rs.getString(1) + " ");
                            out.println(rs.getString(2) + " PROCESADA");
                            out.println("</p>");
                            //out.println("<br>");
                            FacturaElectronica factura = new FacturaElectronica(rs.getString(1), rs.getString(2));
                            factura.ejecutar();
                        }

                        rs.close();
                        preparedStatement.close();
                        conn.close();
                    } catch (SQLException ex) {
                        out.println("Fallo en la ejecución de la consulta");
                    }


                %>      
            </div>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>      
    </body>
</html>
