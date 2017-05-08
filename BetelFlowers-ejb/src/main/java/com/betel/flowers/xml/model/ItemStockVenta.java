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
public class ItemStockVenta {
    
    private String cantidadCaja;
    private String tipoCaja;
    private String totalTallos;
    private String precio;
    private List<ItemCajaStockVenta> variedades;

    public ItemStockVenta() {
        this.variedades = new ArrayList<>();
    }

    public String getCantidadCaja() {
        return cantidadCaja;
    }

    @XmlElement
    public void setCantidadCaja(String cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    public String getTipoCaja() {
        return tipoCaja;
    }

    @XmlElement
    public void setTipoCaja(String tipoCaja) {
        this.tipoCaja = tipoCaja;
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

    public List<ItemCajaStockVenta> getVariedades() {
        return variedades;
    }

    @XmlElement
    public void setVariedades(List<ItemCajaStockVenta> variedades) {
        this.variedades = variedades;
    }
    
}
