/*
 * Copyright (C) 2014 jorjoluiso
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.xml;

import com.jorge.datos.CampoAdicional;
import com.jorge.datos.factura.Factura;
import com.jorge.datos.factura.Impuesto;
import com.jorge.datos.factura.Impuestos;
import com.jorge.datos.InformacionAdicional;
import com.jorge.datos.InformacionTributaria;
import com.jorge.datos.factura.TotalConImpuestos;
import com.jorge.datos.factura.TotalImpuesto;
import com.jorge.datos.factura.Detalle;
import com.jorge.datos.factura.Detalles;
import com.jorge.datos.factura.InformacionFactura;
import com.jorge.datos.factura.Pago;
import com.jorge.datos.factura.Pagos;
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
public class GeneraFactura {

    Factura factura = new Factura();
    InformacionTributaria infoT = new InformacionTributaria();
    InformacionFactura infoF = new InformacionFactura();
    List<Pago> pago = new ArrayList<>();
    List<TotalImpuesto> totalImpuesto = new ArrayList<>();
    List<Detalle> detalle = new ArrayList<>();
    InformacionAdicional informacionAdicional = new InformacionAdicional();
    List<CampoAdicional> campoAdicional = new ArrayList<>();

    void generaArchivo(String RutaArchivo, String NombreArchivo) {
        factura.setId("comprobante");
        factura.setVersion("1.1.0");

        OutputStreamWriter out = null;
        try {
            JAXBContext context = JAXBContext.newInstance(new Class[]{Factura.class});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
            out = new OutputStreamWriter(new FileOutputStream(RutaArchivo + File.separatorChar + NombreArchivo + ".xml"), "UTF-8");
            marshaller.marshal(factura, out);
            System.out.println("Archivo factura " + NombreArchivo + " generado.");
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
                infoF.setDirEstablecimiento(rs.getString("DIRECCION"));
                infoF.setContribuyenteEspecial(rs.getString("CONTRIBUYENTE_ESPECIAL"));
                infoF.setObligadoContabilidad(rs.getString("OBLIGADO_CONTABILIDAD"));
                //infoF.setContribuyenteEspecial("1");
                //infoF.setObligadoContabilidad("NO");
            }
            System.out.println("SQL Consultas.InformacionTributaria factura correcto");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void generaMaestro(Connection conn, String consulta, String codigo, String numero) {
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
                infoF.setFechaEmision(rs.getString("FECHA"));
                infoF.setTipoIdentificacionComprador(rs.getString("TIPO_DOCUMENTO"));
                infoF.setRazonSocialComprador(rs.getString("RAZON_SOCIAL"));
                infoF.setIdentificacionComprador(rs.getString("DOCUMENTO"));
                infoF.setTotalSinImpuestos(rs.getFloat("TOTAL_SIN_IVA") + rs.getFloat("TOTAL_CON_IVA"));
                infoF.setTotalDescuento(rs.getFloat("DESCUENTOS"));
                infoF.setPropina((float) 0.00);
                infoF.setImporteTotal(rs.getFloat("TOTAL"));
                infoF.setMoneda("DOLAR");
                generaFormaPago(conn, numero, rs.getFloat("TOTAL"));

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
            System.out.println("SQL Consultas.FacturaMaestro correcto");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void generaDetalle(Connection conn, String consulta, String codigo, String numero) {

        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero);
            while (rs.next()) {
                List<Impuesto> impuesto = new ArrayList<>();
                impuesto.add(new Impuesto("2", rs.getString("CODIGO_PORCENTAJE"), rs.getFloat("PORCENTAJE_IVA"), rs.getFloat("PRECIO_TOTAL_SIN_IMPUESTO"), rs.getFloat("VALOR_IVA")));
                detalle.add(new Detalle(rs.getString("CODIGO_PRINCIPAL"), rs.getString("DESCRIPCION"), rs.getFloat("CANTIDAD"), rs.getFloat("PRECIO_UNITARIO"), rs.getFloat("DESCUENTO"), rs.getFloat("PRECIO_TOTAL_SIN_IMPUESTO"), new Impuestos(impuesto)));
            }
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.FacturaDetalle correcto");

        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void generaFormaPago(Connection conn, String numero, float total) {
        Consultas consultas = new Consultas();
        try {
            Statement stmt = null;
            ResultSet rs = null;
            String sql;
            stmt = conn.createStatement();
            sql = consultas.FormaPago + " WHERE factura=" + numero + " and rownum=1";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                pago.add(new Pago(rs.getString("CODIGO"), total));
            }
            infoF.setPagos(new Pagos(pago));
            System.out.println("SQL Consultas.FormaPago correcto");
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String genera(String codigo, String numero, String rutaArchivo) {
        try {
            MotorConfiguracion configMotor = new MotorConfiguracion();
            Consultas consultas = new Consultas();
            SRIConfiguracion sri = new SRIConfiguracion();
            DataBaseConnection oc = new DataBaseConnection();
            Modulo11 m11 = new Modulo11();
            Connection conn;

            String ClaveAcceso;
            Date fecha = null;

            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            generaInformacion(conn, consultas.InformacionTributaria);
            generaMaestro(conn, consultas.FacturaMaestro, codigo, numero);
            generaDetalle(conn, consultas.FacturaDetalle, codigo, numero);

            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(infoF.getFechaEmision());

            ClaveAcceso = (new SimpleDateFormat("ddMMyyyy").format(fecha)) + infoT.getCodDoc() + infoT.getRuc() + sri.getAmbiente() + infoT.getEstab() + infoT.getPtoEmi() + infoT.getSecuencial() + "12345678" + sri.getTipoEmision();

            infoT.setClaveAcceso(ClaveAcceso + m11.modulo11(ClaveAcceso));
            System.out.println("Clave de acceso factura " + infoT.getClaveAcceso());

            factura.setInfoTributaria(infoT);
            infoF.setTotalConImpuestos(new TotalConImpuestos(totalImpuesto));
            factura.setInfoFactura(infoF);
            factura.setDetalles(new Detalles(detalle));

            formaPagoInformacionAdicional(conn, consultas.FormaPago, codigo, numero);

            if (!informacionAdicional.getCampoAdicional().isEmpty()) {
                factura.setInfoAdicional(informacionAdicional);
            }

            generaArchivo(rutaArchivo, ClaveAcceso + m11.modulo11(ClaveAcceso));

            conn.close();
            return ClaveAcceso + m11.modulo11(ClaveAcceso);

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
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
            sql = consultas.FacturaDetalle + " WHERE CODIGO='" + codigo + "' AND NUMERO=" + numero
                    + " AND PORCENTAJE_IVA>0 AND rownum=1";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                porcentajeIVA = rs.getInt("PORCENTAJE_IVA");
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return porcentajeIVA;
    }

    void formaPagoInformacionAdicional(Connection conn, String consulta, String codigo, String numero) {

        try {
            Statement stmt = null;
            ResultSet rs = null;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(consulta + " WHERE factura=" + numero);
            while (rs.next()) {
                campoAdicional.add(new CampoAdicional("Pago", rs.getString("FORMA_PAGO")));
                if (rs.getInt("PLAZO") > 0) {
                    campoAdicional.add(new CampoAdicional("Plazo", rs.getInt("PLAZO") + " " + rs.getString("TIEMPO")));
                }
            }
            informacionAdicional.setCampoAdicional(campoAdicional);
            rs.close();
            stmt.close();
            System.out.println("SQL Consultas.FormaPago Información Adicional correcto");

        } catch (SQLException ex) {
            Logger.getLogger(GeneraFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
