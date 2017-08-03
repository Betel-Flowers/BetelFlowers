/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.Date;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class PointMatrix {

    private int co;
    private int cf;
    private String codeMark;
    private String tipoCaja;
    private String marcaCaja;
    
    @Reference
    private Variedad variedad;
    
    private String gradoLogitud;//nombre de la columna
    private String puntoCorte;
    private Integer numeroTallosRamo;
    private Integer numeroRamosCaja;
    private Integer value; // numero de tallos = Cantidad total x item seleccionado
    private Double precioUnit;
    private Double precioMin;
    private Double subTotal;
    private ValorNodo valorNodo;
    
    private Boolean estadoItemEmpaque; // true listo
    private String comentarioItemEmpaque;
    private Date fechaItemEmpaque;
    
    private String username; //empaque

    public PointMatrix() {
        this.co = 0;
        this.cf = 0;
        this.tipoCaja = "";
        this.marcaCaja = "";
        this.gradoLogitud = "";
        this.value = 0;
        this.precioMin = 0.0;
        this.precioUnit = 0.0;
        this.subTotal = 0.0;
        this.valorNodo = new ValorNodo();
        this.estadoItemEmpaque = Boolean.FALSE;
        this.comentarioItemEmpaque = "";
    }

    public PointMatrix(Variedad variadad, String gradoLogitud, Integer value, ValorNodo valorNodo) {
        this.co = 0;
        this.cf = 0;
        this.variedad = variadad;
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
        this.variedad = variedad;
        this.gradoLogitud = "";
        this.value = 0;
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

    public String getCodeMark() {
        return codeMark;
    }

    public void setCodeMark(String codeMark) {
        this.codeMark = codeMark;
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

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public String getGradoLogitud() {
        return gradoLogitud;
    }

    public void setGradoLogitud(String gradoLogitud) {
        this.gradoLogitud = gradoLogitud;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
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
        return subTotal = (double) (value) * precioUnit;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public ValorNodo getValorNodo() {
        return valorNodo;
    }

    public void setValorNodo(ValorNodo valorNodo) {
        this.valorNodo = valorNodo;
    }

    public Boolean getEstadoItemEmpaque() {
        return estadoItemEmpaque;
    }

    public void setEstadoItemEmpaque(Boolean estadoItemEmpaque) {
        this.estadoItemEmpaque = estadoItemEmpaque;
    }

    public String getComentarioItemEmpaque() {
        return comentarioItemEmpaque;
    }

    public void setComentarioItemEmpaque(String comentarioItemEmpaque) {
        this.comentarioItemEmpaque = comentarioItemEmpaque;
    }

    public Date getFechaItemEmpaque() {
        return fechaItemEmpaque;
    }

    public void setFechaItemEmpaque(Date fechaItemEmpaque) {
        this.fechaItemEmpaque = fechaItemEmpaque;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumeroRamosCaja() {
        return numeroRamosCaja;
    }

    public void setNumeroRamosCaja(Integer numeroRamosCaja) {
        this.numeroRamosCaja = numeroRamosCaja;
    }

}
