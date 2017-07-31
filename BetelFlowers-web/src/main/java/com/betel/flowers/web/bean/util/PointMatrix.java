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

    private int co;
    private int cf;
    private String codeMark;
    private String tipoCaja;
    private String marcaCaja;
    private Variedad variadad;
    private String gradoLogitud;//nombre de la columna
    private String puntoCorte;
    private Integer numeroTallosRamo;
    private Integer value; // numero de tallos
    private Double precioUnit;
    private Double precioMin;
    private Double subTotal;
    private ValorNodo valorNodo;

    public PointMatrix() {
        this.co = 0;
        this.cf = 0;
        this.tipoCaja = "";
        this.marcaCaja = "";
        this.gradoLogitud = "";
        this.value = 0;
        this.precioMin =0.0;
        this.precioUnit = 0.0;
        this.subTotal = 0.0;
        this.valorNodo = new ValorNodo();
    }

    public PointMatrix(Variedad variadad, String gradoLogitud, Integer value, ValorNodo valorNodo) {
        this.co = 0;
        this.cf = 0;
        this.variadad = variadad;
        this.gradoLogitud = gradoLogitud;
        this.value = value;
        this.valorNodo = valorNodo;
    }

    public PointMatrix(String gradoLogitud, Integer value) {
        this.co = 0;
        this.cf = 0;
        this.gradoLogitud = gradoLogitud;
        this.value = value;
    }

    public PointMatrix(Variedad variedad) {
        this.co = 0;
        this.cf = 0;
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

    public int getCo() {
        return co;
    }

    public void setCo(int co) {
        this.co = co;
    }

    public int getCf() {
        return cf;
    }

    public void setCf(int cf) {
        this.cf = cf;
    }

    public Double getPrecioUnit() {
        return precioUnit;
    }

    public void setPrecioUnit(Double precioUnit) {
        this.precioUnit = precioUnit;
    }

    public Double getPrecioMin() {
        return precioMin;
    }

    public void setPrecioMin(Double precioMin) {
        this.precioMin = precioMin;
    }

    public Double getSubTotal() {
        return subTotal = (double)(value) * precioUnit;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getTipoCaja() {
        return tipoCaja;
    }

    public void setTipoCaja(String tipoCaja) {
        this.tipoCaja = tipoCaja;
    }

    public String getMarcaCaja() {
        return marcaCaja;
    }

    public void setMarcaCaja(String marcaCaja) {
        this.marcaCaja = marcaCaja;
    }

    public String getCodeMark() {
        return codeMark;
    }

    public void setCodeMark(String codeMark) {
        this.codeMark = codeMark;
    }

}
