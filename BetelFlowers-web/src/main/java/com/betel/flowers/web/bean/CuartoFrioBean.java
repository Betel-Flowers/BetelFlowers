/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.CuartoFrio;
import com.betel.flowers.service.CuartoFrioService;
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
@Named(value = "cuartoFrioBean")
@ViewScoped
public class CuartoFrioBean implements Serializable {

    private static final long serialVersionUID = -3670473308153522455L;

    private CuartoFrio nuevo;
    private CuartoFrio selected;
    private List<CuartoFrio> cuartosFrio;

    @Inject
    private CuartoFrioService cuartoService;

    @PostConstruct
    public void init() {
        this.nuevo = new CuartoFrio();
        this.nuevo.setUsername("usertest");//usertest
        this.selected = null;
        this.cuartosFrio = this.cuartoService.obtenerListFlag(1);
        if (this.cuartosFrio == null) {
            this.cuartosFrio = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.cuartoService.insert(this.nuevo);
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
            Boolean exito = this.cuartoService.update(this.selected);
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
            Boolean exito = this.cuartoService.deteleFlag(this.selected);
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

    public CuartoFrio getNuevo() {
        return nuevo;
    }

    public void setNuevo(CuartoFrio nuevo) {
        this.nuevo = nuevo;
    }

    public CuartoFrio getSelected() {
        return selected;
    }

    public void setSelected(CuartoFrio selected) {
        this.selected = selected;
    }

    public List<CuartoFrio> getCuartosFrio() {
        return cuartosFrio;
    }

    public void setCuartosFrio(List<CuartoFrio> cuartosFrio) {
        this.cuartosFrio = cuartosFrio;
    }

}
