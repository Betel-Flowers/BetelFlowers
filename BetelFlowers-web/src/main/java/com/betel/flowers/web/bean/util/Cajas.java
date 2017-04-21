/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.MarcaCaja;
import com.betel.flowers.service.MarcaCajaService;
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
public class Cajas implements Serializable{
    
    private static final long serialVersionUID = -7849298998466250785L;
    
    //agregar y eliminar
    private MarcaCaja nuevo;
    private MarcaCaja selected;
    private List<MarcaCaja> cajas;
    
    @Inject
    MarcaCajaService marcaCajaService;

    public Cajas() {
        this.nuevo = new MarcaCaja();
        this.selected = null;
        this.marcaCajaService = new MarcaCajaService();
        if (this.cajas == null) {
            this.cajas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.cajas != null) {
            MarcaCaja mCaja = this.marcaCajaService.findByCodigo(this.nuevo);
            this.setNuevo(mCaja);
            Boolean exito = this.cajas.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new MarcaCaja();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, MarcaCaja select) {
        this.selected = select;
        if (this.selected != null
                && this.cajas != null
                && !this.cajas.isEmpty()) {
            Boolean exito = this.cajas.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public MarcaCaja getNuevo() {
        return nuevo;
    }

    public void setNuevo(MarcaCaja nuevo) {
        this.nuevo = nuevo;
    }

    public MarcaCaja getSelected() {
        return selected;
    }

    public void setSelected(MarcaCaja selected) {
        this.selected = selected;
    }

    public List<MarcaCaja> getCajas() {
        return cajas;
    }

    public void setCajas(List<MarcaCaja> cajas) {
        this.cajas = cajas;
    }
}
