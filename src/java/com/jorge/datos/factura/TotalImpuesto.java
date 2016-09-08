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

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"codigo", "codigoPorcentaje", "baseImponible", "valor"})
public class TotalImpuesto {

    private String Codigo;
    private String CodigoPorcentaje;
    private float BaseImponible;
    private float Valor;

    public TotalImpuesto() {
        this.Codigo = "";
        this.CodigoPorcentaje = "";
        this.BaseImponible = 0;
        this.Valor = 0;
    }

    public TotalImpuesto(String Codigo, String CodigoPorcentaje, float BaseImponible, float Valor) {
        this.Codigo = Codigo;
        this.CodigoPorcentaje = CodigoPorcentaje;
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

    public float getBaseImponible() {
        return BaseImponible;
    }

    public void setBaseImponible(float BaseImponible) {
        this.BaseImponible = BaseImponible;
    }

    public float getValor() {
        return Valor;
    }

    public void setValor(float Valor) {
        this.Valor = Valor;
    }

}
