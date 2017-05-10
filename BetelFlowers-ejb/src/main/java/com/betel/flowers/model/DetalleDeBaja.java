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
public class DetalleDeBaja {

    private Integer cantidad;
    @Reference
    private RegistroExportacion item;
    @Reference
    private MotivoEmpaque motivo;

    public DetalleDeBaja() {
        this.cantidad = 0;
        this.item = new RegistroExportacion();
        this.motivo = new MotivoEmpaque();
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public RegistroExportacion getItem() {
        return item;
    }

    public void setItem(RegistroExportacion item) {
        this.item = item;
    }

    public MotivoEmpaque getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoEmpaque motivo) {
        this.motivo = motivo;
    }
    
    public Integer calcularCantidadActual() {
        return this.getItem().getTotalTallos() - this.getCantidad();
    }

}
