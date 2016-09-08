/*
 * Copyright (C) 2014 jorjoluiso
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jorge.electronico;

import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.utilidades.ArchivoUtils;
import com.jorge.ws.cliente.sri.AutorizacionComprobantesWS;
import com.jorge.ws.cliente.sri.EnvioComprobantesWS;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */
public class ComprobanteElectronico {

    DirectorioConfiguracion dirConfig;

    public ComprobanteElectronico() {
        dirConfig = new DirectorioConfiguracion();
    }

    public String getNumeroAutorizacion(String nombreArchivoAutorizado) {
        File archivoAutorizado = new File(dirConfig.getRutaArchivoAutorizado() + File.separator + nombreArchivoAutorizado + ".xml");
        return ArchivoUtils.obtenerValorXML(archivoAutorizado, "/autorizacion/numeroAutorizacion");

    }

    public String getFechaAutorizacion(String nombreArchivoAutorizado) {
        File archivoAutorizado = new File(dirConfig.getRutaArchivoAutorizado() + File.separator + nombreArchivoAutorizado + ".xml");
        return ArchivoUtils.obtenerValorXML(archivoAutorizado, "/autorizacion/fechaAutorizacion");
    }

    public String AutorizacionComprobante(File ArchivoXML, String wsAutoriza) {
        
        String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/claveAcceso");
        String tipoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/codDoc").substring(1);
        String respuestaAutoriz = null;
        if ((tipoComprobante != null) && (claveAccesoComprobante != null)) {
            respuestaAutoriz = AutorizacionComprobantesWS.autorizarComprobanteIndividual(claveAccesoComprobante, ArchivoXML.getName(), wsAutoriza);

            String estado = null;
            String resultado = null;
            if (respuestaAutoriz.lastIndexOf("|") != -1) {
                estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1, respuestaAutoriz.length());
            } else {
                estado = respuestaAutoriz;
                resultado = "Procesado";
            }

            if (respuestaAutoriz.equals("AUTORIZADO")) {
                System.out.println("El comprobante fue autorizado por el SRI");
            } else {
                System.out.println("El comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado");

            }
        }
        
        return respuestaAutoriz;
    }

    public String EnvioComprobante(File ArchivoXML, String wsEnvio, String wsAutoriza) {
        
        RespuestaSolicitud respuestaRecepcion = new RespuestaSolicitud();

        try {
            String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/claveAcceso");
            String tipoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/codDoc").substring(1);

            if ((tipoComprobante != null) && (claveAccesoComprobante != null)) {
                respuestaRecepcion = EnvioComprobantesWS.obtenerRespuestaEnvio(ArchivoXML, claveAccesoComprobante, wsEnvio);
                if (respuestaRecepcion.getEstado().equals("RECIBIDA")) {
                    Thread.currentThread();
                    Thread.sleep(10 * 1000);
                    String respuestaAutoriz = AutorizacionComprobantesWS.autorizarComprobanteIndividual(claveAccesoComprobante, ArchivoXML.getName(), wsAutoriza);

                    String estado = null;
                    String resultado = null;
                    if (respuestaAutoriz.lastIndexOf("|") != -1) {
                        estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                        resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1, respuestaAutoriz.length());
                    } else {
                        estado = respuestaAutoriz;
                        resultado = "Procesado";
                    }

                    if (respuestaAutoriz.equals("AUTORIZADO")) {
                        System.out.println("El comprobante fue autorizado por el SRI");
                    } else {
                        System.out.println("El comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado");

                    }

                    
                    return respuestaAutoriz;
                } else if (respuestaRecepcion.getEstado().equals("DEVUELTA")) {
                    String resultado = EnvioComprobantesWS.obtenerMensajeRespuesta(respuestaRecepcion);

                    String dirRechazados = dirConfig.getRutaArchivoNoAutorizado() + File.separator + "rechazados";

                    ArchivoUtils.anadirMotivosRechazo(ArchivoXML, respuestaRecepcion);

                    File rechazados = new File(dirRechazados);
                    if (!rechazados.exists()) {
                        new File(dirRechazados).mkdir();
                    }

                    if (!ArchivoUtils.copiarArchivo(ArchivoXML, rechazados.getPath() + File.separator + ArchivoXML.getName())) {
                        System.out.println("Error al mover archivo a carpeta rechazados");
                    } else {
                        
                    }
                    System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
                    return resultado;
                }

            } else {
                String m = " En: " + ArchivoXML.getName() + " la información <codDoc> y <claveAcceso> son obligatorias para el envio del archivo";
                System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
                return "ERROR EN ARCHIVO";
            }
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronico.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
        }
        return "ERROR EN FacturaElectronica - EnvioComprobante";
    }
}
