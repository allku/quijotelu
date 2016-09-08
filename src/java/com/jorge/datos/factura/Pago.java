/*
 * Copyright (C) 2016 jorjoluiso
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
package com.jorge.datos.factura;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"formaPago", "total","plazo", "unidadTiempo"})
public class Pago {

    private String FormaPago;
    private float Total;
    private int Plazo;
    private String UnidadTiempo;

    public Pago() {
        this.FormaPago = "";
        this.Total = 0;
    }

    public Pago(String FormaPago, float Total) {
        this.FormaPago = FormaPago;
        this.Total = Total;
    }

    public Pago(String FormaPago, float Total, int Plazo, String UnidadTiempo) {
        this.FormaPago = FormaPago;
        this.Total = Total;
        this.Plazo = Plazo;
        this.UnidadTiempo = UnidadTiempo;
    }

    public String getFormaPago() {
        return FormaPago;
    }

    public float getTotal() {
        return Total;
    }

    public int getPlazo() {
        return Plazo;
    }

    public String getUnidadTiempo() {
        return UnidadTiempo;
    }    

    public void setFormaPago(String FormaPago) {
        this.FormaPago = FormaPago;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }        

    public void setPlazo(int Plazo) {
        this.Plazo = Plazo;
    }

    public void setUnidadTiempo(String UnidadTiempo) {
        this.UnidadTiempo = UnidadTiempo;
    }
}
