/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ValorNodo;
import com.betel.flowers.model.Variedad;
import java.io.Serializable;

/**
 *
 * @author luis
 */
public class PointMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    private Variedad variadad;
    private String gradoLogitud;//nombre de la columna
    private String puntoCorte;
    private Integer numeroTallosRamo;
    private Integer value; // numero de tallos
    private ValorNodo valorNodo;

    public PointMatrix() {
        this.gradoLogitud = "";
        this.value = 0;
        this.valorNodo = new ValorNodo();
    }

    public PointMatrix(Variedad variadad, String gradoLogitud, Integer value, ValorNodo valorNodo) {
        this.variadad = variadad;
        this.gradoLogitud = gradoLogitud;
        this.value = value;
        this.valorNodo = valorNodo;
    }

    public PointMatrix(String gradoLogitud, Integer value) {
        this.gradoLogitud = gradoLogitud;
        this.value = value;
    }

    public PointMatrix(Variedad variedad) {
        this.variadad = variedad;
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

    public ValorNodo getValorNodo() {
        return valorNodo;
    }

    public void setValorNodo(ValorNodo valorNodo) {
        this.valorNodo = valorNodo;
    }

    public Variedad getVariadad() {
        return variadad;
    }

    public void setVariadad(Variedad variadad) {
        this.variadad = variadad;
    }

    public String getPuntoCorte() {
        return puntoCorte;
    }

    public void setPuntoCorte(String puntoCorte) {
        this.puntoCorte = puntoCorte;
    }

    public Integer getNumeroTallosRamo() {
        return numeroTallosRamo;
    }

    public void setNumeroTallosRamo(Integer numeroTallosRamo) {
        this.numeroTallosRamo = numeroTallosRamo;
    }

}
