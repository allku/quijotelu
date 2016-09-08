
<%@page import="java.io.File"%>
<%@page import="com.jorge.propiedades.DirectorioConfiguracion"%>
<%
    DirectorioConfiguracion directorio = new DirectorioConfiguracion();
    String claveAcceso = request.getParameter("claveAcceso");
    String filename = claveAcceso + ".pdf";
    //String filepath = "/app/quijotelu/pdf/";
    String filepath = directorio.getRutaArchivoPDF();
    response.setContentType("APPLICATION/OCTET-STREAM");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath + File.separatorChar + filename);

    int i;
    while ((i = fileInputStream.read()) != -1) {
        out.write(i);
    }
    fileInputStream.close();

%> 