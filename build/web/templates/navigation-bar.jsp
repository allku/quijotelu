<%-- 
    Document   : navigation-bar
    Created on : 06-nov-2015, 10:09:15
    Author     : jorjoluiso
--%>

<%@page import="com.jorge.propiedades.General"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    General general = new General();
%>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp"> <% if (general.Publicidad.equals("si")) {
                    out.print("Quijotelu");
                } else {
                    out.print("Facturación Electrónica");
                } %></a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <%
                    String opcion = request.getParameter("active");

                    String[] activo = {"Inicio", "Empresa", "Acerca", "Contacto"};

                    if (opcion.equals("Inicio")) {
                        activo[0] = "class=\"active\"";
                        activo[1] = "";
                        activo[2] = "";
                        activo[3] = "";
                    } else if (opcion.equals("Empresa")) {
                        activo[0] = "";
                        activo[1] = "class=\"active\"";
                        activo[2] = "";
                        activo[3] = "";
                    } else if (opcion.equals("Acerca")) {
                        activo[0] = "";
                        activo[1] = "";
                        activo[2] = "class=\"active\"";
                        activo[3] = "";
                    } else if (opcion.equals("Contacto")) {
                        activo[0] = "";
                        activo[1] = "";
                        activo[2] = "";
                        activo[3] = "class=\"active\"";
                    } else {
                        activo[0] = "";
                        activo[1] = "";
                        activo[2] = "";
                        activo[3] = "";
                    }
                %>                
                <li <%=activo[0]%>><a href="index.jsp">Inicio</a></li>
                <li <%=activo[1]%>><a href="informacion_empresa.jsp">Empresa</a></li>
                <li <%=activo[2]%>><a href="acerca.jsp">Acerca de</a></li>

                <% if (general.Publicidad.equals("si")) {%>
                <li <%=activo[3]%>><a href="contacto.jsp">Contacto</a></li>
                    <% } %>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>