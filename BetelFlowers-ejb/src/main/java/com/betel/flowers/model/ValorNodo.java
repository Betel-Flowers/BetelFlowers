/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ValorNodo {

    private Integer longitud;
    private String glongitud;
    private Integer cantidad;
    private List<RegistroExportacion> registros;

    public ValorNodo() {
        this.longitud = 0;
        this.glongitud = "";
        this.cantidad = 0;
        this.registros = new ArrayList<>();
    }

    public ValorNodo(Integer longitud, String glongitud) {
        this.longitud = longitud;
        this.glongitud = glongitud;
        this.cantidad = 0;
        this.registros = new ArrayList<>();
    }

    public void conteo() {
        Integer total = 0;
        if (this.registros != null && !this.registros.isEmpty()) {
            for(RegistroExportacion regexpo : this.registros){
                total = total + regexpo.getTotalTallos();
            }
        }
        this.cantidad = total;
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

    public List<RegistroExportacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroExportacion> registros) {
        this.registros = registros;
    }
}
