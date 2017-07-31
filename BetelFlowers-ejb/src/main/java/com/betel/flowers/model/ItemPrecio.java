/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 *
 * @author luis
 */
@Embedded
public class ItemPrecio {

    private Integer longitud;
    private String glongitud;
    private Double min;

    public ItemPrecio() {
        this.min = 0d;
    }

    public ItemPrecio(String glongitud) {
        this.glongitud = glongitud;
        this.min = 0d;
    }

    public ItemPrecio(Integer longitud) {
        this.longitud = longitud;
        this.min = 0d;
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

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

}
