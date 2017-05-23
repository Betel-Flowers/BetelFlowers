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
public class OrdenPreEmpaque {
    
    private Integer numeroRamos;
    private Integer numeroTallosRamo;
    @Reference
    private RegistroExportacion registro;

    public OrdenPreEmpaque(Integer numeroRamos, Integer numeroTallosRamo, RegistroExportacion registro) {
        this.numeroRamos = numeroRamos;
        this.numeroTallosRamo = numeroTallosRamo;
        this.registro = registro;
    }

    public OrdenPreEmpaque() {
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

    public RegistroExportacion getRegistro() {
        return registro;
    }

    public void setRegistro(RegistroExportacion registro) {
        this.registro = registro;
    }
}
