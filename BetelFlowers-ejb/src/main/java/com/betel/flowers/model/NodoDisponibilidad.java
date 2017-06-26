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
public class NodoDisponibilidad {
    
    private Variedad variedad;
    private List<ValorNodo>  voloresNodo;

    public NodoDisponibilidad() {
        this.variedad = new Variedad();
        this.voloresNodo = new ArrayList<>();
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public List<ValorNodo> getVoloresNodo() {
        return voloresNodo;
    }

    public void setVoloresNodo(List<ValorNodo> voloresNodo) {
        this.voloresNodo = voloresNodo;
    }
    
}
