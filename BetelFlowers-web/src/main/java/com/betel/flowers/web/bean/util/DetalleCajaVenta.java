/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.ItemDetalleVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class DetalleCajaVenta implements Serializable {

    private ItemDetalleVenta nuevo;
    private ItemDetalleVenta selected;
    private List<ItemDetalleVenta> detalle;

    public DetalleCajaVenta() {
        this.nuevo = new ItemDetalleVenta();
        this.selected = null;
        this.detalle = new ArrayList<>();
    }

    public ItemDetalleVenta getNuevo() {
        return nuevo;
    }

    public void setNuevo(ItemDetalleVenta nuevo) {
        this.nuevo = nuevo;
    }

    public ItemDetalleVenta getSelected() {
        return selected;
    }

    public void setSelected(ItemDetalleVenta selected) {
        this.selected = selected;
    }

    public List<ItemDetalleVenta> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<ItemDetalleVenta> detalle) {
        this.detalle = detalle;
    }

}
