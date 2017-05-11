/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "precioVariedadBean")
@ViewScoped
public class PrecioVariedadBean implements Serializable {

    private static final long serialVersionUID = 1642099928093855605L;

    private Variedad selected;
    private List<Variedad> variedades;
    private Boolean activeTab;

    @Inject
    private VariedadService variedadService;

    @PostConstruct
    public void init() {
        this.selected = new Variedad();
        this.variedades = this.variedadService.obtenerListPrecioLongitudFlag(1);
        if (this.variedades == null) {
            this.variedades = new ArrayList<>();
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.variedadService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Variedad) event.getObject();
        this.setActiveTab(Boolean.FALSE);
    }

    public Variedad getSelected() {
        return selected;
    }

    public void setSelected(Variedad selected) {
        this.selected = selected;
    }

    public List<Variedad> getVariedades() {
        return variedades;
    }

    public void setVariedades(List<Variedad> variedades) {
        this.variedades = variedades;
    }

    public Boolean getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(Boolean activeTab) {
        this.activeTab = activeTab;
    }

}
