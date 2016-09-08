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
public class FirmaConfiguracion {

    private String PKCS12_RESOURCE;
    private String PKCS12_PASSWORD;

    public FirmaConfiguracion() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Firma.properties");
            if (config.getProperty("Firma.Ruta_PKCS12") == null) {
                config.setProperty("Firma.Ruta_PKCS12", "/data/startup/BCE/jorge_luis_quiguango_teran.p12");
                config.save();
            }

            if (config.getProperty("Firma.Clave_PKCS12") == null) {
                config.setProperty("Firma.Clave_PKCS12", "Gluc4g0n");
                config.save();
            }
            PKCS12_RESOURCE = (String) config.getProperty("Firma.Ruta_PKCS12");
            PKCS12_PASSWORD = (String) config.getProperty("Firma.Clave_PKCS12");
        } catch (ConfigurationException ex) {
            Logger.getLogger(FirmaConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPKCS12_RESOURCE() {
        return PKCS12_RESOURCE;
    }

    public String getPKCS12_PASSWORD() {
        return PKCS12_PASSWORD;
    }

}
