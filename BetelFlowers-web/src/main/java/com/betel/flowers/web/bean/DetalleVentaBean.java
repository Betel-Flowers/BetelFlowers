/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.DetalleVenta;
import com.betel.flowers.service.DetalleVentaService;
import com.betel.flowers.web.bean.util.BarcodeRegistroVenta;
import com.betel.flowers.web.bean.util.DetalleCajaVenta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "detalleVentaBean")
@ViewScoped
public class DetalleVentaBean implements Serializable {

    private static final long serialVersionUID = 7020916561769238382L;

    private DetalleVenta nuevo;
    private DetalleVenta selected;
    private DetalleVenta remove;
    private List<DetalleVenta> detalleVentas;
    private List<DetalleVenta> detalleVentasG;
    private List<BarcodeRegistroVenta> barcodeList;
    private DetalleCajaVenta detalle;
    private Boolean gerated;
    private String barcode;

    @Inject
    private DetalleVentaService detalleVentaService;

    public DetalleVentaBean() {
        this.nuevo = new DetalleVenta();
        this.nuevo.setUsername("usertest");
        this.selected = new DetalleVenta();
        this.detalleVentas = new ArrayList<>();
        this.gerated = Boolean.TRUE;
        this.detalle = new DetalleCajaVenta();
        this.barcodeList = new ArrayList<>();
        this.detalleVentasG = this.detalleVentaService.obtenerListFlag(1);
        if (this.detalleVentasG == null) {
            this.detalleVentasG = new ArrayList<>();
        } else {
            Collections.reverse(this.detalleVentasG);
            this.loadBarcodeList();
        }
    }

    private void loadBarcodeList() {
        if (this.detalleVentasG != null && !this.detalleVentasG.isEmpty()) {
            List<DetalleVenta> unique = this.selectBarcode(this.detalleVentasG);
            for (DetalleVenta registro : unique) {
                BarcodeRegistroVenta barcodes = new BarcodeRegistroVenta();
                barcodes.setCreationDate(registro.getCreationDate());
                barcodes.setBarcode(registro.getBarcode());
                barcodes.setUsername(registro.getUsername());
                for (int i = 0; i < this.detalleVentasG.size(); i++) {
                    if (this.detalleVentasG.get(i).getBarcode().equals(registro.getBarcode())) {
                        barcodes.getListBarcode().add(this.detalleVentasG.get(i));
                    }
                }
                this.barcodeList.add(barcodes);
            }
        }
    }

    private List<DetalleVenta> selectBarcode(List<DetalleVenta> barcode) {

        List<DetalleVenta> unique = new ArrayList<>();
        if (barcode != null && !barcode.isEmpty()) {
            unique.add(barcode.get(0));
            for (int i = 0; i < barcode.size(); i++) {
                if (!(barcode.get(i).getBarcode().equals(unique.get(unique.size() - 1).getBarcode()))) {
                    unique.add(barcode.get(i));
                }
            }
        }
        return unique;
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

    public DetalleVenta getRemove() {
        return remove;
    }

    public void setRemove(DetalleVenta remove) {
        this.remove = remove;
    }

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(List<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public List<DetalleVenta> getDetalleVentasG() {
        return detalleVentasG;
    }

    public void setDetalleVentasG(List<DetalleVenta> detalleVentasG) {
        this.detalleVentasG = detalleVentasG;
    }

    public DetalleCajaVenta getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCajaVenta detalle) {
        this.detalle = detalle;
    }

    public Boolean getGerated() {
        return gerated;
    }

    public void setGerated(Boolean gerated) {
        this.gerated = gerated;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
