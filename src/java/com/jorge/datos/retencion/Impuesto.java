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

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"codigo", "codigoRetencion", "baseImponible", "porcentajeRetener", "valorRetenido", "codDocSustento", "numDocSustento", "fechaEmisionDocSustento"})

public class Impuesto {

    String Codigo;
    String CodigoRetencion;
    float BaseImponible;
    float PorcentajeRetener;
    float ValorRetenido;
    String CodDocSustento;
    String NumDocSustento;
    String FechaEmisionDocSustento;

    public Impuesto() {
        this.Codigo = "";
        this.CodigoRetencion = "";
        this.BaseImponible = 0;
        this.PorcentajeRetener = 0;
        this.ValorRetenido = 0;
        this.CodDocSustento = "";
        this.NumDocSustento = "";
        this.FechaEmisionDocSustento = "";
    }

    public Impuesto(String Codigo, String CodigoRetencion, float BaseImponible, float PorcentajeRetener, float ValorRetenido, String CodDocSustento, String NumDocSustento, String FechaEmisionDocSustento) {
        this.Codigo = Codigo;
        this.CodigoRetencion = CodigoRetencion;
        this.BaseImponible = BaseImponible;
        this.PorcentajeRetener = PorcentajeRetener;
        this.ValorRetenido = ValorRetenido;
        this.CodDocSustento = CodDocSustento;
        this.NumDocSustento = NumDocSustento;
        this.FechaEmisionDocSustento = FechaEmisionDocSustento;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getCodigoRetencion() {
        return CodigoRetencion;
    }

    public void setCodigoRetencion(String CodigoRetencion) {
        this.CodigoRetencion = CodigoRetencion;
    }

    public float getBaseImponible() {
        return BaseImponible;
    }

    public void setBaseImponible(float BaseImponible) {
        this.BaseImponible = BaseImponible;
    }

    public float getPorcentajeRetener() {
        return PorcentajeRetener;
    }

    public void setPorcentajeRetener(float PorcentajeRetener) {
        this.PorcentajeRetener = PorcentajeRetener;
    }

    public float getValorRetenido() {
        return ValorRetenido;
    }

    public void setValorRetenido(float ValorRetenido) {
        this.ValorRetenido = ValorRetenido;
    }

    public String getCodDocSustento() {
        return CodDocSustento;
    }

    public void setCodDocSustento(String CodDocSustento) {
        this.CodDocSustento = CodDocSustento;
    }

    public String getNumDocSustento() {
        return NumDocSustento;
    }

    public void setNumDocSustento(String NumDocSustento) {
        this.NumDocSustento = NumDocSustento;
    }

    public String getFechaEmisionDocSustento() {
        return FechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String FechaEmisionDocSustento) {
        this.FechaEmisionDocSustento = FechaEmisionDocSustento;
    }

}
