/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.pdf.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.io.RandomAccessSourceFactory;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author luis
 */
public class RemoveBlankPageFromPDF {

    // value where we can consider that this is a blank image
    // can be much higher or lower depending of what is considered as a blank page
    public static final int BLANK_THRESHOLD = 160;

    public static void removeBlankPdfPages(String source, String destination)
            throws IOException, DocumentException {
        PdfReader r = null;
        RandomAccessSourceFactory rasf = null;
        RandomAccessFileOrArray raf = null;
        Document document = null;
        PdfCopy writer = null;

        try {
            r = new PdfReader(source);
            // deprecated
            //    RandomAccessFileOrArray raf
            //           = new RandomAccessFileOrArray(pdfSourceFile);
            // itext 5.4.1
            rasf = new RandomAccessSourceFactory();
            raf = new RandomAccessFileOrArray(rasf.createBestSource(source));
            document = new Document(r.getPageSizeWithRotation(1));
            writer = new PdfCopy(document, new FileOutputStream(destination));
            document.open();
            PdfImportedPage page = null;

            for (int i = 1; i <= r.getNumberOfPages(); i++) {
                // first check, examine the resource dictionary for /Font or
                // /XObject keys.  If either are present -> not blank.
                PdfDictionary pageDict = r.getPageN(i);
                PdfDictionary resDict = (PdfDictionary) pageDict.get(PdfName.RESOURCES);
                boolean noFontsOrImages = true;
                if (resDict != null) {
                    noFontsOrImages = resDict.get(PdfName.FONT) == null
                            && resDict.get(PdfName.XOBJECT) == null;
                }

                if (!noFontsOrImages) {
                    byte bContent[] = r.getPageContent(i, raf);
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    bs.write(bContent);

                    if (bs.size() > BLANK_THRESHOLD) {
                        page = writer.getImportedPage(r, i);
                        writer.addPage(page);
                    }
                }
            }
        } finally {
            if (document != null) {
                document.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (raf != null) {
                raf.close();
            }
            if (r != null) {
                r.close();
            }
        }
    }
}
