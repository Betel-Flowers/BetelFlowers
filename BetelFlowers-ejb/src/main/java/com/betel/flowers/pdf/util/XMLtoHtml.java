/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.pdf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class XMLtoHtml implements Serializable {

    private static final Logger LOG = Logger.getLogger(XMLtoHtml.class.getName());
    private static final long serialVersionUID = -1684391484304843987L;

    public Boolean createHTML(String direccionDestino, String direccionDestinoHtml, String direccionXML, String barcode, int Type) throws IOException, FileNotFoundException, TransformerFactoryConfigurationError, TransformerException {
        Boolean exito = false;
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xmlDoc = new StreamSource(direccionXML);
            Source xslDoc = null;
            switch (Type) {
                case 0:
                    BarcodeGenerator.createBarCode(direccionXML, direccionDestino);
                    xslDoc = new StreamSource(new File("/var/www/html/pdf/resources/tag/re/tagRegistroExportacion.xsl"));
                    copyFile("/var/www/html/pdf/resources/tag/re/style.css", direccionDestino+"style.css");
                    break;
                case 1:
                    xslDoc = new StreamSource(XMLtoHtml.class.getResourceAsStream("/rapidloans/pdf/res/rptpdf.xsl"));
                    break;
                case 2:
                    break;
                default:
                    LOG.log(Level.SEVERE, "ninguna opcion XMLtoHTML");
            }

            if (xslDoc != null) {
                StringWriter writer = new StringWriter();
                Transformer transformer = tFactory.newTransformer(xslDoc);
                transformer.setParameter("barcode", barcode);
                transformer.transform(xmlDoc, new javax.xml.transform.stream.StreamResult(writer));
                String result = writer.toString();
                exito = writeHTML(checkHTML(result), direccionDestinoHtml);
            }
        } catch (TransformerFactoryConfigurationError | FileNotFoundException | TransformerException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw ex;
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        } finally {
            try {

                exito = true;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, ex.getMessage());
            }
        }

        return exito;
    }

    public String checkHTML(String htmlString) throws IOException {

        String checkedhtml = null;
        try {
            String value = new String(Jsoup.parse(htmlString, "UTF-8").html().getBytes(), "UTF-8");
            checkedhtml = StringEscapeUtils.unescapeHtml4(value);
        } catch (Exception ex) {
            throw ex;
        }

        return checkedhtml;
    }

    public void transform(File source, String srcEncoding, File target, String tgtEncoding) throws IOException {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(source), srcEncoding));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(target), tgtEncoding));
            char[] buffer = new char[16384];
            int read;
            while ((read = br.read(buffer)) != -1) {
                bw.write(buffer, 0, read);
            }
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } finally {
                if (bw != null) {
                    bw.close();
                }
            }
        }
    }

    public boolean writeHTML(String htmlString, String direccionDestino) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        Boolean exito = false;
        FileOutputStream fos = new FileOutputStream(direccionDestino);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                fos, "UTF-8"));
        try {
            out.write(htmlString);
            exito = true;
        } catch (Exception ex) {
        } finally {
            out.close();
            fos.close();
        }
        return exito;
    }

    public void copyFile(String origen, String destino) {
        File source = new File(origen);
        File dest = new File(destino);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
}
