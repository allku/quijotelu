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
import com.jorge.datos.nota.credito.Detalle;
import com.jorge.datos.nota.credito.Detalles;
import com.jorge.datos.nota.credito.Impuesto;
import com.jorge.datos.nota.credito.Impuestos;
import com.jorge.datos.nota.credito.InformacionNotaCredito;
import com.jorge.datos.nota.credito.NotaCredito;
import com.jorge.datos.nota.credito.TotalConImpuestos;
import com.jorge.datos.nota.credito.TotalImpuesto;
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
public class GeneraNotaCredito {

    NotaCredito notaCredito = new NotaCredito();
    InformacionTributaria infoT = new InformacionTributaria();
    InformacionNotaCredito infoNC = new InformacionNotaCredito();
    List<Detalle> detalle = new ArrayList<>();
    InformacionAdicional informacionAdicional = new InformacionAdicional();

    void generaArchivo(String RutaArchivo, String NombreArchivo) {
        notaCredito.setId("comprobante");
        notaCredito.setVersion("1.1.0");

        OutputStreamWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{NotaCredito.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            out = new OutputStreamWriter(new FileOutputStream(RutaArchivo + File.separatorChar + NombreArchivo + ".xml"), "UTF-8");
            marshaller.marshal(notaCredito, out);
            System.out.println("Archivo nota de crédito " + NombreArchivo + " generado en " + RutaArchivo);
        } catch (JAXBException | UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
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
                infoNC.setDirEstablecimiento(rs.getString("DIRECCION"));
                infoNC.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                infoNC.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
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
            List<TotalImpuesto> totalImpuesto = new ArrayList<>();
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement();

            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                infoT.setCodDoc(rs.getString("CODIGO_DOCUMENTO"));
                infoT.setEstab(rs.getString("ESTABLECIMIENTO"));
                infoT.setPtoEmi(rs.getString("PUNTO_EMISION"));
                infoT.setSecuencial(rs.getString("SECUENCIAL"));
                infoNC.setFechaEmision(rs.getString("FECHA"));
                infoNC.setTipoIdentificacionComprador(rs.getString("TIPO_DOCUMENTO"));
                infoNC.setRazonSocialComprador(rs.getString("RAZON_SOCIAL"));
                infoNC.setIdentificacionComprador(rs.getString("DOCUMENTO"));
                infoNC.setCodDocModificado(rs.getString("DOCUMENTO_MODIFICADO"));
                infoNC.setNumDocModificado(rs.getString("MODIFICADO"));
                infoNC.setFechaEmisionDocSustento(rs.getString("FECHA_MODIFICADO"));
                infoNC.setTotalSinImpuestos(rs.getFloat("TOTAL_SIN_IMPUESTOS"));
                infoNC.setValorModificacion(rs.getFloat("TOTAL_MODIFICADO"));
                infoNC.setMoneda("DOLAR");

                if (rs.getFloat("TOTAL_SIN_IVA") > 0) {
                    totalImpuesto.add(new TotalImpuesto("2", "0", rs.getFloat("TOTAL_SIN_IVA"), 0));
                }
                if (rs.getFloat("TOTAL_CON_IVA") > 0) {
                    int porcentajeIVA = getPorcentajeIVA(conn, codigo, numero);

                    String codigoIVA = "";
                    if (porcentajeIVA == 12) {
                        codigoIVA = "2";
                    } else if (porcentajeIVA == 14) {
                        codigoIVA = "3";
                    }
                    totalImpuesto.add(new TotalImpuesto("2", codigoIVA, rs.getFloat("TOTAL_CON_IVA"), rs.getFloat("IVA")));
                }
                infoNC.setTotalConImpuestos(new TotalConImpuestos(totalImpuesto));
                infoNC.setMotivo(rs.getString("MOTIVO"));

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
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generaDetalle(Connection conn, String consulta, String codigo, String numero) {
        try {
            Statement stmt;
            ResultSet rs;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                List<Impuesto> impuesto = new ArrayList<>();
                impuesto.add(new Impuesto("2", rs.getString("CODIGO_PORCENTAJE"), rs.getFloat("PORCENTAJE_IVA"), rs.getFloat("PRECIO_TOTAL_SIN_IMPUESTO"), rs.getFloat("VALOR_IVA")));
                detalle.add(new Detalle(rs.getString("CODIGO_INTERNO"), rs.getString("DESCRIPCION"), rs.getFloat("CANTIDAD"), rs.getFloat("PRECIO_UNITARIO"), rs.getFloat("DESCUENTO"), rs.getFloat("PRECIO_TOTAL_SIN_IMPUESTO"), new Impuestos(impuesto)));
            }
            System.out.println("SQL Consultas.NotaCreditoDetalle correcto");
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
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
            generaMaestro(conn, consultas.NotaCreditoMaestro, CodigoNotaCredito, NumeroNotaCredito);
            generaDetalle(conn, consultas.NotaCreditoDetalle, CodigoNotaCredito, NumeroNotaCredito);

            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(infoNC.getFechaEmision());
            ClaveAcceso = (new SimpleDateFormat("ddMMyyyy").format(fecha)) + infoT.getCodDoc() + infoT.getRuc() + sri.getAmbiente() + infoT.getEstab() + infoT.getPtoEmi() + infoT.getSecuencial() + "12345678" + sri.getTipoEmision();
            System.out.println("ClaveAcceso nota de crédito " + ClaveAcceso);
            infoT.setClaveAcceso(ClaveAcceso + m11.modulo11(ClaveAcceso));

            notaCredito.setInfoTributaria(infoT);

            notaCredito.setInfoNotaCredito(infoNC);

            notaCredito.setDetalles(new Detalles(detalle));
            if (!informacionAdicional.getCampoAdicional().isEmpty()) {
                notaCredito.setInfoAdicional(informacionAdicional);
            }

            generaArchivo(RutaArchivo, ClaveAcceso + m11.modulo11(ClaveAcceso));
            conn.close();
            return ClaveAcceso + m11.modulo11(ClaveAcceso);
        } catch (ParseException ex) {
            Logger.getLogger(GeneraRetencion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public int getPorcentajeIVA(Connection conn, String codigo, String numero) {
        int porcentajeIVA = 0;
        try {
            Consultas consultas = new Consultas();
            Statement stmt = null;
            ResultSet rs = null;
            String sql;
            stmt = conn.createStatement();
            sql = consultas.NotaCreditoDetalle + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero
                    + " AND PORCENTAJE_IVA>0 AND rownum=1";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                porcentajeIVA = rs.getInt("PORCENTAJE_IVA");
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return porcentajeIVA;
    }
}
