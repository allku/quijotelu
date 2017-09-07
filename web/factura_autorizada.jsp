<%-- 
    Document   : factura_autorizada
    Created on : 01/01/2015, 13:35:05
    Author     : jorjoluiso
--%>
<%@page import="com.jorge.db.Reportes"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Date"%>
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
        <title>Factura autorizada</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/starter-template.css" rel="stylesheet">

    </head>
    <body>
        <jsp:include page="templates/navigation-bar.jsp">
            <jsp:param name="active" value=""/>
        </jsp:include>
        <div class="container">
            <h2>Factura autorizada</h2>

            <form name="consulta_autorizada" action="factura_autorizada.jsp" class="navbar-form navbar-form" role="search">
                <%--Fecha (dd/mm/yyyy):<input type="text" name="fecha" value="<%= new SimpleDateFormat("dd/MM/yyyy").format(new Date())%>" />--%>
                <%--<input type="submit" value="Consultar" />--%>

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
                    preparedStatement = conn.prepareStatement(reporte.facturasAutorizadas);

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
                        <th>Total</th>
                        <th>Documento</th>
                        <th>Razón social</th>
                        <th>Correo electrónico</th>
                        <th>XML</th>
                        <th>PDF</th>
                        <th>GENERAR</th>
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
                                out.println("<td><a href=\"enviar_correo.jsp?claveAcceso=" + rs.getString(8) + "&correo=" + rs.getString(7) + "\" target=\"_blank\">" + rs.getString(7) + "</a></td>");
                                out.println("<td><a href=\"descargar_xml.jsp?claveAcceso=" + rs.getString(8) + "\">XML</a></td>");
                                out.println("<td><a href=\"descargar_pdf.jsp?claveAcceso=" + rs.getString(9) + "\">PDF</a></td>");
                                out.println("<td><a href=\"reimprimir_pdf.jsp?claveAcceso=" + rs.getString(9) + "&codigo=" + rs.getString(1) + "&numero=" + rs.getString(2) + "\">PDF</a></td>");
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
