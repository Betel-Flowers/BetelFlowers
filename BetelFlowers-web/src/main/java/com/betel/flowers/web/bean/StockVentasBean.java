/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.StockVenta;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.StockVentasService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.util.FacesUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "stockVentasBean")
@ViewScoped
public class StockVentasBean {
    
    private StockVenta nuevo;
    private StockVenta selected;
    private List<StockVenta> stockVentas;
    private List<StockVenta> stockVentasG;
    
    @Inject
    private StockVentasService stockVentaService;
    @Inject
    private VariedadService variedadService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new StockVenta();
        this.nuevo.setUsername("usertes");
        this.selected = null;
        this.stockVentas = new ArrayList<>();
        this.stockVentasG = this.stockVentaService.obtenerListFlag(1);
        if (this.stockVentasG == null) {
            this.stockVentasG = new ArrayList<>();
        } else {
            Collections.reverse(this.stockVentasG);
        }
    }
    
    public void add(ActionEvent evt) {
        Boolean exito = this.stockVentaService.insert(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }
    
    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.stockVentaService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }
    
    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.stockVentaService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
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
    
    public StockVenta getNuevo() {
        return nuevo;
    }
    
    public void setNuevo(StockVenta nuevo) {
        this.nuevo = nuevo;
    }
    
    public StockVenta getSelected() {
        return selected;
    }
    
    public void setSelected(StockVenta selected) {
        this.selected = selected;
    }
    
    public List<StockVenta> getStockVentas() {
        return stockVentas;
    }
    
    public void setStockVentas(List<StockVenta> stockVentas) {
        this.stockVentas = stockVentas;
    }
    
    public List<StockVenta> getStockVentasG() {
        return stockVentasG;
    }
    
    public void setStockVentasG(List<StockVenta> stockVentasG) {
        this.stockVentasG = stockVentasG;
    }
    
}
