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
public class MatrizDisponibilidad {
    
    private Especie especie;
    private List<NodoDisponibilidad> variedades;

    public MatrizDisponibilidad() {
        this.especie = new Especie();
        this.variedades = new ArrayList<>();
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public List<NodoDisponibilidad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<NodoDisponibilidad> variedades) {
        this.variedades = variedades;
    }
    
}
