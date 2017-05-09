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

    private Integer totalInicial;
    @Reference
    private RegistroExportacion item;
    @Reference
    private MotivoEmpaque motivo;

    public DetalleDeBaja() {
        this.item = new RegistroExportacion();
        this.motivo = new MotivoEmpaque();
    }

    public Integer getTotalInicial() {
        return totalInicial;
    }

    public void setTotalInicial(Integer totalInicial) {
        this.totalInicial = totalInicial;
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

}
