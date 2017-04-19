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
public class EtiquetaRegExpo {
    
    private String barcode;
    private String createDate;   
    private List<DetalleRegExpo> detalle;

    public EtiquetaRegExpo() {
    this.detalle = new ArrayList<>();
    }
    
    public String getBarcode() {
        return barcode;
    }

    @XmlElement
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getCreateDate() {
        return createDate;
    }

    @XmlElement
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<DetalleRegExpo> getDetalle() {
        return detalle;
    }

    @XmlElement
    public void setDetalle(List<DetalleRegExpo> detalle) {
        this.detalle = detalle;
    }
    
}
