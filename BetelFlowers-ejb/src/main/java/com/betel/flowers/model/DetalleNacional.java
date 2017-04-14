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
public class DetalleNacional {

    private Integer cantidad;

    @Reference
    private Motivo motivo;

    public DetalleNacional() {
        this.cantidad = 0;
        this.motivo = new Motivo();
    }

    public DetalleNacional(Integer cantidad, Motivo motivo) {
        this.cantidad = cantidad;
        this.motivo = motivo;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return  "cantidad=" + cantidad + ", motivo=" + motivo.getDescripcion();
    }
    
}
