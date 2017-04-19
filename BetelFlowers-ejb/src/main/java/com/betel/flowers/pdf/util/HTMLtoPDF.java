/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.pdf.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class HTMLtoPDF implements Serializable{

    public static Boolean createPDF(String htmlOrigen, String pdfDestino) throws IOException {
        Boolean exito = true;
        OutputStream os = null;
        try {
            //   FileWriter fw = new FileWriter
            File tmp = new File(pdfDestino);

            String inputFile = htmlOrigen;
            String outputFile = pdfDestino;
            String url = new File(inputFile).toURI().toURL().toString();
            //os = new FileOutputStream(outputFile);
            StringBuilder sbtemp = new StringBuilder(tmp.getParent());
            sbtemp.append("\\tmp");
            sbtemp.append(tmp.getName());
            os = new FileOutputStream(sbtemp.toString());
            tmp.delete();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(os);
            RemoveBlankPageFromPDF.removeBlankPdfPages(sbtemp.toString(), pdfDestino);
            File tmppdf = new File(sbtemp.toString());
            if (tmppdf.exists()) {
                tmppdf.delete();
            }

        } catch (Exception ex) {
            //System.out.println(ex.getMessage());
            throw ex;
        } finally {
            os.close();
            return exito;
        }

    }
}
