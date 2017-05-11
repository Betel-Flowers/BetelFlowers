/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Especie;
import com.betel.flowers.service.EspecieService;
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
@Named(value = "especieBean")
@ViewScoped
public class EspecieBean implements Serializable {

    private static final long serialVersionUID = -6508363715126992124L;

    private Especie nuevo;
    private Especie selected;
    private List<Especie> especies;

    @Inject
    private EspecieService especieService;

    @PostConstruct
    public void init() {
        this.nuevo = new Especie();
        this.nuevo.setUsername("usertest");//usertest
        this.selected = null;
        this.especies = this.especieService.obtenerListFlag(1);
        if (this.especies == null) {
            this.especies = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.especieService.insert(this.nuevo);
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
            Boolean exito = this.especieService.update(this.selected);
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
            Boolean exito = this.especieService.deteleFlag(this.selected);
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

    public Especie getNuevo() {
        return nuevo;
    }

    public void setNuevo(Especie nuevo) {
        this.nuevo = nuevo;
    }

    public Especie getSelected() {
        return selected;
    }

    public void setSelected(Especie selected) {
        this.selected = selected;
    }

    public List<Especie> getEspecies() {
        return especies;
    }

    public void setEspecies(List<Especie> especies) {
        this.especies = especies;
    }

}
