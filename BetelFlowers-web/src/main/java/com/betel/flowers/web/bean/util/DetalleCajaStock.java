/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ItemCajaStock;
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
public class DetalleCajaStock implements Serializable {

    private ItemCajaStock nuevo;
    private ItemCajaStock selected;
    private List<ItemCajaStock> detalleCajaStock;

    @Inject
    private VariedadService variedadService;

    public DetalleCajaStock() {
        this.nuevo = new ItemCajaStock();
        this.selected = null;
        this.detalleCajaStock = new ArrayList<>();
        this.variedadService = new VariedadService();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.detalleCajaStock != null) {
            Boolean exito = this.detalleCajaStock.add(nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new ItemCajaStock();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, ItemCajaStock select) {
        this.selected = select;
        if (this.selected != null
                && this.detalleCajaStock != null
                && !this.detalleCajaStock.isEmpty()) {
            Boolean exito = this.detalleCajaStock.remove(this.selected);
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

    public ItemCajaStock getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemCajaStock nuevo) {
        this.nuevo = nuevo;
    }

    public ItemCajaStock getSelected() {
        return selected;
    }

    public void setSelected(ItemCajaStock selected) {
        this.selected = selected;
    }

    public List<ItemCajaStock> getDetalleCajaStock() {
        return detalleCajaStock;
    }

    public void setDetalleCajaStock(List<ItemCajaStock> detalleCajaStock) {
        this.detalleCajaStock = detalleCajaStock;
    }
}
