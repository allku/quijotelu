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

import com.jorge.datos.InformacionTributaria;
import com.jorge.datos.guia.remision.Destinatario;
import com.jorge.datos.guia.remision.Destinatarios;
import com.jorge.datos.guia.remision.Detalle;
import com.jorge.datos.guia.remision.Detalles;
import com.jorge.datos.guia.remision.GuiaRemision;
import com.jorge.datos.guia.remision.InformacionGuiaRemision;
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
import java.util.Calendar;
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
public class GeneraGuiaRemision {

    GuiaRemision guiaRemision = new GuiaRemision();
    InformacionTributaria infoT = new InformacionTributaria();
    InformacionGuiaRemision infoGR = new InformacionGuiaRemision();
    Destinatarios destinatarios;
    Consultas consultas = new Consultas();

    void generaArchivo(String RutaArchivo, String NombreArchivo) {
        guiaRemision.setId("comprobante");
        guiaRemision.setVersion("1.1.0");

        OutputStreamWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{GuiaRemision.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            out = new OutputStreamWriter(new FileOutputStream(RutaArchivo + File.separatorChar + NombreArchivo + ".xml"), "UTF-8");
            marshaller.marshal(guiaRemision, out);
            System.out.println("Archivo guía remisión" + NombreArchivo + " generado.");
        } catch (JAXBException | UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(GeneraGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generaInformacion(Connection conn, String consulta) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();

            rs = stmt.executeQuery(consulta);
            while (rs.next()) {
                infoT.setRazonSocial(rs.getString("RAZON_SOCIAL"));
                infoT.setNombreComercial(rs.getString("NOMBRE_COMERCIAL"));
                infoT.setRuc(rs.getString("RUC"));
                infoT.setDirMatriz(rs.getString("DIRECCION"));
                infoGR.setDirEstablecimiento(rs.getString("DIRECCION"));
                infoGR.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                infoGR.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
            }
            System.out.println("SQL Consultas.InformacionTributaria guía remisión correcto");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void generaGuiaRemision(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String sql;
            stmt = conn.createStatement();
            sql = consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infoT.setCodDoc(rs.getString("CODIGO_DOCUMENTO"));
                infoT.setEstab(rs.getString("ESTABLECIMIENTO"));
                infoT.setPtoEmi(rs.getString("PUNTO_EMISION"));
                infoT.setSecuencial(rs.getString("SECUENCIAL"));
                infoGR.setDirPartida(rs.getString("DIRECCION_PARTIDA"));
                infoGR.setRazonSocialTransportista(rs.getString("RAZON_SOCIAL_TRANSPORTISTA"));
                infoGR.setTipoIdentificacionTransportista(rs.getString("TIPO_DOCUMENTO"));
                infoGR.setRucTransportista(rs.getString("DOCUMENTO"));
                infoGR.setDirPartida(rs.getString("DIRECCION_PARTIDA"));
                infoGR.setFechaIniTransporte(rs.getString("FECHA"));
                Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("FECHA"));
                Calendar c = Calendar.getInstance();
                c.setTime(fecha);
                c.add(Calendar.DATE, 3);
                infoGR.setFechaFinTransporte(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
                infoGR.setPlaca(rs.getString("PLACA"));
            }
            System.out.println("SQL Consultas.GuiaRemision correcto");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(GeneraGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generaDestinatario(Connection conn, String consulta, String codigo, String numero) {
        try {
            List<Destinatario> destinatario = new ArrayList<>();
            
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                Detalles detalles=generaDestinatarioDetalle(conn, consultas.DestinatarioDetalle, codigo, numero, rs.getString("DOCUMENTO"));
                destinatario.add(new Destinatario(rs.getString("DOCUMENTO"), rs.getString("RAZON_SOCIAL"), rs.getString("DIRECCION"), rs.getString("MOTIVO_TRASLADO"),detalles));
            }
            destinatarios = new Destinatarios(destinatario);
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.Destinatario correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Detalles generaDestinatarioDetalle(Connection conn, String consulta, String codigo, String numero, String documento) {
        try {
            List<Detalle> detalle = new ArrayList<>();
            Statement stmt = null;
            ResultSet rs = null;
            String sql;
            stmt = conn.createStatement();
            sql=consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero + " AND DOCUMENTO='" + documento+"'";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                detalle.add(new Detalle(rs.getString("CODIGO_ARTICULO"), rs.getString("NOMBRE_ARTICULO"), rs.getString("CANTIDAD")));
            }
            
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.DestinatarioDetalle correcto");
            return new Detalles(detalle);
        } catch (SQLException ex) {
            Logger.getLogger(GeneraGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String genera(String codigo, String numero, String rutaArchivo) {
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            SRIConfiguracion sri = new SRIConfiguracion();
            DataBaseConnection oc = new DataBaseConnection();
            Modulo11 m11 = new Modulo11();
            Connection conn;

            String ClaveAcceso;
            Date fecha = null;
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());

            generaInformacion(conn, consultas.InformacionTributaria);
            generaGuiaRemision(conn, consultas.GuiaRemision, codigo, numero);
            generaDestinatario(conn, consultas.Destinatario, codigo, numero);

            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(infoGR.getFechaIniTransporte());

            ClaveAcceso = (new SimpleDateFormat("ddMMyyyy").format(fecha)) + infoT.getCodDoc() + infoT.getRuc() + sri.getAmbiente() + infoT.getEstab() + infoT.getPtoEmi() + infoT.getSecuencial() + "12345678" + sri.getTipoEmision();

            infoT.setClaveAcceso(ClaveAcceso + m11.modulo11(ClaveAcceso));
            System.out.println("Clave de acceso guía remisión " + infoT.getClaveAcceso());

            guiaRemision.setInfoTributaria(infoT);
            guiaRemision.setInfoGuiaRemision(infoGR);
            guiaRemision.setDestinatarios(destinatarios);

            generaArchivo(rutaArchivo, ClaveAcceso + m11.modulo11(ClaveAcceso));

            conn.close();
            return ClaveAcceso + m11.modulo11(ClaveAcceso);

        } catch (ParseException | SQLException ex) {
            Logger.getLogger(GeneraGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
