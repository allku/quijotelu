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
package com.jorge.db;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class Instrucciones {

    public String verificarComprobante;
    public String insertaComprobante;
    public String actualizaComprobante;
    public String correoAlternativo;

    public Instrucciones() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Respuesta.properties");
            if (config.getProperty("respuesta.VerificaComprobante") == null) {
                config.setProperty("respuesta.VerificaComprobante", "SELECT count(*) FROM ELE_DOCUMENTO_ELECTRONICO");
                config.save();
            }
            if (config.getProperty("respuesta.InsertaComprobante") == null) {
                config.setProperty("respuesta.InsertaComprobante", "INSERT INTO ELE_DOCUMENTO_ELECTRONICO(ID,CODIGO,NUMERO,NUMERO_AUTORIZACION,FECHA_AUTORIZACION,OBSERVACION,ESTADO) VALUES (S_ELE_DOCUMENTO_ELECTRONICO.NEXTVAL,?,?,?,?,?,?)");
                config.save();
            }
            if (config.getProperty("respuesta.ActualizaComprobante") == null) {
                config.setProperty("respuesta.ActualizaComprobante", "UPDATE ELE_DOCUMENTO_ELECTRONICO SET NUMERO_AUTORIZACION=?,FECHA_AUTORIZACION=?,OBSERVACION=?,ESTADO=? WHERE CODIGO=? AND NUMERO=?");
                config.save();
            }
            if (config.getProperty("respuesta.CorreoAlternativo") == null) {
                config.setProperty("respuesta.CorreoAlternativo", "SELECT MAIL_ALTERNATIVO FROM V_INFO_CORREO_ALTERNATIVO where DOCUMENTO=?");
                config.save();
            }
            verificarComprobante = config.getProperty("respuesta.VerificaComprobante").toString().replace("[", "").replace("]", "");
            insertaComprobante = config.getProperty("respuesta.InsertaComprobante").toString().replace("[", "").replace("]", "");
            actualizaComprobante = config.getProperty("respuesta.ActualizaComprobante").toString().replace("[", "").replace("]", "");
            correoAlternativo = config.getProperty("respuesta.CorreoAlternativo").toString().replace("[", "").replace("]", "");
        } catch (ConfigurationException ex) {
            Logger.getLogger(Instrucciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
