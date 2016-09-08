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

import java.util.Locale;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"codigo", "codigoPorcentaje", "tarifa", "baseImponible", "valor"})

public class Impuesto {

    String Codigo;
    String CodigoPorcentaje;
    float Tarifa;
    float BaseImponible;
    float Valor;

    public Impuesto() {
        this.Codigo = "";
        this.CodigoPorcentaje = "";
        this.Tarifa = 0;
        this.BaseImponible = 0;
        this.Valor = 0;
    }

    public Impuesto(String Codigo, String CodigoPorcentaje, float Tarifa, float BaseImponible, float Valor) {
        this.Codigo = Codigo;
        this.CodigoPorcentaje = CodigoPorcentaje;
        this.Tarifa = Tarifa;
        this.BaseImponible = BaseImponible;
        this.Valor = Valor;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getCodigoPorcentaje() {
        return CodigoPorcentaje;
    }

    public void setCodigoPorcentaje(String CodigoPorcentaje) {
        this.CodigoPorcentaje = CodigoPorcentaje;
    }

    @XmlElement(name = "tarifa")
    public String getTarifa() {
        return String.format(Locale.US, "%.2f", Tarifa);
    }

    public void setTarifa(float Tarifa) {
        this.Tarifa = Tarifa;
    }

    @XmlElement(name = "baseImponible")
    public String getBaseImponible() {
        return String.format(Locale.US, "%.2f", BaseImponible);
    }

    public void setBaseImponible(float BaseImponible) {
        this.BaseImponible = BaseImponible;
    }

    @XmlElement(name = "valor")
    public String getValor() {
        return String.format(Locale.US, "%.2f", Valor);
    }

    public void setValor(float Valor) {
        this.Valor = Valor;
    }

}
