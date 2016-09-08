/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.ejecutar;

import com.imprimir.pdf.FacturaPDF;
import com.jorge.correo.Correo;
import com.jorge.electronico.ComprobanteElectronico;
import com.jorge.electronico.GrabaRespuesta;
import com.xml.GeneraFactura;
import com.jorge.firma.XAdESBESSignature;
import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.propiedades.SRIConfiguracion;
import java.io.File;

/**
 *
 * @author jorjoluiso
 */
public class FacturaElectronica {

    String codigoComprobante;
    String numeroComprobante;

    public FacturaElectronica(String CodigoComprobante, String NumeroComprobante) {
        this.codigoComprobante = CodigoComprobante;
        this.numeroComprobante = NumeroComprobante;
    }

    public String ejecutar() {
        String mensaje = null;
        String numeroAutorizacion;
        String fechaAutorizacion;
        GeneraFactura fXML = new GeneraFactura();
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

        NombreArchivo = fXML.genera(codigoComprobante, numeroComprobante, RutaArchivoGenerado);

        xadesBesFirma.firmar(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml", NombreArchivo + ".xml", RutaArchivoFirmado);
        if (!verifcaBaseDatos.existeComprobante()) {
            mensaje = ce.EnvioComprobante(new File(RutaArchivoFirmado + File.separatorChar + NombreArchivo + ".xml"), wsEnvio, wsAutoriza);
            System.out.println("Envió al SRI");
        } else {
            mensaje = ce.AutorizacionComprobante(new File(RutaArchivoFirmado + File.separatorChar + NombreArchivo + ".xml"), wsAutoriza);
            //mensaje = "No";
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
            FacturaPDF pdf = new FacturaPDF(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml");
            pdf.genera(numeroAutorizacion, fechaAutorizacion);
            System.out.println("Genera reporte");
            Correo correo = new Correo();
            String destinatario;
            destinatario = correo.extraer(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml");
            if (!destinatario.equals("")) {
                correo.enviar(new File(dirConfig.getRutaArchivoAutorizado() + File.separatorChar + NombreArchivo + ".xml"), new File(dirConfig.getRutaArchivoPDF() + File.separatorChar + NombreArchivo + ".pdf"), destinatario);
            }

        } else {
            GrabaRespuesta graba = new GrabaRespuesta(codigoComprobante, numeroComprobante, mensaje);
            graba.setRespuesta();
            FacturaPDF pdf = new FacturaPDF(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml");
            pdf.genera("", "");
            System.out.println("Genera reporte");

            System.out.println("No autorizó el SRI");
            System.out.println("Mensaje " + mensaje);
        }

        return mensaje;
    }
}
