/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.datos.factura;

import java.util.Locale;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlRootElement
@XmlType(propOrder = {"fechaEmision", "dirEstablecimiento", "contribuyenteEspecial", "obligadoContabilidad", "tipoIdentificacionComprador", "razonSocialComprador", "identificacionComprador", "totalSinImpuestos", "totalDescuento", "totalConImpuestos", "propina", "importeTotal", "moneda","pagos"})
public class InformacionFactura {

    private String FechaEmision;
    private String DirEstablecimiento;
    private String ContribuyenteEspecial;
    private String ObligadoContabilidad;
    private String TipoIdentificacionComprador;    
    private String RazonSocialComprador;
    private String IdentificacionComprador;
    private float TotalSinImpuestos;
    private float TotalDescuento;
    private TotalConImpuestos totalConImpuestos;
    private float Propina;
    private float ImporteTotal;
    private String Moneda;
    private Pagos pagos;

    @XmlElement(name = "totalConImpuestos")
    public TotalConImpuestos getTotalConImpuestos() {
        return totalConImpuestos;
    }

    public void setTotalConImpuestos(TotalConImpuestos totalConImpuestos) {
        this.totalConImpuestos = totalConImpuestos;
    }

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

    @XmlElement(name = "totalSinImpuestos")
    public String getTotalSinImpuestos() {
        return String.format(Locale.US, "%.2f", TotalSinImpuestos);
    }

    public void setTotalSinImpuestos(float TotalSinImpuestos) {
        this.TotalSinImpuestos = TotalSinImpuestos;
    }

    @XmlElement(name = "totalDescuento")
    public String getTotalDescuento() {
        return String.format(Locale.US, "%.2f", TotalDescuento);
    }

    public void setTotalDescuento(float TotalDescuento) {
        this.TotalDescuento = TotalDescuento;
    }

    @XmlElement(name = "propina")
    public String getPropina() {
        return String.format(Locale.US, "%.2f", Propina);
    }

    public void setPropina(float Propina) {
        this.Propina = Propina;
    }
@XmlElement(name = "importeTotal")
    public String getImporteTotal() {
        return String.format(Locale.US, "%.2f",ImporteTotal);
    }

    public void setImporteTotal(float ImporteTotal) {
        this.ImporteTotal = ImporteTotal;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public Pagos getPagos() {
        return pagos;
    }

    public void setPagos(Pagos pagos) {
        this.pagos = pagos;
    }

}
