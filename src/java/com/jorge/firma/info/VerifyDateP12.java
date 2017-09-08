/*
 * Copyright (C) 2017 jorgequiguango
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
package com.jorge.firma.info;

import com.jorge.propiedades.FirmaConfiguracion;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorgequiguango
 */
public class VerifyDateP12 {

    public List<String> getInformacion() {
        List<String> listaFirmaP12 = new ArrayList();
        FirmaConfiguracion firmaConfig = new FirmaConfiguracion();

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(new FileInputStream(firmaConfig.getPKCS12_RESOURCE()), firmaConfig.getPKCS12_PASSWORD().toCharArray());
            Enumeration e = keyStore.aliases();

            while (e.hasMoreElements()) {
                String alias = (String) e.nextElement();

                if (alias.matches(".*decryption.*")) {

                    java.security.cert.Certificate[] chain = keyStore.getCertificateChain(alias);
                    System.out.println("Alias " + alias);
                    for (java.security.cert.Certificate cert : chain) {

                        String[] separated = cert.toString().split("\n");
                        for (int i = 0; i < separated.length; i++) {
//                        System.out.println(separated[i]);
                            if (separated[i].matches(".*Subject:.*")) {
                                listaFirmaP12.add(separated[i]);
                            } else if (separated[i].matches(".*Validity:.*")) {
                                listaFirmaP12.add(separated[i]);
                                listaFirmaP12.add(separated[i + 1]);
                            }
                        }
                    }
                }
            }

//            for (String str : listaFirmaP12) {
//                System.out.println(str);
//                System.out.println("Press key ...");
//                System.in.read();
//            }
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException ex) {
            Logger.getLogger(VerifyDateP12.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaFirmaP12;
    }
}
