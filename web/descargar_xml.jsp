<%@page import="java.io.File"%>
<%@page import="com.jorge.propiedades.DirectorioConfiguracion"%>
<%
    DirectorioConfiguracion directorio = new DirectorioConfiguracion();
    String claveAcceso = request.getParameter("claveAcceso");
    String filename = claveAcceso + ".xml";
    //String filepath = "/app/quijotelu/autorizado/"; 
    String filepath = directorio.getRutaArchivoAutorizado();
    //response.setContentType("APPLICATION/OCTET-STREAM"); 
    response.setContentType("text/plain");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
    File file = new File(filepath + File.separatorChar + filename);
    java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
    out.clear();
    int i;
    while ((i = fileInputStream.read()) != -1) {
        out.write(i);
    }    
    out.flush();
    fileInputStream.close();
%> 