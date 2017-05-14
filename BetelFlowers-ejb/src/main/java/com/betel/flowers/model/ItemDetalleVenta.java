/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import java.util.ArrayList;
import java.util.List;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Embedded
public class ItemDetalleVenta {

    private Integer cantidadCaja;
    private Integer totalTallosCaja;
    private Double subTotalCaja;

    @Reference
    private TipoCaja cajaTipo;
    @Reference
    private MarcaCaja marcaCaja;
    @Embedded
    private List<ItemVariedadVenta> contenedor;

    public ItemDetalleVenta() {
        this.cantidadCaja = 0;
        this.cajaTipo = new TipoCaja();
        this.marcaCaja = new MarcaCaja();
        this.contenedor = new ArrayList<>();
    }

    public Integer getCantidadCaja() {
        return cantidadCaja;
    }

    public void setCantidadCaja(Integer cantidadCaja) {
        this.cantidadCaja = cantidadCaja;
    }

    public TipoCaja getCajaTipo() {
        return cajaTipo;
    }

    public void setCajaTipo(TipoCaja cajaTipo) {
        this.cajaTipo = cajaTipo;
    }

    public MarcaCaja getMarcaCaja() {
        return marcaCaja;
    }

    public void setMarcaCaja(MarcaCaja marcaCaja) {
        this.marcaCaja = marcaCaja;
    }

    public List<ItemVariedadVenta> getContenedor() {
        return contenedor;
    }

    public void setContenedor(List<ItemVariedadVenta> contenedor) {
        this.contenedor = contenedor;
    }

    public Integer getTotalTallosCaja() {
        return this.getCantidadCaja() * this.calcularTotalTallosContenedor();
    }

    public void setTotalTallosCaja(Integer totalTallosCaja) {
        this.totalTallosCaja = totalTallosCaja;
    }

    public Double getSubTotalCaja() {
        return this.getSubTotalCaja() * this.calcularSubTotalContenedor();
    }

    public void setSubTotalCaja(Double subTotalCaja) {
        this.subTotalCaja = subTotalCaja;
    }

    private Integer calcularTotalTallosContenedor() {
        Integer total = 0;
        if (this.contenedor != null && !this.contenedor.isEmpty()) {
            for (ItemVariedadVenta item : this.contenedor) {
                total = total + item.getTotalTallos();
            }
        }
        return total;
    }

    private Double calcularSubTotalContenedor() {
        Double subtotal = 0.0;
        if (this.contenedor != null && !this.contenedor.isEmpty()) {
            for (ItemVariedadVenta item : this.contenedor) {
                subtotal = subtotal + item.getSubTotal();
            }
        }
        return subtotal;
    }

}
