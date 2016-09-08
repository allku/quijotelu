/*
 * Copyright (C) 2015 jorjoluiso
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
package com.jorge.db;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class Reportes {

    public String facturasNoAutorizadas;
    public String facturasAutorizadas;
    public String retencionesNoAutorizadas;
    public String retencionesAutorizadas;
    public String notasCreditoNoAutorizadas;
    public String notasCreditoAutorizadas;
    public String guiasRemisionNoAutorizadas;
    public String guiasRemisionAutorizadas;
    public String autorizarFacturas;

    public Reportes() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Reportes.properties");
            if (config.getProperty("reporte.facturasNoAutorizadas") == null) {
                config.setProperty("reporte.facturasNoAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.TOTAL,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,(SELECT e.ESTADO FROM ELE_DOCUMENTO_ELECTRONICO e where e.CODIGO=f.CODIGO and e.NUMERO=f.NUMERO) as estado FROM V_INFO_FACTURA_MAESTRO f where CODIGO||NUMERO not in (SELECT e.codigo||e.numero FROM ELE_DOCUMENTO_ELECTRONICO e where e.ESTADO='AUTORIZADO') and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.facturasAutorizadas") == null) {
                config.setProperty("reporte.facturasAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.TOTAL,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as xml,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as pdf FROM ELE_DOCUMENTO_ELECTRONICO e INNER JOIN V_INFO_FACTURA_MAESTRO f ON f.CODIGO  = e.CODIGO AND f.NUMERO = e.NUMERO where e.ESTADO='AUTORIZADO' and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.retencionesNoAutorizadas") == null) {
                config.setProperty("reporte.retencionesNoAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,(SELECT e.ESTADO FROM ELE_DOCUMENTO_ELECTRONICO e where e.CODIGO=f.CODIGO and e.NUMERO=f.NUMERO) as estado FROM V_INFO_RETENCION_MAESTRO f where CODIGO||NUMERO not in (SELECT e.codigo||e.numero FROM ELE_DOCUMENTO_ELECTRONICO e where e.ESTADO='AUTORIZADO') and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.retencionesAutorizadas") == null) {
                config.setProperty("reporte.retencionesAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as xml,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as pdf FROM ELE_DOCUMENTO_ELECTRONICO e INNER JOIN V_INFO_RETENCION_MAESTRO f ON f.CODIGO  = e.CODIGO AND f.NUMERO = e.NUMERO where e.ESTADO='AUTORIZADO' and FECHA like ?");
                config.save();
            }
             if (config.getProperty("reporte.notasCreditoNoAutorizadas") == null) {
                config.setProperty("reporte.notasCreditoNoAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.TOTAL_MODIFICADO,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,(SELECT e.ESTADO FROM ELE_DOCUMENTO_ELECTRONICO e where e.CODIGO=f.CODIGO and e.NUMERO=f.NUMERO) as estado FROM V_INFO_NOTA_CREDITO_MAESTRO f where CODIGO||NUMERO not in (SELECT e.codigo||e.numero FROM ELE_DOCUMENTO_ELECTRONICO e where e.ESTADO='AUTORIZADO') and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.notasCreditoAutorizadas") == null) {
                config.setProperty("reporte.notasCreditoAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.TOTAL_MODIFICADO,f.DOCUMENTO,f.RAZON_SOCIAL,f.MAIL,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as xml,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as pdf FROM ELE_DOCUMENTO_ELECTRONICO e INNER JOIN V_INFO_NOTA_CREDITO_MAESTRO f ON f.CODIGO  = e.CODIGO AND f.NUMERO = e.NUMERO where e.ESTADO='AUTORIZADO' and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.guiasRemisionNoAutorizadas") == null) {
                config.setProperty("reporte.guiasRemisionNoAutorizadas", "SELECT d.CODIGO,d.NUMERO,d.FECHA,d.DIRECCION_PARTIDA,d.RAZON_SOCIAL_TRANSPORTISTA,d.DOCUMENTO,d.PLACA,(SELECT e.ESTADO FROM ELE_DOCUMENTO_ELECTRONICO e where e.CODIGO=d.CODIGO and e.NUMERO=d.NUMERO) as estado FROM V_INFO_GUIA_REMISION d where d.CODIGO||d.NUMERO not in (SELECT e.codigo||e.numero FROM ELE_DOCUMENTO_ELECTRONICO e where e.ESTADO='AUTORIZADO') and FECHA like ?");
                config.save();
            }
            if (config.getProperty("reporte.guiasRemisionAutorizadas") == null) {
                config.setProperty("reporte.guiasRemisionAutorizadas", "SELECT f.CODIGO,f.NUMERO,f.FECHA,f.PLACA,f.DOCUMENTO,f.RAZON_SOCIAL_TRANSPORTISTA,f.DIRECCION_PARTIDA,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as xml,FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as pdf FROM ELE_DOCUMENTO_ELECTRONICO e INNER JOIN V_INFO_GUIA_REMISION f ON f.CODIGO  = e.CODIGO AND f.NUMERO = e.NUMERO where e.ESTADO='AUTORIZADO' and FECHA like ?");
                config.save();
            }
             if (config.getProperty("reporte.autorizarFacturas") == null) {
                config.setProperty("reporte.autorizarFacturas", "SELECT f.CODIGO,f.NUMERO FROM V_INFO_FACTURA_MAESTRO f where f.CODIGO||f.NUMERO not in (SELECT e.codigo||e.numero FROM ELE_DOCUMENTO_ELECTRONICO e where e.ESTADO='AUTORIZADO') and f.FECHA like ?");
                config.save();
            }             
            facturasNoAutorizadas = config.getProperty("reporte.facturasNoAutorizadas").toString().replace("[", "").replace("]", "");
            facturasAutorizadas = config.getProperty("reporte.facturasAutorizadas").toString().replace("[", "").replace("]", "");
            retencionesNoAutorizadas = config.getProperty("reporte.retencionesNoAutorizadas").toString().replace("[", "").replace("]", "");
            retencionesAutorizadas = config.getProperty("reporte.retencionesAutorizadas").toString().replace("[", "").replace("]", "");
            notasCreditoNoAutorizadas = config.getProperty("reporte.notasCreditoNoAutorizadas").toString().replace("[", "").replace("]", "");
            notasCreditoAutorizadas = config.getProperty("reporte.notasCreditoAutorizadas").toString().replace("[", "").replace("]", "");
            guiasRemisionNoAutorizadas = config.getProperty("reporte.guiasRemisionNoAutorizadas").toString().replace("[", "").replace("]", "");
            guiasRemisionAutorizadas = config.getProperty("reporte.guiasRemisionAutorizadas").toString().replace("[", "").replace("]", "");
            autorizarFacturas= config.getProperty("reporte.autorizarFacturas").toString().replace("[", "").replace("]", "");
        } catch (ConfigurationException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
