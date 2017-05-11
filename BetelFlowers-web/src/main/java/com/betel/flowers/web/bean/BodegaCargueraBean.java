/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaCarguera;
import com.betel.flowers.service.BodegaCargueraService;
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
@Named(value = "bodegaCargueraBean")
@ViewScoped
public class BodegaCargueraBean implements Serializable {

    private static final long serialVersionUID = -3524157319549872731L;

    private BodegaCarguera nuevo;
    private BodegaCarguera selected;
    private List<BodegaCarguera> bodegas;

    @Inject
    private BodegaCargueraService bodegaCargueraService;

    @PostConstruct
    public void init() {
        this.nuevo = new BodegaCarguera();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.bodegas = this.bodegaCargueraService.obtenerListFlag(1);
        if (this.bodegas == null) {
            this.bodegas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.bodegaCargueraService.insert(this.nuevo);
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
            Boolean exito = this.bodegaCargueraService.update(this.selected);
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
            Boolean exito = this.bodegaCargueraService.deteleFlag(this.selected);
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

    public BodegaCarguera getNuevo() {
        return nuevo;
    }

    public void setNuevo(BodegaCarguera nuevo) {
        this.nuevo = nuevo;
    }

    public BodegaCarguera getSelected() {
        return selected;
    }

    public void setSelected(BodegaCarguera selected) {
        this.selected = selected;
    }

    public List<BodegaCarguera> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<BodegaCarguera> bodegas) {
        this.bodegas = bodegas;
    }

}
