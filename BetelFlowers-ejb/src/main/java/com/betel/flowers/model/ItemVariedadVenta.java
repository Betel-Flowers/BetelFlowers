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
public class ItemVariedadVenta {

    private Integer numeroRamos;
    private Integer numeroTallosRamo;
    private Integer totalTallos;
    private Integer longitud;
    private String glongitud;
    private String puntoCorte;
    private Double precioUnit;
    private Double subTotal;

    @Reference
    private Variedad variedad;
    @Reference
    private RegistroExportacion registro;

    public ItemVariedadVenta() {
        this.precioUnit = 0.0;
        this.variedad = new Variedad();
        this.registro = new RegistroExportacion();
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

    public String getPuntoCorte() {
        return puntoCorte;
    }

    public void setPuntoCorte(String puntoCorte) {
        this.puntoCorte = puntoCorte;
    }

    public Double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(Double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public RegistroExportacion getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroExportacion registro) {
        this.registro = registro;
    }

    public Double getSubTotal() {
        return this.getPrecioUnit() * (double) (this.getTotalTallos());
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        String text = "";
        if (variedad.getGirasol()) {
            text = "Variedad: " + variedad.getNombre() + " Ramos: " + numeroRamos + ", Tallos: " + numeroTallosRamo + ", Longitud: " + glongitud + ", Punto Corte: " + puntoCorte + " Total:" + this.getTotalTallos() + ", Precio Unit.: " + precioUnit + ", subTotal: " + this.getSubTotal();
        } else {
            text = "Variedad: " + variedad.getNombre() + " Ramos: " + numeroRamos + ", Tallos: " + numeroTallosRamo + ", Longitud: " + longitud + ", Punto Corte: " + puntoCorte + " Total:" + this.getTotalTallos() + ", Precio Unit.: " + precioUnit + ", subTotal: " + this.getSubTotal();
        }
        return text;
    }

}
