/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jorge.datos;

import com.jorge.propiedades.SRIConfiguracion;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author jorjoluiso
 */
@XmlType(propOrder = {"ambiente", "tipoEmision", "razonSocial", "nombreComercial", "ruc", "claveAcceso", "codDoc", "estab", "ptoEmi", "secuencial", "dirMatriz"})

public class InformacionTributaria {
    private String Ambiente;
    private String TipoEmision;            
    private String RazonSocial;
    private String NombreComercial;
    private String Ruc;
    private String ClaveAcceso;
    private String CodDoc;
    private String Estab;
    private String PtoEmi;
    private String Secuencial;
    private String DirMatriz;

    public InformacionTributaria() {
        SRIConfiguracion sri=new SRIConfiguracion();
        Ambiente=sri.getAmbiente();
        TipoEmision=sri.getTipoEmision();
    }

    public String getAmbiente() {
        return Ambiente;
    }

    public void setAmbiente(String Ambiente) {
        this.Ambiente = Ambiente;
    }

    public String getTipoEmision() {
        return TipoEmision;
    }

    public void setTipoEmision(String TipoEmision) {
        this.TipoEmision = TipoEmision;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public String getNombreComercial() {
        return NombreComercial;
    }

    public void setNombreComercial(String NombreComercial) {
        this.NombreComercial = NombreComercial;
    }

    public String getRuc() {
        return Ruc;
    }

    public void setRuc(String Ruc) {
        this.Ruc = Ruc;
    }

    public String getClaveAcceso() {
        return ClaveAcceso;
    }

    public void setClaveAcceso(String ClaveAcceso) {
        this.ClaveAcceso = ClaveAcceso;
    }

    public String getCodDoc() {
        return CodDoc;
    }

    public void setCodDoc(String CodDoc) {
        this.CodDoc = CodDoc;
    }

    public String getEstab() {
        return Estab;
    }

    public void setEstab(String Estab) {
        this.Estab = Estab;
    }

    public String getPtoEmi() {
        return PtoEmi;
    }

    public void setPtoEmi(String PtoEmi) {
        this.PtoEmi = PtoEmi;
    }

    public String getSecuencial() {
        return Secuencial;
    }

    public void setSecuencial(String Secuencial) {
        this.Secuencial = Secuencial;
    }

    public String getDirMatriz() {
        return DirMatriz;
    }

    public void setDirMatriz(String DirMatriz) {
        this.DirMatriz = DirMatriz;
    }

    
}
