/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author luis
 */
public class NodoDisponibilidad {

    private Variedad variedad;
    private List<RegistroExportacion> registros;
    private List<ValorNodo> valoresNodo;
    private List<Integer> longituds;
    private List<String> glongituds;

    public NodoDisponibilidad() {
        this.variedad = new Variedad();
        this.registros = new ArrayList<>();
        this.valoresNodo = new ArrayList<>();
        this.longituds = new ArrayList<>();
        this.glongituds = new ArrayList<>();
    }

    public void AnalyzeGradoLongitud() {
        if (this.registros != null && !this.registros.isEmpty()) {
            for (RegistroExportacion regexp : this.registros) {
                if (regexp.getVariedad().getGirasol()) {
                    this.glongituds.add(regexp.getGlongitud());
                } else {
                    this.longituds.add(regexp.getLongitud());
                }
            }
            if (this.longituds != null && !this.longituds.isEmpty()) {
                this.longituds = this.uniqueInteger(this.longituds);
                for (Integer lo : this.longituds) {
                    ValorNodo val = new ValorNodo();
                    val.setLongitud(lo);
                    this.valoresNodo.add(val);
                }
                ConstructNodeLogitud();
            }
            if (this.glongituds != null && !this.glongituds.isEmpty()) {
                this.glongituds = this.uniqueString(this.glongituds);
                for (String glo : this.glongituds) {
                    ValorNodo val = new ValorNodo();
                    val.setGlongitud(glo);
                    this.valoresNodo.add(val);
                }
                ConstructNodeGlogitud();
            }
        }
    }

    private void ConstructNodeLogitud() {
        for (int i = 0; i < this.longituds.size(); i++) {
            for (int j = 0; j < this.registros.size(); j++) {
                if (this.longituds.get(i).equals(this.registros.get(j).getLongitud())) {
                    this.valoresNodo.get(i).getRegistros().add(this.getRegistros().get(j));
                }
            }
        }
        updateListValoresNodo();
    }

    private void ConstructNodeGlogitud() {
        for (int i = 0; i < this.glongituds.size(); i++) {
            for (int j = 0; j < this.registros.size(); j++) {
                if (this.glongituds.get(i).equals(this.registros.get(j).getGlongitud())) {
                    this.valoresNodo.get(i).getRegistros().add(this.getRegistros().get(j));
                }
            }
        }
        updateListValoresNodo();
    }

    private void updateListValoresNodo() {
        if (this.valoresNodo != null && !this.valoresNodo.isEmpty()) {
            for (int i = 0; i < this.valoresNodo.size(); i++) {
                this.valoresNodo.get(i).conteo();
            }
        }
    }

    private List<String> uniqueString(List<String> lista) {
        HashSet<String> hs = new HashSet<String>();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        return lista;
    }

    private List<Integer> uniqueInteger(List<Integer> lista) {
        HashSet<Integer> hs = new HashSet<Integer>();
        hs.addAll(lista);
        lista.clear();
        lista.addAll(hs);
        return lista;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public List<ValorNodo> getValoresNodo() {
        return valoresNodo;
    }

    public void setValoresNodo(List<ValorNodo> valoresNodo) {
        this.valoresNodo = valoresNodo;
    }

    public List<RegistroExportacion> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroExportacion> registros) {
        this.registros = registros;
    }

    public List<Integer> getLongituds() {
        return longituds;
    }

    public void setLongituds(List<Integer> longituds) {
        this.longituds = longituds;
    }

    public List<String> getGlongituds() {
        return glongituds;
    }

    public void setGlongituds(List<String> glongituds) {
        this.glongituds = glongituds;
    }

}
