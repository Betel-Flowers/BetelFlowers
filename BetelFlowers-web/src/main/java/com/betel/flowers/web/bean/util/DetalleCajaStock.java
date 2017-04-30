/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ItemCajaStock;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class DetalleCajaStock implements Serializable {

    private ItemCajaStock nuevo;
    private ItemCajaStock selected;
    private List<ItemCajaStock> detalleCajaStock;
    private List<ItemCajaStock> selectedItemsStock;

    public DetalleCajaStock() {
        this.nuevo = new ItemCajaStock();
        this.selected = null;
        this.detalleCajaStock = new ArrayList<>();
    }
    
    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.detalleCajaStock != null) {
            int index = this.detalleCajaStock.size();
            Boolean exito = this.detalleCajaStock.add(this.nuevo);
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

    public List<ItemCajaStock> getSelectedItemsStock() {
        return selectedItemsStock;
    }

    public void setSelectedItemsStock(List<ItemCajaStock> selectedItemsStock) {
        this.selectedItemsStock = selectedItemsStock;
    }
    
}
