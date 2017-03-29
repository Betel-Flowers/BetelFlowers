/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.StockVenta;
import com.betel.flowers.model.TipoCaja;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.StockVentasService;
import com.betel.flowers.service.TipoCajaService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author luis
 */
@Named(value = "stockVentasBean")
@ViewScoped
public class StockVentasBean implements Serializable {

    private static final long serialVersionUID = -6549694294186350254L;

    private StockVenta nuevo;
    private StockVenta selected;
    private StockVenta remove;
    private List<StockVenta> stockVentas;
    private List<StockVenta> stockVentasG;
    private Boolean gerated;

    @Inject
    private StockVentasService stockVentaService;
    @Inject
    private VariedadService variedadService;
    @Inject
    private TipoCajaService tipoCajaService;

    @PostConstruct
    public void init() {
        this.nuevo = new StockVenta();
        this.nuevo.setUsername("usertest");
        this.selected = new StockVenta();
        this.stockVentas = new ArrayList<>();
        this.gerated = Boolean.TRUE;
        this.stockVentasG = this.stockVentaService.obtenerListFlag(1);
        if (this.stockVentasG == null) {
            this.stockVentasG = new ArrayList<>();
        } else {
            Collections.reverse(this.stockVentasG);
        }
    }

    public void generateContainer(ActionEvent evt) {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
        TipoCaja caja = this.tipoCajaService.findByCodigo(this.nuevo.getCaja());
        this.nuevo.setVariedad(variedad);
        this.nuevo.setCaja(caja);
        this.nuevo.setCodigo(this.generatedTempCode());
        Boolean exito = this.stockVentas.add(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.nuevo = new StockVenta();
            this.nuevo.setUsername("usertest");//usertest
            this.nuevo.setVariedad(new Variedad());
            this.nuevo.setCaja(new TipoCaja());
            this.stateGenetated();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
        }
    }

    public void updateContainer(ActionEvent evt) {
        if (this.selected != null && this.remove != null) {
            Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad());
            TipoCaja caja = this.tipoCajaService.findByCodigo(this.selected.getCaja());
            this.selected.setVariedad(variedad);
            this.selected.setCaja(caja);
            int index = this.stockVentas.indexOf(this.selected);
            Boolean exito = this.stockVentas.remove(this.remove);
            this.stockVentas.add(index, this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.stateGenetated();
                this.selected = new StockVenta();
                this.remove = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void removeContainer(ActionEvent evt, StockVenta select) {
        this.remove = select;
        if (this.remove != null) {
            Boolean exito = this.stockVentas.remove(this.remove);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.stateGenetated();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    private Integer generatedTempCode() {
        Integer number = 0;
        number = new Integer(RandomStringUtils.randomNumeric(4));
        return number;
    }

    private void stateGenetated() {
        if (this.stockVentas == null || this.stockVentas.isEmpty()) {
            this.gerated = Boolean.TRUE;
        } else {
            this.gerated = Boolean.FALSE;
        }
    }

    public void enviarOriginalRegister(ActionEvent evt, StockVenta select) {
        this.remove = select;
    }

    private Boolean allInserts() {
        Boolean exito = Boolean.FALSE;
        if (this.stockVentas != null && !this.stockVentas.isEmpty()) {
            for (int i = 0; i < this.stockVentas.size(); i++) {
                exito = this.stockVentaService.insert(this.stockVentas.get(i));
                if (!exito) {
                    exito = Boolean.FALSE;
                    break;
                }
            }
        }
        return exito;
    }

    private void generatedBarcode() {
        if (this.stockVentas != null && !this.stockVentas.isEmpty()) {
            int size = this.stockVentas.size();
            int length = this.stockVentasG.size();
            String code = RandomStringUtils.randomNumeric(2);
            String barcode = "BETEL-SK" + code + "" + size + "" + length;
            for (int i = 0; i < size; i++) {
                Integer total = this.stockVentas.get(i).getTotalTallos();
                this.stockVentas.get(i).setTotalTallos(total);
                this.stockVentas.get(i).setBarcode(barcode);
            }
        }
    }

    public void add(ActionEvent evt) {
        this.generatedBarcode();
        Boolean exito = this.allInserts();
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

    public Boolean getGerated() {
        return gerated;
    }

    public void setGerated(Boolean gerated) {
        this.gerated = gerated;
    }

}
