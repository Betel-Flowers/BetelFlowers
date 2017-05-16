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
public class DetalleCajaVenta implements Serializable {

    private ItemVariedadVenta nuevo;
    private ItemVariedadVenta selected;
    private List<ItemVariedadVenta> detalleCajaVenta;

    @Inject
    private VariedadService variedadService;

    public DetalleCajaVenta() {
        this.nuevo = new ItemVariedadVenta();
        this.selected = null;
        this.detalleCajaVenta = new ArrayList<>();
        this.variedadService = new VariedadService();
    }
    
    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.detalleCajaVenta != null) {
            if (this.nuevo.getPrecioUnit() > 0.0) {
                Boolean exito = this.detalleCajaVenta.add(nuevo);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha agregado.");
                    this.nuevo = new ItemVariedadVenta();
                } else {
                    FacesUtil.addMessageError(null, "No se ha agregado.");
                }
            } else {
                FacesUtil.addMessageInfo("Por favor ingrese un precio.");
            }
        }
    }

    public void remove(ActionEvent evt, ItemVariedadVenta select) {
        this.selected = select;
        if (this.selected != null
                && this.detalleCajaVenta != null
                && !this.detalleCajaVenta.isEmpty()) {
            Boolean exito = this.detalleCajaVenta.remove(this.selected);
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
    
    public ItemVariedadVenta getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemVariedadVenta nuevo) {
        this.nuevo = nuevo;
    }

    public ItemVariedadVenta getSelected() {
        return selected;
    }

    public void setSelected(ItemVariedadVenta selected) {
        this.selected = selected;
    }

    public List<ItemVariedadVenta> getDetalleCajaVenta() {
        return detalleCajaVenta;
    }

    public void setDetalleCajaVenta(List<ItemVariedadVenta> detalleCajaVenta) {
        this.detalleCajaVenta = detalleCajaVenta;
    }
   
}
