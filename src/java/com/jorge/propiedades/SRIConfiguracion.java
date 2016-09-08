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
package com.jorge.propiedades;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class SRIConfiguracion {

    String Ambiente;
    String TipoEmision;
    String WebServiceEnvio;
    String WebServiceAutoriza;

    public SRIConfiguracion() {
                try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/SRI.properties");
            if (config.getProperty("SRI.ambiente") == null) {
                /*
                 Tipo de ambiente
                 1=pruebas
                 2=producción
                 */
                config.setProperty("SRI.ambiente", "1");
                config.save();
            }

            if (config.getProperty("SRI.tipoEmision") == null) {
                config.setProperty("SRI.tipoEmision", "1");
                config.save();
            }
            if (config.getProperty("SRI.WebServiceEnvio") == null) {
                config.setProperty("SRI.WebServiceEnvio", "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl");
                config.save();
            }
            if (config.getProperty("SRI.WebServiceAutoriza") == null) {
                config.setProperty("SRI.WebServiceAutoriza", "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl");
                config.save();
            }
            Ambiente = (String) config.getProperty("SRI.ambiente");
            TipoEmision = (String) config.getProperty("SRI.tipoEmision");
            WebServiceEnvio = (String) config.getProperty("SRI.WebServiceEnvio");
            WebServiceAutoriza = (String) config.getProperty("SRI.WebServiceAutoriza");
        } catch (ConfigurationException ex) {
            Logger.getLogger(SRIConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public String getAmbiente() {
        return Ambiente;
    }

    public String getTipoEmision() {
        return TipoEmision;
    }

    public String getWebServiceEnvio() {
        return WebServiceEnvio;
    }

    public String getWebServiceAutoriza() {
        return WebServiceAutoriza;
    }

}
