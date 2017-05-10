/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.BodegaCarguera;
import com.betel.flowers.service.BodegaCargueraService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
public class Bodegas implements Serializable {

    private static final long serialVersionUID = -6024120883528209651L;

    private BodegaCarguera nuevo;
    private BodegaCarguera selected;
    private List<BodegaCarguera> bodegas;

    @Inject
    private BodegaCargueraService bodegaService;

    public Bodegas() {
        this.nuevo = new BodegaCarguera();
        this.selected = null;
        this.bodegaService = new BodegaCargueraService();
        if (this.bodegas == null) {
            this.bodegas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.bodegas != null) {
            BodegaCarguera bodega = this.bodegaService.findByCodigo(this.nuevo);
            this.setNuevo(bodega);
            Boolean exito = this.bodegas.add(this.getNuevo());
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new BodegaCarguera();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, BodegaCarguera select) {
        this.selected = select;
        if (this.selected != null
                && this.bodegas != null
                && !this.bodegas.isEmpty()) {
            Boolean exito = this.bodegas.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
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
