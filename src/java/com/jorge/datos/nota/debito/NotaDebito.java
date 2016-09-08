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
package com.jorge.datos.nota.debito;

import com.jorge.datos.InformacionAdicional;
import com.jorge.datos.InformacionTributaria;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"infoTributaria", "infoNotaDebito", "motivos", "infoAdicional"})

public class NotaDebito {

    String id;
    String version;
    InformacionTributaria infoTributaria;
    InformacionNotaDebito infoNotaDebito;
    Motivos motivos;
    InformacionAdicional infoAdicional;

    public NotaDebito() {
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

    public InformacionNotaDebito getInfoNotaDebito() {
        return infoNotaDebito;
    }

    public void setInfoNotaDebito(InformacionNotaDebito infoNotaDebito) {
        this.infoNotaDebito = infoNotaDebito;
    }

    public Motivos getMotivos() {
        return motivos;
    }

    public void setMotivos(Motivos motivos) {
        this.motivos = motivos;
    }

    public InformacionAdicional getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(InformacionAdicional infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

}
