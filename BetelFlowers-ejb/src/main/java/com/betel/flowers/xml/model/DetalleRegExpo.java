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
public class DetalleRegExpo {

    private String variedad;
    private String fechaVencimiento;

    public DetalleRegExpo() {
    }

    public String getVariedad() {
        return variedad;
    }

    @XmlElement
    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    @XmlElement
    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

}
