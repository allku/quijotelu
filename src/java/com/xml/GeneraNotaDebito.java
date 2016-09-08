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
package com.xml;

import com.jorge.datos.CampoAdicional;
import com.jorge.datos.InformacionAdicional;
import com.jorge.datos.InformacionTributaria;
import com.jorge.datos.nota.debito.Impuesto;
import com.jorge.datos.nota.debito.Impuestos;
import com.jorge.datos.nota.debito.InformacionNotaDebito;
import com.jorge.datos.nota.debito.Motivo;
import com.jorge.datos.nota.debito.Motivos;
import com.jorge.datos.nota.debito.NotaDebito;
import com.jorge.db.Consultas;
import com.jorge.db.DataBaseConnection;
import com.jorge.propiedades.MotorConfiguracion;
import com.jorge.propiedades.SRIConfiguracion;
import com.jorge.utilidades.Modulo11;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author jorjoluiso
 */
public class GeneraNotaDebito {
    
    NotaDebito notaDebito = new NotaDebito();
    InformacionTributaria infoT = new InformacionTributaria();
    InformacionNotaDebito infoND = new InformacionNotaDebito();
    Motivos motivos;
    InformacionAdicional informacionAdicional = new InformacionAdicional();
    
    void generaArchivo(String RutaArchivo, String NombreArchivo) {
        notaDebito.setId("comprobante");
        notaDebito.setVersion("1.0.0");
        
        OutputStreamWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{NotaDebito.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            out = new OutputStreamWriter(new FileOutputStream(RutaArchivo + File.separatorChar + NombreArchivo + ".xml"), "UTF-8");
            marshaller.marshal(notaDebito, out);
            System.out.println("Archivo nota de crédito " + NombreArchivo + " generado en " + RutaArchivo);
        } catch (JAXBException | UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(GeneraNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void generaInformacion(Connection conn, String consulta) {
        try {
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                infoT.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                infoT.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
                infoT.setRuc(rs.getString("RUC"));
                infoT.setDirMatriz(rs.getString("DIRECCION"));
                infoND.setDirEstablecimiento(rs.getString("DIRECCION"));
                infoND.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                infoND.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
            }
            rs.close();
            stmt.close();
            
            System.out.println("SQL Consultas.InformacionTributaria nota de crédito correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    void generaMaestro(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                infoT.setCodDoc(rs.getString("CODIGO_DOCUMENTO"));
                infoT.setEstab(rs.getString("ESTABLECIMIENTO"));
                infoT.setPtoEmi(rs.getString("PUNTO_EMISION"));
                infoT.setSecuencial(rs.getString("SECUENCIAL"));
                infoND.setFechaEmision(rs.getString("FECHA"));
                infoND.setTipoIdentificacionComprador(rs.getString("TIPO_DOCUMENTO"));
                infoND.setRazonSocialComprador(rs.getString("RAZON_SOCIAL"));
                infoND.setIdentificacionComprador(rs.getString("DOCUMENTO"));
                infoND.setCodDocModificado(rs.getString("DOCUMENTO_MODIFICADO"));
                infoND.setNumDocModificado(rs.getString("MODIFICADO"));
                infoND.setFechaEmisionDocSustento(rs.getString("FECHA_MODIFICADO"));
                infoND.setTotalSinImpuestos(rs.getFloat("TOTAL_SIN_IMPUESTOS"));
                
                List<Impuesto> impuesto = new ArrayList<>();
                impuesto.add(new Impuesto("2", "7", 0, infoND.getTotalSinImpuestos(), 0));
                infoND.setImpuestos(new Impuestos(impuesto));
                infoND.setValorTotal(infoND.getTotalSinImpuestos());
                
                List<CampoAdicional> campoAdicional = new ArrayList<>();
                if (rs.getString("DIRECCION") != null) {
                    campoAdicional.add(new CampoAdicional("Dirección", rs.getString("DIRECCION")));
                }
                if (rs.getString("TELEFONO") != null) {
                    campoAdicional.add(new CampoAdicional("Teléfono", rs.getString("TELEFONO")));
                }
                if (rs.getString("MAIL") != null) {
                    campoAdicional.add(new CampoAdicional("Email", rs.getString("MAIL")));
                }
                informacionAdicional.setCampoAdicional(campoAdicional);
            }
            rs.close();
            stmt.close();
            
            System.out.println("SQL Consultas.NotaCreditoMaestro correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void generaDetalle(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt;
            ResultSet rs;
            List<Motivo> motivo = new ArrayList<>();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                motivo.add(new Motivo(rs.getString("MOTIVO"), rs.getFloat("VALOR")));
            }
            motivos = new Motivos(motivo);
            System.out.println("SQL Consultas.NotaCreditoDetalle correcto");
            rs.close();
            stmt.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String genera(String CodigoNotaCredito, String NumeroNotaCredito, String RutaArchivo) {
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            Consultas consultas = new Consultas();
            SRIConfiguracion sri = new SRIConfiguracion();
            DataBaseConnection oc = new DataBaseConnection();
            
            Modulo11 m11 = new Modulo11();
            Connection conn;
            Date fecha = null;
            String ClaveAcceso;
            
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            generaInformacion(conn, consultas.InformacionTributaria);
            generaMaestro(conn, consultas.NotaDebitoMaestro, CodigoNotaCredito, NumeroNotaCredito);
            generaDetalle(conn, consultas.NotaDebitoDetalle, CodigoNotaCredito, NumeroNotaCredito);
            
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(infoND.getFechaEmision());
            ClaveAcceso = (new SimpleDateFormat("ddMMyyyy").format(fecha)) + infoT.getCodDoc() + infoT.getRuc() + sri.getAmbiente() + infoT.getEstab() + infoT.getPtoEmi() + infoT.getSecuencial() + "12345678" + sri.getTipoEmision();
            
            infoT.setClaveAcceso(ClaveAcceso + m11.modulo11(ClaveAcceso));
            System.out.println("Clave de  acceso nota de crédito " + infoT.getClaveAcceso());
            
            notaDebito.setInfoTributaria(infoT);
            notaDebito.setInfoNotaDebito(infoND);
            notaDebito.setMotivos(motivos);
            
            if (!informacionAdicional.getCampoAdicional().isEmpty()) {
                notaDebito.setInfoAdicional(informacionAdicional);
            }
            generaArchivo(RutaArchivo, ClaveAcceso + m11.modulo11(ClaveAcceso));
            conn.close();
            return ClaveAcceso + m11.modulo11(ClaveAcceso);
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(GeneraNotaDebito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
}
