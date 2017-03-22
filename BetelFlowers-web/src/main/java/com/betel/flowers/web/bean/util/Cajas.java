/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Caja;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class Cajas implements Serializable{
    
    private static final long serialVersionUID = -7849298998466250785L;
    
    //agregar y eliminar
    private Caja nuevo;
    private Caja selected;
    private List<Caja> cajas;

    public Cajas() {
        this.nuevo = new Caja();
        this.selected = null;
        if (this.cajas == null) {
            this.cajas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.cajas != null) {
            int index = this.cajas.size();
            this.nuevo.setIndex(index);
            Boolean exito = this.cajas.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new Caja();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, Caja select) {
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

    public Caja getNuevo() {
        return nuevo;
    }

    public void setNuevo(Caja nuevo) {
        this.nuevo = nuevo;
    }

    public Caja getSelected() {
        return selected;
    }

    public void setSelected(Caja selected) {
        this.selected = selected;
    }

    public List<Caja> getCajas() {
        return cajas;
    }

    public void setCajas(List<Caja> cajas) {
        this.cajas = cajas;
    }
}
