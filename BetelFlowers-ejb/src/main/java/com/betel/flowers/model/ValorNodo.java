/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

/**
 *
 * @author luis
 */
public class ValorNodo {
    
    private Integer longitud;
    private String glongitud;
    private Integer cantidad;

    public ValorNodo() {
        this.longitud = 0;
        this.glongitud = "";
        this.cantidad = 0;
    }

    public ValorNodo(Integer longitud, String glongitud) {
        this.longitud = longitud;
        this.glongitud = glongitud;
        this.cantidad = 0;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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
}
