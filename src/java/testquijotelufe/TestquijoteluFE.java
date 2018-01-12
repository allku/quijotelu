/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testquijotelufe;

import com.imprimir.pdf.FacturaPDF;
import com.imprimir.pdf.GuiaRemisionPDF;
import com.imprimir.pdf.NotaCreditoPDF;
import com.imprimir.pdf.RetencionPDF;
import com.jorge.correo.Correo;
import com.jorge.ejecutar.FacturaElectronica;
import com.jorge.ejecutar.GuiaRemisionElectronica;
import com.jorge.ejecutar.NotaCreditoElectronica;
import com.jorge.ejecutar.RetencionElectronica;
import com.jorge.electronico.ComprobanteElectronico;
import com.jorge.electronico.GrabaRespuesta;
import com.xml.GeneraFactura;
import com.xml.GeneraNotaCredito;
import com.xml.GeneraRetencion;
import com.jorge.firma.XAdESBESSignature;
import com.jorge.propiedades.DirectorioConfiguracion;
import com.jorge.propiedades.SRIConfiguracion;
import com.jorge.utilidades.ArchivoUtils;
import com.jorge.ws.cliente.sri.AutorizacionComprobantesWS;
import com.jorge.ws.cliente.sri.EnvioComprobantesWS;
import com.xml.GeneraGuiaRemision;
import com.xml.GeneraNotaDebito;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorjoluiso
 */
public class TestquijoteluFE {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        //factura();
        //retencion();
        //notaCredito();
        //notaDredito();
        //firmaEnvia("0512201407100245687700110010010000000011234567811");
        //
        //System.out.println(ce.getNumeroAutorizacion("0412201401100245687700110010020000000211234567814"));
        //System.out.println(ce.getFechaAutorizacion("0412201401100245687700110010020000000211234567814"));
        //FacturaElectronica factura = new FacturaElectronica("FAC", "1");
        
