<%-- 
    Document   : generar_autorizacion
    Created on : 01/11/2017
    Author     : jorgequiguango
--%>

<%@page import="com.jorge.ejecutar.GuiaRemisionElectronica"%>
<%@page import="com.jorge.ejecutar.NotaCreditoElectronica"%>
<%@page import="com.jorge.ejecutar.RetencionElectronica"%>
<%@page import="com.jorge.ejecutar.FacturaElectronica"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Generar Comprobante</title>
    </head>
    <body>
        <%
            try {
                String codigo = request.getParameter("codigo");
                String numero = request.getParameter("numero");
                out.println("Código " + codigo);
                out.println("Número " + numero);
                if (codigo.equals("FAC") || codigo.equals("EXP")) {
                    FacturaElectronica factura = new FacturaElectronica(codigo, numero);
                    factura.generar();
                    response.sendRedirect("factura_no_autorizada.jsp");
                    return;
                } else if (codigo.equals("RET")) {
                    RetencionElectronica retencion = new RetencionElectronica(codigo, numero);
                    response.sendRedirect("retencion_no_autorizada.jsp");
                    return;
                } else if (codigo.equals("DVC")) {
                    NotaCreditoElectronica notaCredito = new NotaCreditoElectronica(codigo, numero);
                    response.sendRedirect("nota_credito_no_autorizada.jsp");
                } else if (codigo.equals("NCC")) {
                    NotaCreditoElectronica notaCredito = new NotaCreditoElectronica(codigo, numero);
                    response.sendRedirect("nota_credito_no_autorizada.jsp");
                } else if (codigo.equals("DES")) {
                    GuiaRemisionElectronica guiaRemision = new GuiaRemisionElectronica(codigo, numero);
                    response.sendRedirect("guia_remision_no_autorizada.jsp");
                } else if (codigo.equals("GUI")) {
                    GuiaRemisionElectronica guiaRemision = new GuiaRemisionElectronica(codigo, numero);
                    response.sendRedirect("guia_remision_no_autorizada.jsp");
                }
            } catch (Exception e) {
                response.sendRedirect("index.jsp");
            }

        %>
    </body>
</html>
