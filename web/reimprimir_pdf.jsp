<%-- 
    Document   : reimprimir_pdf
    Created on : 30-sep-2016, 13:28:21
    Author     : solecito
--%>
<%@page import="java.io.File"%>
<%@page import="com.jorge.propiedades.DirectorioConfiguracion"%>
<%@page import="com.jorge.ejecutar.FacturaElectronica"%>

<%
    try {
        String claveAcceso = request.getParameter("claveAcceso");
        String codigo = request.getParameter("codigo");
        String numero = request.getParameter("numero");
        out.println("Clave Acceso  " + claveAcceso);
        out.println("Código " + codigo);
        out.println("Número " + numero);
        DirectorioConfiguracion directorio = new DirectorioConfiguracion();
        String filename = claveAcceso + ".pdf";
        if (codigo.equals("FAC") || codigo.equals("EXP")) {
            FacturaElectronica factura = new FacturaElectronica(codigo, numero);
            factura.reimprimir(claveAcceso);
            String filepath = directorio.getRutaArchivoPDF();
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + File.separatorChar + filename);
            int i;
            while ((i = fileInputStream.read()) != -1) {
                out.write(i);
            }
            fileInputStream.close();
            //response.sendRedirect("factura_autorizada.jsp"); 
            return;
        }
    } catch (Exception e) {
        response.sendRedirect("index.jsp");
    }
%>