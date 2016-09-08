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
import com.jorge.datos.retencion.Impuestos;
import com.jorge.datos.retencion.Impuesto;
import com.jorge.datos.retencion.InformacionRetencion;
import com.jorge.datos.retencion.Retencion;
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
public class GeneraRetencion {

    Retencion r = new Retencion();
    InformacionTributaria infoT = new InformacionTributaria();
    InformacionRetencion infoR = new InformacionRetencion();
    Impuestos impuestos;
    InformacionAdicional informacionAdicional = new InformacionAdicional();

    void generaArchivo(String RutaArchivo, String NombreArchivo) {
        r.setId("comprobante");
        r.setVersion("1.0.0");

        OutputStreamWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{Retencion.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            out = new OutputStreamWriter(new FileOutputStream(RutaArchivo + File.separatorChar + NombreArchivo + ".xml"), "UTF-8");
            marshaller.marshal(r, out);
            System.out.println("Archivo retención" + NombreArchivo + " generado.");
        } catch (JAXBException | UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
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
                infoR.setDirEstablecimiento(rs.getString("DIRECCION"));
                infoR.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                infoR.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
            }
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.InformacionTributaria retención correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void generaMaestro(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            String sql = consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                infoT.setCodDoc(rs.getString("CODIGO_DOCUMENTO"));
                infoT.setEstab(rs.getString("ESTABLECIMIENTO"));
                infoT.setPtoEmi(rs.getString("PUNTO_EMISION"));
                infoT.setSecuencial(rs.getString("SECUENCIAL"));
                infoR.setFechaEmision(rs.getString("FECHA"));
                infoR.setTipoIdentificacionSujetoRetenido(rs.getString("TIPO_DOCUMENTO"));
                infoR.setIdentificacionSujetoRetenido(rs.getString("DOCUMENTO"));
                infoR.setRazonSocialSujetoRetenido(rs.getString("RAZON_SOCIAL"));
                infoR.setPeriodoFiscal(rs.getString("PERIODO_FISCAL"));
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
            System.out.println("SQL Consultas.RetencionMaestro correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generaDetalle(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt = null;
            ResultSet rs = null;
            List<Impuesto> impuesto = new ArrayList<>();
            stmt = conn.createStatement();
            String sql = consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {

                impuesto.add(new Impuesto(rs.getString("TIPO"), rs.getString("CODIGO_SRI"), rs.getFloat("BASE_IMPONIBLE"), rs.getFloat("PORCENTAJE"), rs.getFloat("VALOR_RETENIDO"), rs.getString("TIPO_COMPROBANTE"), rs.getString("NUMERO_COMPROBANTE"), rs.getString("FECHA_COMPROBANTE")));
            }
            impuestos = new Impuestos(impuesto);
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.RetencionDetalle correcto");
        } catch (SQLException ex) {
            Logger.getLogger(GeneraRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String genera(String codigo, String numero, String RutaArchivo) {
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
            generaMaestro(conn, consultas.RetencionMaestro, codigo, numero);
            generaDetalle(conn, consultas.RetencionDetalle, codigo, numero);

            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(infoR.getFechaEmision());
            ClaveAcceso = (new SimpleDateFormat("ddMMyyyy").format(fecha)) + infoT.getCodDoc() + infoT.getRuc() + sri.getAmbiente() + infoT.getEstab() + infoT.getPtoEmi() + infoT.getSecuencial() + "12345678" + sri.getTipoEmision();

            infoT.setClaveAcceso(ClaveAcceso + m11.modulo11(ClaveAcceso));
            System.out.println("Clave de acceso retención " + infoT.getClaveAcceso());
            r.setInfoTributaria(infoT);
            r.setInfoCompRetencion(infoR);
            r.setImpuestos(impuestos);
            if (!informacionAdicional.getCampoAdicional().isEmpty()) {
                r.setInfoAdicional(informacionAdicional);
            }

            generaArchivo(RutaArchivo, ClaveAcceso + m11.modulo11(ClaveAcceso));

            conn.close();
            return ClaveAcceso + m11.modulo11(ClaveAcceso);
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(GeneraRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
