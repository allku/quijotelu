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
package com.jorge.propiedades;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author jorjoluiso
 */
public class CorreoConfiguracion {

    String correo;
    String clave;

    public CorreoConfiguracion() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Correo.properties");
            if (config.getProperty("correo.correo") == null) {
                config.setProperty("correo.correo", "jorjoluiso@gmail.com");
                config.save();
            }
            if (config.getProperty("correo.clave") == null) {
                config.setProperty("correo.clave", "suclave");
                config.save();
            }
            correo = (String) config.getProperty("correo.correo");
            clave = (String) config.getProperty("correo.clave");
        } catch (ConfigurationException ex) {
            Logger.getLogger(CorreoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

}
