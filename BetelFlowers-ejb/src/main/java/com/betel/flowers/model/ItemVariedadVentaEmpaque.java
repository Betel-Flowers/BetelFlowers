/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class ItemVariedadVentaEmpaque {

    private Integer numeroRamos;
    private Integer totalTallos;
    private Integer longitud;
    private String glongitud;
    private String puntoCorte;
    private Double precioUnit;
    private Double subTotal;

    @Reference
    private Variedad variedad;
    @Embedded
    private List<OrdenPreEmpaque> registrosOrdenEmpaque;

    public ItemVariedadVentaEmpaque() {
        this.variedad = new Variedad();
        this.registrosOrdenEmpaque = new ArrayList<>();
    }

    public Integer getNumeroRamos() {
        return numeroRamos;
    }

    public void setNumeroRamos(Integer numeroRamos) {
        this.numeroRamos = numeroRamos;
    }

    public Integer getTotalTallos() {
        return totalTallos = this.calcularTotalTallos();
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

    public Double getSubTotal() {
        return (double) (this.getPrecioUnit() * this.getTotalTallos());
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public List<OrdenPreEmpaque> getRegistrosOrdenEmpaque() {
        return registrosOrdenEmpaque;
    }

    public void setRegistrosOrdenEmpaque(List<OrdenPreEmpaque> registrosOrdenEmpaque) {
        this.registrosOrdenEmpaque = registrosOrdenEmpaque;
    }

    public Integer calcularTotalTallos() {
        Integer total = 0;
        if (this.registrosOrdenEmpaque != null && !this.registrosOrdenEmpaque.isEmpty()) {
            for (OrdenPreEmpaque item : this.registrosOrdenEmpaque) {
                total = total + item.getNumeroRamos() * item.getNumeroTallosRamo();
            }
        }
        return total;
    }
}
