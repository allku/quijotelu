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
package com.jorge.correo;

import com.jorge.db.DataBaseConnection;
import com.jorge.db.Instrucciones;
import com.jorge.propiedades.CorreoConfiguracion;
import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.propiedades.MotorConfiguracion;
import com.jorge.utilidades.ArchivoUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author jorjoluiso
 */
public class Correo {

    String correo;
    String clave;
    List<String> correoAlternativo;

    public Correo() {
        CorreoConfiguracion cc = new CorreoConfiguracion();
        correo = cc.getCorreo();
        clave = cc.getClave();
        correoAlternativo = new ArrayList<>();
    }

    public String extraer(String archivo) {
        File archivoXML = new File(archivo);
        String correoDestinatario = ArchivoUtils.obtenerValorXML(archivoXML, "/*/infoAdicional/campoAdicional[@nombre=\"Email\"]");
        return correoDestinatario.replace(" ", "");
    }

    public void enviar(File archivoXML, File archivoPDF, String correosDestinatario) {
        String pathXML = archivoXML.getPath();
        String nombreArchivoXML = archivoXML.getName();
        String pathPDF = archivoPDF.getPath();
        String nombreArchivoPDF = archivoPDF.getName();
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        try {

// se obtiene el objeto Session. La configuración es para
            // una cuenta de gmail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", correo);
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText(nombreArchivoXML);

            // Se compone el adjunto con la imagen
            BodyPart adjuntoXML = new MimeBodyPart();
            BodyPart adjuntoPDF = new MimeBodyPart();
            adjuntoXML.setDataHandler(
                    new DataHandler(new FileDataSource(pathXML)));
            adjuntoXML.setFileName(nombreArchivoXML);

            adjuntoPDF.setDataHandler(
                    new DataHandler(new FileDataSource(pathPDF)));
            adjuntoPDF.setFileName(nombreArchivoPDF);

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjuntoXML);
            multiParte.addBodyPart(adjuntoPDF);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correosDestinatario));
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(correosDestinatario));
            //Envío a correo alternativo
            correoAlternativo = getCorreoAlternativo(dirConfig.getRutaArchivoGenerado() + File.separatorChar + archivoXML.getName());

            for (int i = 0; i < correoAlternativo.size(); i++) {
                message.addRecipient(
                        Message.RecipientType.CC,
                        new InternetAddress(correoAlternativo.get(i)));
            }

            message.setSubject("Comprobante electrónico");
            message.setContent(multiParte);

            // Se envía el correo.
            Transport t = session.getTransport("smtp");
            t.connect(correo, clave);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<String> getCorreoAlternativo(String archivo) {
        File archivoXML = new File(archivo);
        Connection conn;
        ResultSet rs;
        MotorConfiguracion configMotor = new MotorConfiguracion();
        DataBaseConnection oc = new DataBaseConnection();
        List<String> correos = new ArrayList<>();
        Instrucciones instrucciones = new Instrucciones();

        try {
            String tipoComprobante = ArchivoUtils.obtenerValorXML(archivoXML, "/*/infoTributaria/codDoc");
            String documento = null;
            conn = oc.getConnection(configMotor.getHost(), configMotor.getPuerto(), configMotor.getServicio(), configMotor.getUsuario(), configMotor.getClave());
            switch (tipoComprobante) {
                case "07":
                    documento = ArchivoUtils.obtenerValorXML(archivoXML, "/*/infoCompRetencion/identificacionSujetoRetenido");
                    break;
                case "01":
                    documento = ArchivoUtils.obtenerValorXML(archivoXML, "/*/infoFactura/identificacionComprador");
                    break;
                case "04":
                    documento = ArchivoUtils.obtenerValorXML(archivoXML, "/*/infoNotaCredito/identificacionComprador");
                    break;
            }
            PreparedStatement preparedStatement;
            preparedStatement = conn.prepareStatement(instrucciones.correoAlternativo);
            preparedStatement.setString(1, documento);
            rs = preparedStatement.executeQuery();
            correos.clear();
            while (rs.next()) {
                correos.add(rs.getString(1));
            }
            rs.close();
            preparedStatement.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);

        }
        return correos;

    }

    public static void main(String[] args) throws Exception {
        Correo c = new Correo();
        System.out.println("correoAlternativo " + c.getCorreoAlternativo("/app/quijotelu/generado/1706201507100245687700110010020000003751234567811.xml"));
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        Correo correo = new Correo();
        String destinatario = "jorjoluiso@hotmail.com";
        String nombreArchivo = "1706201507100245687700110010020000003751234567811";

        correo.enviar(new File(dirConfig.getRutaArchivoAutorizado() + File.separatorChar + nombreArchivo + ".xml"), new File(dirConfig.getRutaArchivoPDF() + File.separatorChar + nombreArchivo + ".pdf"), destinatario);
    }

}
