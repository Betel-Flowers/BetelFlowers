/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.Longitud;
import com.betel.flowers.web.bean.util.PuntoCorte;
import com.betel.flowers.web.bean.util.Ramo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "detalleVariedadBean")
@ViewScoped
public class DetalleVariedadBean implements Serializable {

    private static final long serialVersionUID = -6401802996273637969L;
    
    private List<Variedad> variedades;
    private Variedad selected;
    private Ramo ramo;
    private Longitud longitud;
    private PuntoCorte puntoCorte;
    private Boolean activeTab;
    
    @Inject
    private VariedadService variedadService;
    
    @PostConstruct
    public void init() {
        this.selected = null;
        this.variedades = this.variedadService.obtenerListFlag(1);
        this.setActiveTab(Boolean.TRUE);
    }
    
    public void onRowSelect(SelectEvent event) {
        this.selected = (Variedad) event.getObject();
        this.setActiveTab(Boolean.FALSE);
        if (this.selected.getRamos() == null) {
            this.selected.setRamos(new ArrayList<Integer>());
        }
        this.ramo = new Ramo(this.selected);
        if (this.selected.getLongitudes() == null) {
            this.selected.setLongitudes(new ArrayList<Integer>());
        }
        this.longitud = new Longitud(this.selected);
        if (this.selected.getPuntosCorte() == null) {
            this.selected.setPuntosCorte(new ArrayList<String>());
        }
        this.puntoCorte = new PuntoCorte(this.selected);
    }
    
    public List<Variedad> getVariedades() {
        return variedades;
    }
    
    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }
    
    public Variedad getSelected() {
        return selected;
    }
    
    public void setSelected(Variedad selected) {
        this.selected = selected;
    }

    public Ramo getRamo() {
        return ramo;
    }

    public void setRamo(Ramo ramo) {
        this.ramo = ramo;
    }

    public Longitud getLongitud() {
        return longitud;
    }

    public void setLongitud(Longitud longitud) {
        this.longitud = longitud;
    }

    public PuntoCorte getPuntoCorte() {
        return puntoCorte;
    }

    public void setPuntoCorte(PuntoCorte puntoCorte) {
        this.puntoCorte = puntoCorte;
    }

    public Boolean getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(Boolean activeTab) {
        this.activeTab = activeTab;
    }


}
