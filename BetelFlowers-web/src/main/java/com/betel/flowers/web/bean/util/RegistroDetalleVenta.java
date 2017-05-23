/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.DetalleVenta;
import com.betel.flowers.service.VariedadService;
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
public class RegistroDetalleVenta implements Serializable {

    private static final long serialVersionUID = 7996508428635273200L;

    private DetalleVenta nuevo;
    private DetalleVenta selected;
    private List<DetalleVenta> itemsDetalleVenta;

    @Inject
    private VariedadService variedadService;

    public RegistroDetalleVenta() {
        this.nuevo = new DetalleVenta();
        this.selected = null;
        this.itemsDetalleVenta = new ArrayList<>();
        this.variedadService = new VariedadService();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.itemsDetalleVenta != null) {
            Boolean exito = this.itemsDetalleVenta.add(nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new DetalleVenta();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, DetalleVenta select) {
        this.selected = select;
        if (this.selected != null
                && this.itemsDetalleVenta != null
                && !this.itemsDetalleVenta.isEmpty()) {
            Boolean exito = this.itemsDetalleVenta.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public void allInsertsDetail(ActionEvent evt) {

    }

    public DetalleVenta getNuevo() {
        return nuevo;
    }

    public void setNuevo(DetalleVenta nuevo) {
        this.nuevo = nuevo;
    }

    public DetalleVenta getSelected() {
        return selected;
    }

    public void setSelected(DetalleVenta selected) {
        this.selected = selected;
    }

    public List<DetalleVenta> getItemsDetalleVenta() {
        return itemsDetalleVenta;
    }

    public void setItemsDetalleVenta(List<DetalleVenta> itemsDetalleVenta) {
        this.itemsDetalleVenta = itemsDetalleVenta;
    }

}
