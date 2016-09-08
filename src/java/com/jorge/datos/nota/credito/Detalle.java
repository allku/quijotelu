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
package com.jorge.datos.nota.credito;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"codigoInterno", "descripcion", "cantidad", "precioUnitario", "descuento", "precioTotalSinImpuesto", "impuestos"})
public class Detalle {

    String CodigoInterno;
    String Descripcion;
    float Cantidad;
    float PrecioUnitario;
    float Descuento;
    float PrecioTotalSinImpuesto;
    Impuestos impuestos;

    public Detalle(String CodigoInterno, String Descripcion, float Cantidad, float PrecioUnitario, float Descuento, float PrecioTotalSinImpuesto, Impuestos impuestos) {
        this.CodigoInterno = CodigoInterno;
        this.Descripcion = Descripcion;
        this.Cantidad = Cantidad;
        this.PrecioUnitario = PrecioUnitario;
        this.Descuento = Descuento;
        this.PrecioTotalSinImpuesto = PrecioTotalSinImpuesto;
        this.impuestos = impuestos;
    }

    public String getCodigoInterno() {
        return CodigoInterno;
    }

    public void setCodigoInterno(String CodigoInterno) {
        this.CodigoInterno = CodigoInterno;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public float getCantidad() {
        return Cantidad;
    }

    public void setCantidad(float Cantidad) {
        this.Cantidad = Cantidad;
    }

    public float getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(float PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float Descuento) {
        this.Descuento = Descuento;
    }

    public float getPrecioTotalSinImpuesto() {
        return PrecioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(float PrecioTotalSinImpuesto) {
        this.PrecioTotalSinImpuesto = PrecioTotalSinImpuesto;
    }

    public Impuestos getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(Impuestos impuestos) {
        this.impuestos = impuestos;
    }

}
