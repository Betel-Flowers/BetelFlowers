/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.xml.service;

import com.betel.flowers.model.ItemVariedadStock;
import com.betel.flowers.model.StockVenta;
import com.betel.flowers.xml.model.EtiquetaRegExpo;
import com.betel.flowers.xml.model.ItemCajaStockVenta;
import com.betel.flowers.xml.model.ItemStockVenta;
import com.betel.flowers.xml.model.MailStockVenta;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class MailStockVentaXML implements Serializable {

    private static final long serialVersionUID = 3335818410368443745L;
    private static final Logger log = Logger.getLogger(MailStockVentaXML.class.getName());

    public void generatedXML(String barcode, String url, String filename,String message, List<StockVenta> barcodeList) {
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
        Date creationDate = calendar.getTime();
        MailStockVenta email = new MailStockVenta();
        email.setBarcode(barcode);
        email.setMessage(message);
        email.setCreationDate(format.format(creationDate));
        List<ItemStockVenta> items = new ArrayList<>();
        for (StockVenta itemStockVenta : barcodeList) {
            email.setMessage(itemStockVenta.getMessage());
            items.add(createTextItemDetailStockVenta(itemStockVenta));
        }
        email.setDetalle(items);
        createXML(url, filename + ".xml", email);
    }

    public ItemStockVenta createTextItemDetailStockVenta(StockVenta itemStockVenta) {
        ItemStockVenta item = new ItemStockVenta();
        item.setCantidadCaja(itemStockVenta.getCantidadCajas() + "");
        item.setTipoCaja(itemStockVenta.getCaja().getTipo());
        List<ItemCajaStockVenta> itemsCaja = new ArrayList<>();
        for (ItemVariedadStock caja : itemStockVenta.getDetalleCajaStock()) {
            itemsCaja.add(createTextItemCajasStockVenta(caja));
        }
        item.setVariedades(itemsCaja);
        item.setTotalTallos(itemStockVenta.getTotalTallos() + "");
        item.setPrecio(itemStockVenta.getPrecio() + "");
        return item;
    }

    public ItemCajaStockVenta createTextItemCajasStockVenta(ItemVariedadStock itemCajaStock) {
        ItemCajaStockVenta item = new ItemCajaStockVenta();
        item.setUrlFoto(itemCajaStock.getVariedad().getUrlFoto());
        item.setVariedad(itemCajaStock.getVariedad().getNombre());
        if (itemCajaStock.getVariedad().getGirasol()) {
            item.setLongitud(itemCajaStock.getGlongitud());
        } else {
            item.setLongitud(itemCajaStock.getLongitud() + "");
        }
        item.setNumeroRamos(itemCajaStock.getNumeroRamos() + "");
        item.setNumeroTallosRamo(itemCajaStock.getNumeroTallosRamo() + "");
        item.setTotalTallos(itemCajaStock.getTotalTallos() + "");
        return item;
    }

    public void createXML(String url, String filename, MailStockVenta email) {
        try {

            File file = new File(url + filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(MailStockVenta.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");

            jaxbMarshaller.marshal(email, file);
            jaxbMarshaller.marshal(email, System.out);

        } catch (JAXBException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }
}
