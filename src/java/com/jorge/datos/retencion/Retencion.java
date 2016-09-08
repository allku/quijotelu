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
package com.jorge.datos.retencion;

import com.jorge.datos.InformacionAdicional;
import com.jorge.datos.InformacionTributaria;
import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author jorjoluiso
 */
@XmlRootElement(name="comprobanteRetencion")
@XmlType(propOrder = {"infoTributaria", "infoCompRetencion", "impuestos", "infoAdicional"})

public class Retencion {

    String id;
    String version;
    InformacionTributaria infoTributaria;
    InformacionRetencion infoCompRetencion;
    Impuestos impuestos;
    InformacionAdicional infoAdicional;

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

    public InformacionRetencion getInfoCompRetencion() {
        return infoCompRetencion;
    }

    public void setInfoCompRetencion(InformacionRetencion infoCompRetencion) {
        this.infoCompRetencion = infoCompRetencion;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Impuestos impuestos) {
        this.impuestos = impuestos;
    }

    public InformacionAdicional getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(InformacionAdicional infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

}
