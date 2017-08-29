<%-- 
    Document   : nota_credito_no_autorizada
    Created on : 14/01/2015, 10:28:47
    Author     : jorjoluiso
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.jorge.db.Reportes"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.jorge.db.DataBaseConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.jorge.propiedades.MotorConfiguracion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notas de crédito no autorizadas</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value=""/>
        </jsp:include>
        <div class="container">
            <h2>Notas de crédito no autorizadas</h2>
            <form name="consulta_no_autorizada" action="nota_credito_no_autorizada.jsp" class="navbar-form navbar-form" role="search">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">Fecha</span>
                    <input type="text" name="fecha" value="<%= new SimpleDateFormat("dd/MM/yyyy").format(new Date())%>" class="form-control" placeholder="dd/mm/aaaa" aria-describedby="basic-addon1">
                </div>
                <button type="submit" class="btn btn-primary">Consultar</button>
            </form>

            <%
                MotorConfiguracion configMotor = new MotorConfiguracion();
                Reportes reporte = new Reportes();
                DataBaseConnection oc = new DataBaseConnection();
                Connection conn;
                ResultSet rs;
                String date;
                conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            %>
            <%
                try {
                    PreparedStatement preparedStatement;
                    preparedStatement = conn.prepareStatement(reporte.notasCreditoNoAutorizadas);

                    if (request.getParameter("fecha") == null) {
                        date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                    } else {
                        date = request.getParameter("fecha");
                    }

                    preparedStatement.setString(1, date);
                    rs = preparedStatement.executeQuery();
            %>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Número</th>
                        <th>Fecha</th>
                        <th>Tarifa 12</th>
                        <th>Tarifa 0</th>
                        <th>IVA</th>
                        <th>Total</th>
                        <th>Documento</th>
                        <th>Razón social</th>
                        <th>Correo electrónico</th>
                        <th>Estado</th>
                        <th>Autorizar</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                            while (rs.next()) {
                                out.println("<tr>");
                                out.println("<td>" + rs.getString(1) + "</td>");
                                out.println("<td>" + rs.getString(2) + "</td>");
                                out.println("<td>" + rs.getString(3) + "</td>");
                                out.println("<td>" + rs.getString(4) + "</td>");
                                out.println("<td>" + rs.getString(5) + "</td>");
                                out.println("<td>" + rs.getString(6) + "</td>");
                                out.println("<td>" + rs.getString(7) + "</td>");
                                out.println("<td>" + rs.getString(8) + "</td>");
                                out.println("<td>" + rs.getString(9) + "</td>");
                                out.println("<td>" + rs.getString(10) + "</td>");
                                out.println("<td><a href=\"estado_comprobante.jsp?codigo=" + rs.getString(1) + "&numero=" + rs.getString(2) + "\" target=\"_blank\">" + rs.getString(11) + "</a></td>");
                                out.println("<td><a href=\"procesar_autorizacion.jsp?codigo=" + rs.getString(1) + "&numero=" + rs.getString(2) + "\">Autorizar</a></td>");
                                out.println("</tr>");
                            }

                            rs.close();
                            preparedStatement.close();
                            conn.close();
                        } catch (SQLException ex) {
                            out.println("Fallo en la ejecución de la consulta");
                            conn.close();
                        }

                    %>                 
                </tbody>
            </table>
        </div>
        <script src="js/jquery-2.1.4.min.js"></script>
        <script src="js/bootstrap.min.js"></script>    
    </body>
</html>
