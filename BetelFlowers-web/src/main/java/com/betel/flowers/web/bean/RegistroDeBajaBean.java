/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.RegistroDeBaja;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.service.CausaEmpaqueService;
import com.betel.flowers.service.MotivoEmpaqueService;
import com.betel.flowers.service.RegistroDeBajaService;
import com.betel.flowers.service.RegistroExportacionService;
import java.io.Serializable;
import java.util.ArrayList;
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
    private List<RegistroExportacion> registrosBarcode;
    private List<RegistroExportacion> registrosBarcodeSelected;

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
        this.registrosBarcode = new ArrayList<>();
        this.registrosEnBaja = this.registroBajaService.obtenerListFlag(1);
        if (this.registrosEnBaja == null) {
            this.registrosEnBaja = new ArrayList<>();
        }
    }

    public void findRegistroExportacionBarcode(ActionEvent evt) {
        if (this.nuevo.getBarcode() != null && !this.nuevo.getBarcode().equals("")) {
            List<RegistroExportacion> registros = this.registroExportacionService.
                    obtenerListBarcode(this.nuevo.getBarcode());
            this.registrosBarcode = registros;
        }
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

}
