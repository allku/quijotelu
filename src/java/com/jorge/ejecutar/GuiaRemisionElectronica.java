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

import com.imprimir.pdf.GuiaRemisionPDF;
import com.jorge.db.Consultas;
import com.jorge.db.DataBaseConnection;
import com.jorge.electronico.ComprobanteElectronico;
import com.jorge.electronico.GrabaRespuesta;
import com.jorge.firma.XAdESBESSignature;
import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.propiedades.MotorConfiguracion;
import com.jorge.propiedades.SRIConfiguracion;
import com.xml.GeneraGuiaRemision;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */
public class GuiaRemisionElectronica {

    String codigoComprobante;
    String numeroComprobante;

    public GuiaRemisionElectronica(String CodigoComprobante, String NumeroComprobante) {
        this.codigoComprobante = CodigoComprobante;
        this.numeroComprobante = NumeroComprobante;
    }

    public String ejecutar() {
        String mensaje = null;
        String numeroAutorizacion;
        String fechaAutorizacion;
        GeneraGuiaRemision grXML = new GeneraGuiaRemision();
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

        NombreArchivo = grXML.genera(codigoComprobante, numeroComprobante, RutaArchivoGenerado);

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
            GuiaRemisionPDF pdf = new GuiaRemisionPDF(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml");
            pdf.genera(numeroAutorizacion, fechaAutorizacion);
            System.out.println("Genera reporte");
            facturasGuiaRemision();
        } else {
            GrabaRespuesta graba = new GrabaRespuesta(codigoComprobante, numeroComprobante, mensaje);
            graba.setRespuesta();
            GuiaRemisionPDF pdf = new GuiaRemisionPDF(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml");
            pdf.genera("", "");
            System.out.println("Genera reporte");
            System.out.println("No autorizó el SRI");
            System.out.println("Mensaje " + mensaje);
        }

        return mensaje;
    }

    private void facturasGuiaRemision() {
        System.out.println("Inicio de autorización de facturas en guía de remisión");
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            Connection conn;
            DataBaseConnection oc = new DataBaseConnection();
            Consultas consultas = new Consultas();
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            Statement stmt = null;
            ResultSet rs = null;
            String sql;
            stmt = conn.createStatement();
            
            sql=consultas.GuiaRemisionFactura+ " WHERE CODIGO='" + codigoComprobante + "' AND NUMERO=" + numeroComprobante;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                FacturaElectronica factura = new FacturaElectronica(rs.getString("CODIGO_FACTURA"), rs.getString("NUMERO_FACTURA"));
                factura.ejecutar();
            }
            System.out.println("Fin de autorización de facturas en guía de remisión");
        } catch (SQLException ex) {
            Logger.getLogger(GuiaRemisionElectronica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
