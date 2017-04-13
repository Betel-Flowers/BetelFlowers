/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.DetalleNacional;
import com.betel.flowers.model.Motivo;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class DetallesNacional implements Serializable {

    private static final long serialVersionUID = -6584754332003378414L;

    private DetalleNacional nuevo;
    private DetalleNacional selected;
    private List<DetalleNacional> detalles;

    public DetallesNacional() {
        this.nuevo = new DetalleNacional();
        this.detalles = new ArrayList<>();
        this.selected = null;
    }

    private void init() {
        this.nuevo = new DetalleNacional();
        this.selected = null;
    }

    public void add() {
        if (this.nuevo != null) {
            int index = this.detalles.size();
            this.nuevo.setIndex(index);
            this.detalles.add(this.nuevo);
            int exito = this.detalles.size();
            if (exito != 0 && exito > index) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, DetalleNacional select) {
        this.selected = select;
        if (this.selected != null) {
            int exito = this.detalles.size();
            this.detalles.remove(this.selected);
            int size = this.detalles.size();
            if (exito > size) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public DetalleNacional getNuevo() {
        return nuevo;
    }

    public void setNuevo(DetalleNacional nuevo) {
        this.nuevo = nuevo;
    }

    public DetalleNacional getSelected() {
        return selected;
    }

    public void setSelected(DetalleNacional selected) {
        this.selected = selected;
    }

    public List<DetalleNacional> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNacional> detalles) {
        this.detalles = detalles;
    }

}
