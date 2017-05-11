/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Pais;
import com.betel.flowers.service.PaisService;
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
@Named(value = "paisBean")
@ViewScoped
public class PaisBean implements Serializable {

    private static final long serialVersionUID = -6059139354438256941L;

    private Pais nuevo;
    private Pais selected;
    private List<Pais> paices;

    @Inject
    private PaisService paisService;

    @PostConstruct
    public void init() {
        this.nuevo = new Pais();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.paices = this.paisService.obtenerListFlag(1);
        if (this.paices == null) {
            this.paices = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.paisService.insert(this.nuevo);
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
            Boolean exito = this.paisService.update(this.selected);
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

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.paisService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public Pais getNuevo() {
        return nuevo;
    }

    public void setNuevo(Pais nuevo) {
        this.nuevo = nuevo;
    }

    public Pais getSelected() {
        return selected;
    }

    public void setSelected(Pais selected) {
        this.selected = selected;
    }

    public List<Pais> getPaices() {
        return paices;
    }

    public void setPaices(List<Pais> paices) {
        this.paices = paices;
    }

}
