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
package com.jorge.ws.servidor;

import com.jorge.ejecutar.FacturaElectronica;
import com.jorge.ejecutar.GuiaRemisionElectronica;
import com.jorge.ejecutar.NotaCreditoElectronica;
import com.jorge.ejecutar.NotaDebitoElectronica;
import com.jorge.ejecutar.RetencionElectronica;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jorjoluiso
 */
@WebService(serviceName = "Ejecutar")
public class Ejecutar {

    /**
     * This is a sample web service operation
     *
     * @param txt
     * @return
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Factura electrónica
     *
     * @param codigo
     * @param numero
     * @return
     */
    @WebMethod(operationName = "Factura")
    public String Factura(@WebParam(name = "codigo") String codigo, @WebParam(name = "numero") String numero) {
        FacturaElectronica factura = new FacturaElectronica(codigo, numero);
        return factura.ejecutar();
    }

    /**
     * Retención electrónica
     *
     * @param codigo
     * @param numero
     * @return
     */
    @WebMethod(operationName = "Retencion")
    public String Retencion(@WebParam(name = "codigo") String codigo, @WebParam(name = "numero") String numero) {
        RetencionElectronica retencion = new RetencionElectronica(codigo, numero);
        return retencion.ejecutar();
    }

    /**
     * Nota de crédito electrónica
     *
     * @param codigo
     * @param numero
     * @return
     */
    @WebMethod(operationName = "NotaCredito")
    public String NotaCredito(@WebParam(name = "codigo") String codigo, @WebParam(name = "numero") String numero) {
        NotaCreditoElectronica notaCredito = new NotaCreditoElectronica(codigo, numero);
        return notaCredito.ejecutar();
    }

    /**
     * Guía de remisión electrónica
     *
     * @param codigo
     * @param numero
     * @return
     */
    @WebMethod(operationName = "GuiaRemision")
    public String GuiaRemision(@WebParam(name = "codigo") String codigo, @WebParam(name = "numero") String numero) {
        GuiaRemisionElectronica guiaRemision = new GuiaRemisionElectronica(codigo, numero);
        return guiaRemision.ejecutar();
    }

    /**
     * Nota de débito electrónica
     *
     * @param codigo
     * @param numero
     * @return
     */
    @WebMethod(operationName = "NotaDebito")
    public String NotaDebito(@WebParam(name = "codigo") String codigo, @WebParam(name = "numero") String numero) {
        NotaDebitoElectronica notaDebito = new NotaDebitoElectronica(codigo, numero);
        return notaDebito.ejecutar();
    }
}
