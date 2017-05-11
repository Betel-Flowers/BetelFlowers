/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.DetalleDeBaja;
import com.betel.flowers.model.MotivoEmpaque;
import com.betel.flowers.model.RegistroDeBaja;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.service.CausaEmpaqueService;
import com.betel.flowers.service.MotivoEmpaqueService;
import com.betel.flowers.service.RegistroDeBajaService;
import com.betel.flowers.service.RegistroExportacionService;
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

/**
 *
 * @author luis
 */
@Named(value = "registroDeBajaBean")
@ViewScoped
public class RegistroDeBajaBean implements Serializable {
    
    private static final long serialVersionUID = 5473862015518957692L;
    
    private RegistroDeBaja nuevo;
    private RegistroDeBaja selected;
    private List<RegistroDeBaja> registrosEnBaja;
    private List<RegistroDeBaja> filteredRegistrosEnBaja;
    private List<RegistroExportacion> registrosBarcode;
    private List<RegistroExportacion> registrosBarcodeSelected;
    private Integer cantidadTotalContenedor;
    private Boolean findBarcode;
    
    @Inject
    private RegistroDeBajaService registroBajaService;
    @Inject
    private CausaEmpaqueService causaService;
    @Inject
    private MotivoEmpaqueService motivoService;
    @Inject
    private RegistroExportacionService registroExportacionService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new RegistroDeBaja();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.cantidadTotalContenedor = 0;
        this.findBarcode = Boolean.FALSE;
        this.registrosBarcode = new ArrayList<>();
        this.registrosBarcodeSelected = null;
        this.filteredRegistrosEnBaja = null;
        this.registrosEnBaja = this.registroBajaService.obtenerListFlag(1);
        if (this.registrosEnBaja == null) {
            this.registrosEnBaja = new ArrayList<>();
        } else {
            Collections.reverse(this.registrosEnBaja);
        }
    }
    
    public void findRegistroExportacionBarcode(ActionEvent evt) {
        if (this.nuevo.getBarcode() != null && !this.nuevo.getBarcode().equals("")) {
            List<RegistroExportacion> registros = this.registroExportacionService.
                    obtenerListBarcode(this.nuevo.getBarcode());
            if (registros != null && !registros.isEmpty()) {
                this.registrosBarcode = registros;
                this.calculateTotalContenedor();
                this.setFindBarcode(Boolean.TRUE);
            } else {
                this.registrosBarcode = new ArrayList<>();
                this.cantidadTotalContenedor = 0;
                this.setFindBarcode(Boolean.FALSE);
            }
        }
    }
    
    public void add(ActionEvent evt) {
        if (this.nuevo.getDetalle() != null && !this.nuevo.getDetalle().isEmpty()) {
            this.asignarCodeMotivosForDetail();
            this.updateContentsBarcodeDown();
            Boolean exito = this.registroBajaService.insert(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Por favor seleccione motivos para la veriedad seleccionada.");
        }
    }
    
    private void asignarCodeMotivosForDetail() {
        if (this.nuevo.getDetalle() != null && !this.nuevo.getDetalle().isEmpty()) {
            for (int i = 0; i < this.nuevo.getDetalle().size(); i++) {
                MotivoEmpaque mot = this.motivoService.
                        findByDescripcion(this.nuevo.getDetalle().get(i).getMotivo());
                this.nuevo.getDetalle().get(i).setMotivo(mot);
            }
        }
    }
    
    private void updateContentsBarcodeDown() {
        if (this.nuevo.getDetalle() != null && !this.nuevo.getDetalle().isEmpty()) {
            for (DetalleDeBaja itemDetailBaja : this.nuevo.getDetalle()) {
                Integer stock = itemDetailBaja.getItem().getTotalTallos() - itemDetailBaja.getCantidad();
                RegistroExportacion registro = this.registroExportacionService.findByCodigo(itemDetailBaja.getItem());
                registro.setStock(stock);
                this.registroExportacionService.update(registro);
                itemDetailBaja.setItem(registro);
            }
        }
    }
    
    public void onClikListenerSelectList() {
        if (this.registrosBarcodeSelected != null && !this.registrosBarcodeSelected.isEmpty()) {
            for (RegistroExportacion item : this.registrosBarcodeSelected) {
                DetalleDeBaja itemBaja = new DetalleDeBaja();
                itemBaja.setItem(item);
                itemBaja.setMotivo(new MotivoEmpaque());
                this.getNuevo().getDetalle().add(itemBaja);
            }
        }
        this.registrosBarcode = new ArrayList<>();
        this.registrosBarcodeSelected = null;
        this.cantidadTotalContenedor = 0;
    }
    
    private void calculateTotalContenedor() {
        Integer total = 0;
        if (this.registrosBarcode != null && !this.registrosBarcode.isEmpty()) {
            for (int i = 0; i < this.registrosBarcode.size(); i++) {
                total = total + this.registrosBarcode.get(i).getTotalTallos();
            }
        }
        this.setCantidadTotalContenedor(total);
    }
    
    public List<DetalleDeBaja> listInsideExportacion(RegistroDeBaja registro) {
        List<DetalleDeBaja> list = new ArrayList<>();
        if (registro != null) {
            if (registro.getDetalle() != null && !registro.getDetalle().isEmpty()) {
                list = registro.getDetalle();
            }
        }
        return list;
    }
    
    public RegistroDeBaja getNuevo() {
        return nuevo;
    }
    
    public void setNuevo(RegistroDeBaja nuevo) {
        this.nuevo = nuevo;
    }
    
    public RegistroDeBaja getSelected() {
        return selected;
    }
    
    public void setSelected(RegistroDeBaja selected) {
        this.selected = selected;
    }
    
    public List<RegistroDeBaja> getRegistrosEnBaja() {
        return registrosEnBaja;
    }
    
    public void setRegistrosEnBaja(List<RegistroDeBaja> registrosEnBaja) {
        this.registrosEnBaja = registrosEnBaja;
    }
    
    public List<RegistroDeBaja> getFilteredRegistrosEnBaja() {
        return filteredRegistrosEnBaja;
    }
    
    public void setFilteredRegistrosEnBaja(List<RegistroDeBaja> filteredRegistrosEnBaja) {
        this.filteredRegistrosEnBaja = filteredRegistrosEnBaja;
    }
    
    public List<RegistroExportacion> getRegistrosBarcode() {
        return registrosBarcode;
    }
    
    public void setRegistrosBarcode(List<RegistroExportacion> registrosBarcode) {
        this.registrosBarcode = registrosBarcode;
    }
    
    public List<RegistroExportacion> getRegistrosBarcodeSelected() {
        return registrosBarcodeSelected;
    }
    
    public void setRegistrosBarcodeSelected(List<RegistroExportacion> registrosBarcodeSelected) {
        this.registrosBarcodeSelected = registrosBarcodeSelected;
    }
    
    public Integer getCantidadTotalContenedor() {
        return cantidadTotalContenedor;
    }
    
    public void setCantidadTotalContenedor(Integer cantidadTotalContenedor) {
        this.cantidadTotalContenedor = cantidadTotalContenedor;
    }
    
    public Boolean getFindBarcode() {
        return findBarcode;
    }
    
    public void setFindBarcode(Boolean findBarcode) {
        this.findBarcode = findBarcode;
    }
    
}
