/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.xml.service;

import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.xml.model.DetalleRegExpo;
import com.betel.flowers.xml.model.EtiquetaRegExpo;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author luis
 */
@Stateless
@LocalBean
public class EtiquetaRegExpoXML implements Serializable {

    private static final long serialVersionUID = -1259799501714302936L;
    private static final Logger log = Logger.getLogger(EtiquetaRegExpoXML.class.getName());

    public void generatedXML(String barcode, String url, String filename, List<RegistroExportacion> barcodeList) {
        Path path = Paths.get(url);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.log(Level.INFO, "Directory is created!");
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to create directory! " + e.getMessage());
            }
        }

        File fileDelete = new File(url + filename + "." + "xml");
        if (fileDelete.delete()) {
            log.log(Level.INFO, "Se elimino el archivo: " + url + filename + "." + "xml");
        } else {
            log.log(Level.INFO, "No se elimino el archivo: " + url + filename + "." + "xml");
        }
        
        GregorianCalendar calendar = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        EtiquetaRegExpo etiqueta = new EtiquetaRegExpo();
        Date creationDate = calendar.getTime();
        etiqueta.setCreateDate(format.format(calendar.getTime()));
        etiqueta.setBarcode(barcode);
        for (RegistroExportacion regExpo : barcodeList) {
            etiqueta.getDetalle().add(createTextItemDetail(creationDate, regExpo));
        }
        createXML(url, filename+".xml", etiqueta);
    }

    public DetalleRegExpo createTextItemDetail(Date creationDate, RegistroExportacion regExpo) {
        DetalleRegExpo item = new DetalleRegExpo();
        Date fechaVencimiento = sumarRestarDiasFecha(creationDate, regExpo.getVariedad().getTiempoVida());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        item.setVariedad(regExpo.getVariedad().getNombre());
        item.setFechaVencimiento(formatDate.format(fechaVencimiento));
        return item;
    }

    public void createXML(String url, String filename, EtiquetaRegExpo etiqueta) {
        try {

            File file = new File(url + filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(EtiquetaRegExpo.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(etiqueta, file);
            jaxbMarshaller.marshal(etiqueta, System.out);

        } catch (JAXBException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    // Suma los días recibidos a la fecha  
    public Date sumarRestarDiasFecha(Date fecha, int dias) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos

    }
// Suma o resta las horas recibidos a la fecha  

    public Date sumarRestarHorasFecha(Date fecha, int horas) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.HOUR, horas);  // numero de horas a añadir, o restar en caso de horas<0

        return calendar.getTime(); // Devuelve el objeto Date con las nuevas horas añadidas

    }

}
