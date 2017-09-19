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
package com.jorge.utilidades;




/**
 *
 * @author jorjoluiso
 */
public class Modulo11 {

    private String invertirCadena(String cadena) {
        String cadenaInvertida = "";
        for (int x = cadena.length() - 1; x >= 0; x--) {
            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
        }
        return cadenaInvertida;
    }

    private int obtenerSumaPorDigitos(String cadena) {
        
            int pivote = 2;
            int longitudCadena = cadena.length();
            int cantidadTotal = 0;
            int b = 1;
            for (int i = 0; i < longitudCadena; i++) {
                if (pivote == 8) {
                    pivote = 2;
                }
                int temporal = Integer.parseInt("" + cadena.substring(i, b));
                b++;
                temporal = temporal * pivote;
                pivote++;
                cantidadTotal = cantidadTotal + temporal;
            }
            cantidadTotal = 11 - cantidadTotal % 11;
            if (cantidadTotal==10){
                cantidadTotal=1;
            }
            if (cantidadTotal==11){
                cantidadTotal=0;
            }
            return cantidadTotal;

    }

    public int modulo11(String cadena) {
        System.out.println("modulo11: " + cadena);
        return obtenerSumaPorDigitos(invertirCadena(cadena));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println("Hola Mundo!");
        Modulo11 a = new Modulo11();
        int vResult;
        vResult = a.obtenerSumaPorDigitos(a.invertirCadena("2310201402100160804900110010011123456781"));
        System.out.println("Cadena: 2310201402100160804900110010011123456781");
        System.out.println("Resultado :" + vResult);

    }
}
