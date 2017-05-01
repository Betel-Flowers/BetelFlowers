/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.pdf.util;

import com.itextpdf.text.pdf.Barcode39;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author luis
 */
public class BarcodeGenerator {

    private static String getClaveAcceso(File xml) throws Exception {
        String claveAcceso = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("etiquetaRegExpo");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    claveAcceso = eElement.getElementsByTagName("barcode").item(0).getTextContent();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return claveAcceso;
    }

    public static boolean createBarCode(String direccionXML, String direccionDestinoHtml) {
        Boolean exito = false;
        try {
            String directorioDestino = direccionDestinoHtml;
            File archivo = new File(direccionXML);
            String claveAcceso = getClaveAcceso(archivo);
            StringBuilder sbca = new StringBuilder(claveAcceso);
            Barcode39 code39 = new Barcode39();
            code39.setCode(sbca.toString());
            code39.setX(0.75f);
            code39.setBarHeight(50f);
            java.awt.Image im = code39.createAwtImage(Color.WHITE, Color.BLACK);
            int w = im.getWidth(null);
            int h = im.getHeight(null);
            int type = BufferedImage.TYPE_INT_RGB;  // other options
            BufferedImage dest = new BufferedImage(w, h, type);
            Graphics2D g2 = dest.createGraphics();
            g2.drawImage(im, 0, 0, null);
            g2.dispose();
            StringBuilder dirDestino = new StringBuilder(directorioDestino);
            dirDestino.append("/");
            dirDestino.append(claveAcceso);
            dirDestino.append(".gif");
            ImageIO.write(dest, "gif", new FileOutputStream(dirDestino.toString()));

        } catch (Exception ex) {
            System.out.println("no se pudo generar el codigo de barras " + ex.toString());
        }

        return exito;
    }

    public static void copyBarcode(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
