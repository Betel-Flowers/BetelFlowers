/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.TipoCaja;
import com.betel.flowers.service.TipoCajaService;
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
@Named(value = "tipoCajaBean")
@ViewScoped
public class TipoCajaBean implements Serializable {

    private static final long serialVersionUID = -6582571527078632747L;

    private TipoCaja nuevo;
    private TipoCaja selected;
    private List<TipoCaja> tipoCajas;

    @Inject
    private TipoCajaService tipoCajaService;

    @PostConstruct
    public void init() {
        this.nuevo = new TipoCaja();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.tipoCajas = this.tipoCajaService.obtenerListFlag(1);
        if (this.tipoCajas == null) {
            this.tipoCajas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.tipoCajaService.insert(this.nuevo);
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
            Boolean exito = this.tipoCajaService.update(this.selected);
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
            Boolean exito = this.tipoCajaService.deteleFlag(this.selected);
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

    public TipoCaja getNuevo() {
        return nuevo;
    }

    public void setNuevo(TipoCaja nuevo) {
        this.nuevo = nuevo;
    }

    public TipoCaja getSelected() {
        return selected;
    }

    public void setSelected(TipoCaja selected) {
        this.selected = selected;
    }

    public List<TipoCaja> getTipoCajas() {
        return tipoCajas;
    }

    public void setTipoCajas(List<TipoCaja> tipoCajas) {
        this.tipoCajas = tipoCajas;
    }

}
