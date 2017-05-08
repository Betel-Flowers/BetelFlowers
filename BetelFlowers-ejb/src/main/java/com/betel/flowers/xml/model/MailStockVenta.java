/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.xml.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@XmlRootElement
public class MailStockVenta {

    private String barcode;
    private String creationDate;
    private String message;
    private List<ItemStockVenta> detalle;

    public MailStockVenta() {
        this.detalle = new ArrayList<>();
    }

    public String getBarcode() {
        return barcode;
    }

    @XmlElement
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCreationDate() {
        return creationDate;
    }

    @XmlElement
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement
    public void setMessage(String message) {
        this.message = message;
    }

    public List<ItemStockVenta> getDetalle() {
        return detalle;
    }

    @XmlElement
    public void setDetalle(List<ItemStockVenta> detalle) {
        this.detalle = detalle;
    }
       
}
