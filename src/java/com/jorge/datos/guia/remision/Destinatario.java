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
package com.jorge.datos.guia.remision;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"identificacionDestinatario", "razonSocialDestinatario", "dirDestinatario",
    "motivoTraslado", "docAduaneroUnico", "codEstabDestino", "ruta",
    "codDocSustento", "numDocSustento", "numAutDocSustento", "fechaEmisionDocSustento", "detalles"})

public class Destinatario {

    String identificacionDestinatario;
    String razonSocialDestinatario;
    String dirDestinatario;
    String motivoTraslado;
    String docAduaneroUnico;
    String codEstabDestino;
    String ruta;
    String codDocSustento;
    String numDocSustento;
    String numAutDocSustento;
    String fechaEmisionDocSustento;
    Detalles detalles;

    public Destinatario() {
    }

    public Destinatario(String identificacionDestinatario, String razonSocialDestinatario, String dirDestinatario, String motivoTraslado, Detalles detalles) {
        this.identificacionDestinatario = identificacionDestinatario;
        this.razonSocialDestinatario = razonSocialDestinatario;
        this.dirDestinatario = dirDestinatario;
        this.motivoTraslado = motivoTraslado;
        this.detalles = detalles;
    }

    public Destinatario(String identificacionDestinatario, String razonSocialDestinatario, String dirDestinatario, String motivoTraslado, String docAduaneroUnico, String codEstabDestino, String ruta, String codDocSustento, String numDocSustento, String numAutDocSustento, String fechaEmisionDocSustento, Detalles detalles) {
        this.identificacionDestinatario = identificacionDestinatario;
        this.razonSocialDestinatario = razonSocialDestinatario;
        this.dirDestinatario = dirDestinatario;
        this.motivoTraslado = motivoTraslado;
        this.docAduaneroUnico = docAduaneroUnico;
        this.codEstabDestino = codEstabDestino;
        this.ruta = ruta;
        this.codDocSustento = codDocSustento;
        this.numDocSustento = numDocSustento;
        this.numAutDocSustento = numAutDocSustento;
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
        this.detalles = detalles;
    }

    public String getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    public void setIdentificacionDestinatario(String identificacionDestinatario) {
        this.identificacionDestinatario = identificacionDestinatario;
    }

    public String getRazonSocialDestinatario() {
        return razonSocialDestinatario;
    }

    public void setRazonSocialDestinatario(String razonSocialDestinatario) {
        this.razonSocialDestinatario = razonSocialDestinatario;
    }

    public String getDirDestinatario() {
        return dirDestinatario;
    }

    public void setDirDestinatario(String dirDestinatario) {
        this.dirDestinatario = dirDestinatario;
    }

    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    public String getDocAduaneroUnico() {
        return docAduaneroUnico;
    }

    public void setDocAduaneroUnico(String docAduaneroUnico) {
        this.docAduaneroUnico = docAduaneroUnico;
    }

    public String getCodEstabDestino() {
        return codEstabDestino;
    }

    public void setCodEstabDestino(String codEstabDestino) {
        this.codEstabDestino = codEstabDestino;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    public String getNumAutDocSustento() {
        return numAutDocSustento;
    }

    public void setNumAutDocSustento(String numAutDocSustento) {
        this.numAutDocSustento = numAutDocSustento;
    }

    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public Detalles getDetalles() {
        return detalles;
    }

    public void setDetalles(Detalles detalles) {
        this.detalles = detalles;
    }

}
