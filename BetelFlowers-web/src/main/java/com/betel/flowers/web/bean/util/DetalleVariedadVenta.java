/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ItemVariedadVenta;
import com.betel.flowers.model.Variedad;
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
public class DetalleVariedadVenta implements Serializable {

    private static final long serialVersionUID = -5273352684512119259L;

    private ItemVariedadVenta nuevo;
    private ItemVariedadVenta selected;
    private List<ItemVariedadVenta> detalle;

    @Inject
    private VariedadService variedadService;

    public DetalleVariedadVenta() {
        this.nuevo = new ItemVariedadVenta();
        this.selected = null;
        this.detalle = new ArrayList<>();
        this.variedadService = new VariedadService();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.detalle != null) {
            Boolean exito = this.detalle.add(nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new ItemVariedadVenta();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, ItemVariedadVenta select) {
        this.selected = select;
        if (this.selected != null
                && this.detalle != null
                && !this.detalle.isEmpty()) {
            Boolean exito = this.detalle.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public void loadVariedad() {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.nuevo.setVariedad(variedad);
        }
    }

    public void loadVariedadSelected() {
        Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.selected.setVariedad(variedad);
        }
    }

}
