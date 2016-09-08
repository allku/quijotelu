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
package com.jorge.datos.factura;

import com.jorge.datos.InformacionAdicional;
import com.jorge.datos.InformacionTributaria;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"infoTributaria", "infoFactura", "detalles", "infoAdicional"})
public class Factura {

    String id;
    String version;
    InformacionTributaria infoTributaria;
    InformacionFactura infoFactura;
    Detalles detalles;
    InformacionAdicional infoAdicional;

    public Factura() {
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public InformacionTributaria getInfoTributaria() {
        return infoTributaria;
    }

    public void setInfoTributaria(InformacionTributaria infoTributaria) {
        this.infoTributaria = infoTributaria;
    }

    public InformacionFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InformacionFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    public Detalles getDetalles() {
        return detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
    }

    public InformacionAdicional getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(InformacionAdicional infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

   

}
