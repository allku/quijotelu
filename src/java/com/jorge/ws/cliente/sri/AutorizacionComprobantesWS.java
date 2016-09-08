/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ws.cliente.sri;

import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.utilidades.ArchivoUtils;
import com.jorge.utilidades.XStreamUtil;
import com.thoughtworks.xstream.XStream;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantes;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesService;
import ec.gob.sri.comprobantes.ws.aut.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

/**
 *
 * @author jorjoluiso
 */
public class AutorizacionComprobantesWS {

    private AutorizacionComprobantesService service;

    public AutorizacionComprobantesWS(String wsdlLocation) throws MalformedURLException {
        /*
         URL url = new URL(wsdlLocation);
         service = new AutorizacionComprobantesService(url);
         */
        this.service = new AutorizacionComprobantesService(new URL(wsdlLocation), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesService"));

    }

    public RespuestaComprobante llamadaWSAutorizacionInd(String claveDeAcceso) {
        RespuestaComprobante response = null;
        try {
            AutorizacionComprobantes port = this.service.getAutorizacionComprobantesPort();
            response = port.autorizacionComprobante(claveDeAcceso);
        } catch (Exception e) {
            Logger.getLogger(AutorizacionComprobantesWS.class.getName()).log(Level.SEVERE, null, e);
            return response;
        }

        return response;
    }

    public static String autorizarComprobanteIndividual(String claveDeAcceso, String nombreArchivo, String urlWsdl) {
        StringBuilder mensaje = new StringBuilder();
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        try {
            String dirAutorizados = dirConfig.getRutaArchivoAutorizado();
            String dirNoAutorizados = dirConfig.getRutaArchivoNoAutorizado();

            RespuestaComprobante respuesta = null;

            for (int i = 0; i < 5; i++) {
                respuesta = new AutorizacionComprobantesWS(urlWsdl).llamadaWSAutorizacionInd(claveDeAcceso);

                if (!respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                    break;
                }
                Thread.currentThread();
                Thread.sleep(300L);
            }
            int i;
            if (respuesta != null) {
                i = 0;
                for (Autorizacion item : respuesta.getAutorizaciones().getAutorizacion()) {
                    mensaje.setLength(0);
                    mensaje.append(item.getEstado());

                    item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");

                    XStream xstream = XStreamUtil.getRespuestaXStream();
                    Writer writer = null;
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    writer = new OutputStreamWriter(outputStream, "UTF-8");
                    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

                    xstream.toXML(item, writer);
                    String xmlAutorizacion = outputStream.toString("UTF-8");
                    /*
                     Modificado por que llegan varias autorizaciones
                     y la última autorización es la buena
                     */
                    if (/*(i == 0) &&*/(item.getEstado().equals("AUTORIZADO"))) {
                        ArchivoUtils.stringToArchivo(dirAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
                        break;
                    }
                    if (item.getEstado().equals("NO AUTORIZADO")) {
                        ArchivoUtils.stringToArchivo(dirNoAutorizados + File.separator + nombreArchivo, xmlAutorizacion);
                        mensaje.append("|" + obtieneMensajesAutorizacion(item));
                        //break;
                    }
                    i++;
                }
            }

            if ((respuesta == null) || (respuesta.getAutorizaciones().getAutorizacion().isEmpty() == true)) {
                mensaje.append("TRANSMITIDO SIN RESPUESTA|Ha ocurrido un error en el proceso de la Autorización");

            }
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(AutorizacionComprobantesWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensaje.toString();
    }

    public static String obtieneMensajesAutorizacion(Autorizacion autorizacion) {
        StringBuilder mensaje = new StringBuilder();
        for (Mensaje m : autorizacion.getMensajes().getMensaje()) {
            if (m.getInformacionAdicional() != null) {
                mensaje.append("\n" + m.getMensaje() + ": " + m.getInformacionAdicional());
            } else {
                mensaje.append("\n" + m.getMensaje());
            }
        }

        return mensaje.toString();
    }

}
