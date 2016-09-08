/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.db;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class Consultas {

    public String InformacionTributaria;
    public String FacturaMaestro;
    public String FacturaDetalle;
    public String RetencionMaestro;
    public String RetencionDetalle;
    public String NotaCreditoMaestro;
    public String NotaCreditoDetalle;
    public String NotaDebitoMaestro;
    public String NotaDebitoDetalle;
    public String GuiaRemision;
    public String Destinatario;
    public String DestinatarioDetalle;
    public String GuiaRemisionFactura;
    public String FormaPago;

    public Consultas() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Consultas.properties");
            if (config.getProperty("consulta.InformacionTributaria") == null) {
                config.setProperty("consulta.InformacionTributaria", "SELECT RAZON_SOCIAL,NOMBRE_COMERCIAL,RUC,DIRECCION,OBLIGADO_CONTABILIDAD,CONTRIBUYENTE_ESPECIAL FROM V_INFO_TRIBUTARIA");
                config.save();
            }
            if (config.getProperty("consulta.FacturaMaestro") == null) {
                config.setProperty("consulta.FacturaMaestro", "SELECT CODIGO,NUMERO,CODIGO_DOCUMENTO,ESTABLECIMIENTO,PUNTO_EMISION,SECUENCIAL,FECHA,TOTAL_SIN_IVA,TOTAL_CON_IVA,IVA,DESCUENTOS,TOTAL,ESTADO,TIPO_DOCUMENTO,DOCUMENTO,RAZON_SOCIAL,DIRECCION,TELEFONO,MAIL,GUIA_REMISION FROM V_INFO_FACTURA_MAESTRO");
                config.save();
            }
            if (config.getProperty("consulta.FacturaDetalle") == null) {
                config.setProperty("consulta.FacturaDetalle", "SELECT CODIGO_PRINCIPAL,DESCRIPCION,CANTIDAD,PRECIO_UNITARIO,CODIGO_PORCENTAJE,PORCENTAJE_IVA,VALOR_IVA,DESCUENTO,PRECIO_TOTAL_SIN_IMPUESTO FROM V_INFO_FACTURA_DETALLE");
                config.save();
            }
            if (config.getProperty("consulta.RetencionMaestro") == null) {
                config.setProperty("consulta.RetencionMaestro", "SELECT CODIGO,NUMERO,CODIGO_DOCUMENTO,ESTABLECIMIENTO,PUNTO_EMISION,SECUENCIAL,FECHA,TIPO_DOCUMENTO,DOCUMENTO,RAZON_SOCIAL,PERIODO_FISCAL,DIRECCION,TELEFONO,MAIL FROM V_INFO_RETENCION_MAESTRO");
                config.save();
            }
            if (config.getProperty("consulta.RetencionDetalle") == null) {
                config.setProperty("consulta.RetencionDetalle", "select CODIGO,NUMERO,TIPO,CODIGO_SRI,BASE_IMPONIBLE,PORCENTAJE,VALOR_RETENIDO,TIPO_COMPROBANTE,NUMERO_COMPROBANTE,FECHA_COMPROBANTE from V_INFO_RETENCION_DETALLE");
                config.save();
            }
            if (config.getProperty("consulta.NotaCreditoMaestro") == null) {
                config.setProperty("consulta.NotaCreditoMaestro", "SELECT CODIGO,NUMERO,CODIGO_DOCUMENTO,ESTABLECIMIENTO,PUNTO_EMISION,SECUENCIAL,FECHA,TIPO_DOCUMENTO,RAZON_SOCIAL,DOCUMENTO,DOCUMENTO_MODIFICADO,MODIFICADO,FECHA_MODIFICADO,TOTAL_SIN_IMPUESTOS,TOTAL_MODIFICADO,TOTAL_SIN_IVA,TOTAL_CON_IVA,IVA,MOTIVO,DIRECCION,TELEFONO,MAIL FROM V_INFO_NOTA_CREDITO_MAESTRO");
                config.save();
            }
            if (config.getProperty("consulta.NotaCreditoDetalle") == null) {
                config.setProperty("consulta.NotaCreditoDetalle", "select CODIGO,NUMERO,CODIGO_INTERNO,DESCRIPCION,CANTIDAD,PRECIO_UNITARIO,DESCUENTO,PRECIO_TOTAL_SIN_IMPUESTO,CODIGO_PORCENTAJE,PORCENTAJE_IVA,VALOR_IVA from V_INFO_NOTA_CREDITO_DETALLE");
                config.save();
            }
            if (config.getProperty("consulta.NotaDebitoMaestro") == null) {
                config.setProperty("consulta.NotaDebitoMaestro", "SELECT CODIGO,NUMERO,CODIGO_DOCUMENTO,ESTABLECIMIENTO,PUNTO_EMISION,SECUENCIAL,FECHA,TIPO_DOCUMENTO,RAZON_SOCIAL,DOCUMENTO,DOCUMENTO_MODIFICADO,MODIFICADO,FECHA_MODIFICADO,TOTAL_SIN_IMPUESTOS,DIRECCION,TELEFONO,MAIL FROM V_INFO_NOTA_DEBITO_MAESTRO");
                config.save();
            }
            if (config.getProperty("consulta.NotaDebitoDetalle") == null) {
                config.setProperty("consulta.NotaDebitoDetalle", "SELECT CODIGO, NUMERO, MOTIVO, VALOR FROM V_INFO_NOTA_DEBITO_DETALLE");
                config.save();
            }
            if (config.getProperty("consulta.GuiaRemision") == null) {
                config.setProperty("consulta.GuiaRemision", "SELECT CODIGO,NUMERO,CODIGO_DOCUMENTO,ESTABLECIMIENTO,PUNTO_EMISION,SECUENCIAL,FECHA,DIRECCION_PARTIDA,RAZON_SOCIAL_TRANSPORTISTA,TIPO_DOCUMENTO,DOCUMENTO,PLACA FROM V_INFO_GUIA_REMISION");
                config.save();
            }
            if (config.getProperty("consulta.Destinatario") == null) {
                config.setProperty("consulta.Destinatario", "SELECT CODIGO,NUMERO,DOCUMENTO,RAZON_SOCIAL,DIRECCION,MOTIVO_TRASLADO FROM V_INFO_DESTINATARIO");
                config.save();
            }   
            if (config.getProperty("consulta.DestinatarioDetalle") == null) {
                config.setProperty("consulta.DestinatarioDetalle", "SELECT CODIGO,NUMERO,DOCUMENTO,CODIGO_ARTICULO,NOMBRE_ARTICULO,CANTIDAD FROM V_INFO_DESTINATARIO_DETALLE");
                config.save();
            }  
            if (config.getProperty("consulta.GuiaRemisionFactura") == null) {
                config.setProperty("consulta.GuiaRemisionFactura", "SELECT CODIGO,NUMERO,CODIGO_FACTURA,NUMERO_FACTURA FROM V_INFO_GUIA_REMISION_FACTURA");
                config.save();
            }  
            if (config.getProperty("consulta.FormaPago") == null) {
                config.setProperty("consulta.FormaPago", "SELECT factura,CODIGO FROM V_FORMA_PAGO_FACTURA");
                config.save();
            } 
            InformacionTributaria = config.getProperty("consulta.InformacionTributaria").toString().replace("[", "").replace("]", "");
            FacturaMaestro = config.getProperty("consulta.FacturaMaestro").toString().replace("[", "").replace("]", "");
            FacturaDetalle = config.getProperty("consulta.FacturaDetalle").toString().replace("[", "").replace("]", "");
            RetencionMaestro = config.getProperty("consulta.RetencionMaestro").toString().replace("[", "").replace("]", "");
            RetencionDetalle = config.getProperty("consulta.RetencionDetalle").toString().replace("[", "").replace("]", "");
            NotaCreditoMaestro = config.getProperty("consulta.NotaCreditoMaestro").toString().replace("[", "").replace("]", "");
            NotaCreditoDetalle = config.getProperty("consulta.NotaCreditoDetalle").toString().replace("[", "").replace("]", "");
            NotaDebitoMaestro = config.getProperty("consulta.NotaDebitoMaestro").toString().replace("[", "").replace("]", "");
            NotaDebitoDetalle = config.getProperty("consulta.NotaDebitoDetalle").toString().replace("[", "").replace("]", "");
            GuiaRemision = config.getProperty("consulta.GuiaRemision").toString().replace("[", "").replace("]", "");
            Destinatario = config.getProperty("consulta.Destinatario").toString().replace("[", "").replace("]", "");
            DestinatarioDetalle = config.getProperty("consulta.DestinatarioDetalle").toString().replace("[", "").replace("]", "");
            GuiaRemisionFactura = config.getProperty("consulta.GuiaRemisionFactura").toString().replace("[", "").replace("]", "");
            FormaPago = config.getProperty("consulta.FormaPago").toString().replace("[", "").replace("]", "");
            //System.out.print("InformacionTributaria "+InformacionTributaria);
        } catch (ConfigurationException ex) {
            Logger.getLogger(Consultas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
