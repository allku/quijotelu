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
public class DirectorioConfiguracion {

    String RutaArchivoGenerado;
    String RutaArchivoFirmado;
    String RutaArchivoAutorizado;
    String RutaArchivoNoAutorizado;
    String RutaArchivoPDF;

    public DirectorioConfiguracion() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./quijotelu/Directorio.properties");
            if (config.getProperty("directorio.generado") == null) {
                config.setProperty("directorio.generado", "/app/quijotelu/generado");
                config.save();
            }
            if (config.getProperty("directorio.firmado") == null) {
                config.setProperty("directorio.firmado", "/app/quijotelu/firmado");
                config.save();
            }
            if (config.getProperty("directorio.autorizado") == null) {
                config.setProperty("directorio.autorizado", "/app/quijotelu/autorizado");
                config.save();
            }
            if (config.getProperty("directorio.noautorizado") == null) {
                config.setProperty("directorio.noautorizado", "/app/quijotelu/noautorizado");
                config.save();
            }
            if (config.getProperty("directorio.pdf") == null) {
                config.setProperty("directorio.pdf", "/app/quijotelu/pdf");
                config.save();
            }
            RutaArchivoNoAutorizado = (String) config.getProperty("directorio.noautorizado");
            RutaArchivoAutorizado = (String) config.getProperty("directorio.autorizado");
            RutaArchivoFirmado = (String) config.getProperty("directorio.firmado");
            RutaArchivoGenerado = (String) config.getProperty("directorio.generado");
            RutaArchivoPDF = (String) config.getProperty("directorio.pdf");

        } catch (ConfigurationException ex) {
            Logger.getLogger(DirectorioConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRutaArchivoGenerado() {
        return RutaArchivoGenerado;
    }

    public String getRutaArchivoFirmado() {
        return RutaArchivoFirmado;
    }

    public String getRutaArchivoAutorizado() {
        return RutaArchivoAutorizado;
    }

    public String getRutaArchivoNoAutorizado() {
        return RutaArchivoNoAutorizado;
    }

    public String getRutaArchivoPDF() {
        return RutaArchivoPDF;
    }

    public void setRutaArchivoPDF(String RutaArchivoPDF) {
        this.RutaArchivoPDF = RutaArchivoPDF;
    }

}
