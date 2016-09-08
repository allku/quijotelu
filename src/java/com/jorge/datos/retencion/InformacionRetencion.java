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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial", "obligadoContabilidad", "tipoIdentificacionSujetoRetenido", "razonSocialSujetoRetenido", "identificacionSujetoRetenido", "periodoFiscal"})

public class InformacionRetencion {
    String FechaEmision;
    String DirEstablecimiento;
    String ContribuyenteEspecial;
    String ObligadoContabilidad;
    String TipoIdentificacionSujetoRetenido;
    String RazonSocialSujetoRetenido;
    String IdentificacionSujetoRetenido;
    String PeriodoFiscal;

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

    public String getTipoIdentificacionSujetoRetenido() {
        return TipoIdentificacionSujetoRetenido;
    }

    public void setTipoIdentificacionSujetoRetenido(String TipoIdentificacionSujetoRetenido) {
        this.TipoIdentificacionSujetoRetenido = TipoIdentificacionSujetoRetenido;
    }

    public String getRazonSocialSujetoRetenido() {
        return RazonSocialSujetoRetenido;
    }

    public void setRazonSocialSujetoRetenido(String RazonSocialSujetoRetenido) {
        this.RazonSocialSujetoRetenido = RazonSocialSujetoRetenido;
    }

    public String getIdentificacionSujetoRetenido() {
        return IdentificacionSujetoRetenido;
    }

    public void setIdentificacionSujetoRetenido(String IdentificacionSujetoRetenido) {
        this.IdentificacionSujetoRetenido = IdentificacionSujetoRetenido;
    }

    public String getPeriodoFiscal() {
        return PeriodoFiscal;
    }

    public void setPeriodoFiscal(String PeriodoFiscal) {
        this.PeriodoFiscal = PeriodoFiscal;
    }
    
    
}
