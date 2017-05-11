/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.TerminoExportacion;
import com.betel.flowers.service.TerminoExportacionService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "terminoExportacionBean")
@ViewScoped
public class TerminoExportacionBean implements Serializable {

    private TerminoExportacion nuevo;
    private TerminoExportacion selected;
    private List<TerminoExportacion> terminos;

    @Inject
    private TerminoExportacionService terminoService;

    @PostConstruct
    public void init() {
        this.nuevo = new TerminoExportacion();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.terminos = this.terminoService.obtenerListFlag(1);
        if (this.terminos == null) {
            this.terminos = new ArrayList<>();
        }
    }
    
    public void add(ActionEvent evt) {
        Boolean exito = this.terminoService.insert(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.terminoService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.terminoService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }


    public TerminoExportacion getNuevo() {
        return nuevo;
    }

    public void setNuevo(TerminoExportacion nuevo) {
        this.nuevo = nuevo;
    }

    public TerminoExportacion getSelected() {
        return selected;
    }

    public void setSelected(TerminoExportacion selected) {
        this.selected = selected;
    }

    public List<TerminoExportacion> getTerminos() {
        return terminos;
    }

    public void setTerminos(List<TerminoExportacion> terminos) {
        this.terminos = terminos;
    }

}
