/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class ItemCajaStock {

    private Integer numeroRamos;
    private Integer numeroTallosRamo;
    private Integer totalTallos;
    private Integer longitud;
    private String glongitud;

    @Reference
    private Variedad variedad;

    public ItemCajaStock() {
        this.numeroRamos = 1;
        this.variedad = new Variedad();
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public Integer getNumeroRamos() {
        return numeroRamos;
    }

    public void setNumeroRamos(Integer numeroRamos) {
        this.numeroRamos = numeroRamos;
    }

    public Integer getNumeroTallosRamo() {
        return numeroTallosRamo;
    }

    public void setNumeroTallosRamo(Integer numeroTallosRamo) {
        this.numeroTallosRamo = numeroTallosRamo;
    }

    public Integer getTotalTallos() {
        return this.getNumeroRamos() * this.getNumeroTallosRamo();
    }

    public void setTotalTallos(Integer totalTallos) {
        this.totalTallos = totalTallos;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public String getGlongitud() {
        return glongitud;
    }

    public void setGlongitud(String glongitud) {
        this.glongitud = glongitud;
    }

    @Override
    public String toString() {
        String text = "";
        if (variedad.getGirasol()) {
            text = "Variedad: " + variedad.getNombre() + " Ramos: " + numeroRamos + ", Tallos: " + numeroTallosRamo + ", Longitud: " + glongitud + " Total:" + this.getTotalTallos() + " ";
        } else {
            text = "Variedad: " + variedad.getNombre() + " Ramos: " + numeroRamos + ", Tallos: " + numeroTallosRamo + ", Longitud: " + longitud + " Total:" + this.getTotalTallos() + " ";
        }
        return text;
    }
}
