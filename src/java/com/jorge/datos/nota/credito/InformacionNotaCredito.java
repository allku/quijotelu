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
package com.jorge.datos.nota.credito;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"fechaEmision", "dirEstablecimiento", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "contribuyenteEspecial", "obligadoContabilidad", "codDocModificado", "numDocModificado", "fechaEmisionDocSustento", "totalSinImpuestos", "valorModificacion", "moneda", "totalConImpuestos", "motivo"})

public class InformacionNotaCredito {

    String FechaEmision;
    String DirEstablecimiento;
    String TipoIdentificacionComprador;
    String RazonSocialComprador;
    String IdentificacionComprador;
    String ContribuyenteEspecial;
    String ObligadoContabilidad;
    String CodDocModificado;
    String NumDocModificado;
    String FechaEmisionDocSustento;
    float TotalSinImpuestos;
    float ValorModificacion;
    String Moneda;
    TotalConImpuestos TotalConImpuestos;
    String Motivo;

    public String getFechaEmision() {
        return FechaEmision;
    }

    public void setFechaEmision(String FechaEmision) {
        this.FechaEmision = FechaEmision;
    }

    public String getDirEstablecimiento() {
        return DirEstablecimiento;
    }

    public void setDirEstablecimiento(String DirEstablecimiento) {
        this.DirEstablecimiento = DirEstablecimiento;
    }

    public String getTipoIdentificacionComprador() {
        return TipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String TipoIdentificacionComprador) {
        this.TipoIdentificacionComprador = TipoIdentificacionComprador;
    }

    public String getRazonSocialComprador() {
        return RazonSocialComprador;
    }

    public void setRazonSocialComprador(String RazonSocialComprador) {
        this.RazonSocialComprador = RazonSocialComprador;
    }

    public String getIdentificacionComprador() {
        return IdentificacionComprador;
    }

    public void setIdentificacionComprador(String IdentificacionComprador) {
        this.IdentificacionComprador = IdentificacionComprador;
    }

    public String getContribuyenteEspecial() {
        return ContribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String ContribuyenteEspecial) {
        this.ContribuyenteEspecial = ContribuyenteEspecial;
    }

    public String getObligadoContabilidad() {
        return ObligadoContabilidad;
    }

    public void setObligadoContabilidad(String ObligadoContabilidad) {
        this.ObligadoContabilidad = ObligadoContabilidad;
    }

    public String getCodDocModificado() {
        return CodDocModificado;
    }

    public void setCodDocModificado(String CodDocModificado) {
        this.CodDocModificado = CodDocModificado;
    }

    public String getNumDocModificado() {
        return NumDocModificado;
    }

    public void setNumDocModificado(String NumDocModificado) {
        this.NumDocModificado = NumDocModificado;
    }

    public String getFechaEmisionDocSustento() {
        return FechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String FechaEmisionDocSustento) {
        this.FechaEmisionDocSustento = FechaEmisionDocSustento;
    }

    public float getTotalSinImpuestos() {
        return TotalSinImpuestos;
    }

    public void setTotalSinImpuestos(float TotalSinImpuestos) {
        this.TotalSinImpuestos = TotalSinImpuestos;
    }

    public float getValorModificacion() {
        return ValorModificacion;
    }

    public void setValorModificacion(float ValorModificacion) {
        this.ValorModificacion = ValorModificacion;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public TotalConImpuestos getTotalConImpuestos() {
        return TotalConImpuestos;
    }

    public void setTotalConImpuestos(TotalConImpuestos TotalConImpuestos) {
        this.TotalConImpuestos = TotalConImpuestos;
    }

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String Motivo) {
        this.Motivo = Motivo;
    }

}