        //guiaRemision();
        imprimirFacturaPDF();
        //imprimirNotaCreditoPDF();
        //imprimirGuiaRemisionPDF();
        /*
        Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse("01/02/2015");
                Calendar c = Calendar.getInstance();
                c.setTime(fecha);
                c.add(Calendar.DATE, 3);
                System.out.println(new SimpleDateFormat("dd/MM/yyyy").format(c.getTime()));
        */
    }
    static void imprimirNotaCreditoPDF(){
            NotaCreditoPDF pdf = new NotaCreditoPDF("/data/startup/quijotelu/Notas de Crédito Emitidas/Febrero2015/Disme/ele.xml");
            pdf.genera("0203201512343510024568770013312187486", "02/03/2015 12:34:35.749");
    }
    static void imprimirFacturaPDF(){
            FacturaPDF pdf = new FacturaPDF("/data/Instaladores/tmp/0812201701179237925300120021110000000071234567813.xml");
            pdf.genera("", "");
    }
    static void imprimirGuiaRemisionPDF(){
            GuiaRemisionPDF pdf = new GuiaRemisionPDF("D:\\app\\quijotelu\\generado\\0409201506100245687700110010020000001211234567813.xml");
            pdf.genera("", "");
    }
    static void firmaEnvia(String NombreArchivo) {
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        SRIConfiguracion sriConfig = new SRIConfiguracion();
        String RutaArchivoGenerado = dirConfig.getRutaArchivoGenerado();
        String RutaArchivoFirmado = dirConfig.getRutaArchivoFirmado();
        XAdESBESSignature xadesBesFirma = new XAdESBESSignature();
        String wsEnvio = sriConfig.getWebServiceEnvio();
        String wsAutoriza = sriConfig.getWebServiceAutoriza();
        xadesBesFirma.firmar(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml", NombreArchivo + ".xml", RutaArchivoFirmado);
        EnvioComprobante(new File("/app/quijotelu/firmado" + File.separatorChar + NombreArchivo + ".xml"), wsEnvio, wsAutoriza);
    }

    static void factura() {
        FacturaElectronica factura = new FacturaElectronica("FAC", "999999999");
        factura.ejecutar();
    }

    static void retencion() {
        RetencionElectronica retencion=new RetencionElectronica("RET", "001001000099999");
        retencion.ejecutar();
    }

    static void notaCredito() {
        NotaCreditoElectronica notaCredito=new NotaCreditoElectronica("DVC", "1002000000001");
        notaCredito.ejecutar();
    }

    static void notaDredito() {
        GeneraNotaDebito ndXML = new GeneraNotaDebito();
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        SRIConfiguracion sriConfig = new SRIConfiguracion();
        XAdESBESSignature xadesBesFirma = new XAdESBESSignature();
        String RutaArchivoGenerado = dirConfig.getRutaArchivoGenerado();
        String RutaArchivoFirmado = dirConfig.getRutaArchivoFirmado();
        String NombreArchivo;
        String wsEnvio = sriConfig.getWebServiceEnvio();
        String wsAutoriza = sriConfig.getWebServiceAutoriza();
        NombreArchivo = ndXML.genera("NDC", "6", RutaArchivoGenerado);
        xadesBesFirma.firmar(RutaArchivoGenerado + File.separatorChar + NombreArchivo + ".xml", NombreArchivo + ".xml", RutaArchivoFirmado);
        EnvioComprobante(new File("/app/quijotelu/firmado" + File.separatorChar + NombreArchivo + ".xml"), wsEnvio, wsAutoriza);
    }

    static void guiaRemision() {
        GuiaRemisionElectronica guiaRemision = new GuiaRemisionElectronica("GUI", "1002000000121");
        guiaRemision.ejecutar();
    }

    static void EnvioComprobante(File ArchivoXML, String wsEnvio, String wsAutoriza) {
        RespuestaSolicitud respuestaRecepcion = new RespuestaSolicitud();
        DirectorioConfiguracion dirConfig = new DirectorioConfiguracion();
        try {
            String claveAccesoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/claveAcceso");
            String tipoComprobante = ArchivoUtils.obtenerValorXML(ArchivoXML, "/*/infoTributaria/codDoc").substring(1);

            if ((tipoComprobante != null) && (claveAccesoComprobante != null)) {
                respuestaRecepcion = EnvioComprobantesWS.obtenerRespuestaEnvio(ArchivoXML, claveAccesoComprobante, wsEnvio);
                if (respuestaRecepcion.getEstado().equals("RECIBIDA")) {
                    Thread.currentThread();
                    Thread.sleep(10 * 1000);
                    String respuestaAutoriz = AutorizacionComprobantesWS.autorizarComprobanteIndividual(claveAccesoComprobante, ArchivoXML.getName(), wsAutoriza);

                    String estado = null;
                    String resultado = null;
                    if (respuestaAutoriz.lastIndexOf("|") != -1) {
                        estado = respuestaAutoriz.substring(0, respuestaAutoriz.lastIndexOf("|"));
                        resultado = respuestaAutoriz.substring(respuestaAutoriz.lastIndexOf("|") + 1, respuestaAutoriz.length());
                    } else {
                        estado = respuestaAutoriz;
                        resultado = "Procesado";
                    }

                    if (respuestaAutoriz.equals("AUTORIZADO")) {
                        System.out.println("El comprobante fue autorizado por el SRI");
                    } else {
                        System.out.println("El comprobante fue guardado, firmado y enviado exitósamente, pero no fue Autorizado");
                    }

                } else if (respuestaRecepcion.getEstado().equals("DEVUELTA")) {
                    String resultado = EnvioComprobantesWS.obtenerMensajeRespuesta(respuestaRecepcion);

                    String dirRechazados = dirConfig.getRutaArchivoNoAutorizado() + File.separator + "rechazados";

                    ArchivoUtils.anadirMotivosRechazo(ArchivoXML, respuestaRecepcion);

                    File rechazados = new File(dirRechazados);
                    if (!rechazados.exists()) {
                        new File(dirRechazados).mkdir();
                    }

                    if (!ArchivoUtils.copiarArchivo(ArchivoXML, rechazados.getPath() + File.separator + ArchivoXML.getName())) {
                        System.out.println("Error al mover archivo a carpeta rechazados");
                    } else {
                        ArchivoXML.delete();
                    }
                    System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
                }

            } else {
                String m = " En: " + ArchivoXML.getName() + " la información <codDoc> y <claveAcceso> son obligatorias para el envio del archivo";
                System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
            }
        } catch (Exception ex) {
            Logger.getLogger(TestquijoteluFE.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al tratar de enviar el comprobante hacia el SRI");
        }
    }
}
