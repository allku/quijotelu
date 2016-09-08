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
package com.jorge.ejecutar;

import com.jorge.electronico.ComprobanteElectronico;
import com.jorge.electronico.GrabaRespuesta;
import com.jorge.firma.XAdESBESSignature;
import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.propiedades.SRIConfiguracion;
import com.xml.GeneraNotaDebito;
import java.io.File;

/**
 *
 * @author jorjoluiso
 */
public class NotaDebitoElectronica {

    String codigoComprobante;
    String numeroComprobante;

    public NotaDebitoElectronica(String CodigoComprobante, String NumeroComprobante) {
        this.codigoComprobante = CodigoComprobante;
        this.numeroComprobante = NumeroComprobante;
    }

    public String ejecutar() {
        String mensaje = null;
        String numeroAutorizacion;
        String fechaAutorizacion;
        GeneraNotaDebito ndXML = new GeneraNotaDebito();
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        GrabaRespuesta verifcaBaseDatos = new GrabaRespuesta(codigoComprobante, numeroComprobante);

        SRIConfiguracion sriConfig = new SRIConfiguracion();
        XAdESBESSignature xadesBesFirma = new XAdESBESSignature();
        String RutaArchivoGenerado = dirConfig.getRutaArchivoGenerado();
        String RutaArchivoFirmado = dirConfig.getRutaArchivoFirmado();
        String NombreArchivo;
        String wsEnvio = sriConfig.getWebServiceEnvio();
        String wsAutoriza = sriConfig.getWebServiceAutoriza();
        ComprobanteElectronico ce = new ComprobanteElectronico();

        NombreArchivo = ndXML.genera(codigoComprobante, numeroComprobante, RutaArchivoGenerado);

        xadesBesFirma.firmar(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml", NombreArchivo + ".xml", RutaArchivoFirmado);
        if (!verifcaBaseDatos.existeComprobante()) {
            mensaje = ce.EnvioComprobante(new File(RutaArchivoFirmado + File.separatorChar + NombreArchivo + ".xml"), wsEnvio, wsAutoriza);
            System.out.println("Envió al SRI");
        } else {
            mensaje = ce.AutorizacionComprobante(new File(RutaArchivoFirmado + File.separatorChar + NombreArchivo + ".xml"), wsAutoriza);
            System.out.println("Solicito autorización al SRI");
            if (!mensaje.equals("AUTORIZADO")) {
                mensaje = ce.EnvioComprobante(new File(RutaArchivoFirmado + File.separatorChar + NombreArchivo + ".xml"), wsEnvio, wsAutoriza);
            }
        }

        if (mensaje.equals("AUTORIZADO")) {
            numeroAutorizacion = ce.getNumeroAutorizacion(NombreArchivo);
            fechaAutorizacion = ce.getFechaAutorizacion(NombreArchivo);
            GrabaRespuesta graba = new GrabaRespuesta(codigoComprobante, numeroComprobante, numeroAutorizacion, fechaAutorizacion, mensaje);
            graba.setRespuesta();
            System.out.println("Autorizó el SRI");
        } else {
            GrabaRespuesta graba = new GrabaRespuesta(codigoComprobante, numeroComprobante, mensaje);
            graba.setRespuesta();
            System.out.println("No autorizó el SRI");
            System.out.println("Mensaje " + mensaje);
        }

        return mensaje;
    }
}
