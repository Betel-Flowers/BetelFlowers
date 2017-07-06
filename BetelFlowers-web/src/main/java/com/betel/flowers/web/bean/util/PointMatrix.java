/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ValorNodo;
import java.io.Serializable;

/**
 *
 * @author luis
 */
public class PointMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    private String variedad; //variedad
    private String gradoLogitud;//nombre de la columna
    private Integer value; // numero de tallos
    private ValorNodo valorNodo;

    public PointMatrix() {
        this.gradoLogitud = "";
        this.variedad = "";
        this.value = 0;
        this.valorNodo = new ValorNodo();
    }

    public PointMatrix(String variedad, String gradoLogitud, Integer value, ValorNodo valorNodo) {
        this.variedad = variedad;
        this.gradoLogitud = gradoLogitud;
        this.value = value;
        this.valorNodo = valorNodo;
    }

    public PointMatrix(String gradoLogitud, Integer value) {
        this.variedad = "";
        this.gradoLogitud = gradoLogitud;
        this.value = value;
    }

    public PointMatrix(String variedad) {
        this.variedad = variedad;
        this.gradoLogitud = "";
        this.value = 0;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getGradoLogitud() {
        return gradoLogitud;
    }

    public void setGradoLogitud(String gradoLogitud) {
        this.gradoLogitud = gradoLogitud;
    }

    public String getVariedad() {
        return variedad;
    }

    public void setVariedad(String variedad) {
        this.variedad = variedad;
    }

    public ValorNodo getValorNodo() {
        return valorNodo;
    }

    public void setValorNodo(ValorNodo valorNodo) {
        this.valorNodo = valorNodo;
    }

}
