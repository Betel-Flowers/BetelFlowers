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

    private List<NodoDisponibilidad> variedades;
    private List<String> colummsLongitud;
    private List<String> colummsGlongitud;

    public MatrizDisponibilidad() {
        this.variedades = new ArrayList<>();
        this.colummsGlongitud = new ArrayList<>();
        this.colummsLongitud = new ArrayList<>();
    }

    public void calcularColumms() {
        if (this.variedades != null && !this.variedades.isEmpty()) {
            for (NodoDisponibilidad node : this.variedades) {
                if (node.getVariedad().getGirasol()) {
                    this.colummsGlongitud.addAll(node.getGlongituds());
                } else {
                    for (Integer col : node.getLongituds()) {
                        this.colummsLongitud.add(col + "");
                    }
                }
            }
        }
    }

    public List<NodoDisponibilidad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<NodoDisponibilidad> variedades) {
        this.variedades = variedades;
    }

    public List<String> getColummsLongitud() {
        return colummsLongitud;
    }

    public void setColummsLongitud(List<String> colummsLongitud) {
        this.colummsLongitud = colummsLongitud;
    }

    public List<String> getColummsGlongitud() {
        return colummsGlongitud;
    }

    public void setColummsGlongitud(List<String> colummsGlongitud) {
        this.colummsGlongitud = colummsGlongitud;
    }


}
