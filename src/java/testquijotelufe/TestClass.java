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
package testquijotelufe;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 *
 * @author jorjoluiso
 */
public class TestClass {
     public static void main(String[] args) throws Exception {

        KeyStore p12 = KeyStore.getInstance("pkcs12");
        p12.load(new FileInputStream("/data/startup/BCE/maria_beatriz_suarez_reyes.p12"), "Mvml121961".toCharArray());
        Enumeration e = p12.aliases();
        while (e.hasMoreElements()) {
            String alias = (String) e.nextElement();
            X509Certificate c = (X509Certificate) p12.getCertificate(alias);
            Principal subject = c.getSubjectDN();
            String subjectArray[] = subject.toString().split(",");
            for (String s : subjectArray) {
                String[] str = s.trim().split("=");
                String key = str[0];
                String value = str[1];
                System.out.println(key + " - " + value);
            }
        }
    }
     public void test()
     {
         String sql="SELECT f.CODIGO,\n" +
"  f.NUMERO,\n" +
"  f.FECHA,\n" +
"  f.TOTAL,\n" +
"  f.DOCUMENTO,\n" +
"  f.RAZON_SOCIAL,\n" +
"  e.NUMERO_AUTORIZACION,\n" +
"  f.MAIL,\n" +
"  FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as xml,\n" +
"  FUN_CLAVE_ACCESO(f.FECHA,f.CODIGO,f.NUMERO) as pdf\n" +
"FROM ELE_DOCUMENTO_ELECTRONICO e\n" +
"INNER JOIN V_INFO_FACTURA_MAESTRO f\n" +
"ON f.CODIGO  = e.CODIGO\n" +
"AND f.NUMERO = e.NUMERO\n" +
"where e.ESTADO='AUTORIZADO'\n" +
"and FECHA like to_char(sysdate,'dd/mm/rrrr')";
     }
}
