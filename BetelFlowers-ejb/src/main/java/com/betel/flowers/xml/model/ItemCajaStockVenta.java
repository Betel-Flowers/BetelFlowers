/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.xml.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luis
 */
@XmlRootElement
public class ItemCajaStockVenta {

    private String urlFoto;
    private String variedad;
    private String longitud;
    private String numeroRamos;
    private String numeroTallosRamo;
    private String totalTallos;
    private String precio;

    public ItemCajaStockVenta() {
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    @XmlElement
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getVariedad() {
        return variedad;
    }

    @XmlElement
    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getLongitud() {
        return longitud;
    }

    @XmlElement
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNumeroRamos() {
        return numeroRamos;
    }

    @XmlElement
    public void setNumeroRamos(String numeroRamos) {
        this.numeroRamos = numeroRamos;
    }

    public String getNumeroTallosRamo() {
        return numeroTallosRamo;
    }

    @XmlElement
    public void setNumeroTallosRamo(String numeroTallosRamo) {
        this.numeroTallosRamo = numeroTallosRamo;
    }

    public String getTotalTallos() {
        return totalTallos;
    }

    @XmlElement
    public void setTotalTallos(String totalTallos) {
        this.totalTallos = totalTallos;
    }

    public String getPrecio() {
        return precio;
    }

    @XmlElement
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    
}
