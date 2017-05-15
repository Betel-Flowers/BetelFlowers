/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.DetalleVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class DetalleCajaVenta implements Serializable {

    private DetalleVenta nuevo;
    private DetalleVenta selected;
    private List<DetalleVenta> detalle;

    public DetalleCajaVenta() {
        this.nuevo = new DetalleVenta();
        this.selected = null;
        this.detalle = new ArrayList<>();
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

    public List<DetalleVenta> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleVenta> detalle) {
        this.detalle = detalle;
    }

}
