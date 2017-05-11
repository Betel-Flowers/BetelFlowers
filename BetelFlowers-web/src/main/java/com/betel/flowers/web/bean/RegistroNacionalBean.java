/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.DetalleNacional;
import com.betel.flowers.model.Motivo;
import com.betel.flowers.model.RegistroNacional;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.CausaService;
import com.betel.flowers.service.MotivoService;
import com.betel.flowers.service.RegistroNacionalService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
@Named(value = "registroNacionalBean")
@ViewScoped
public class RegistroNacionalBean implements Serializable {

    private static final long serialVersionUID = 2110096779145174998L;

    private RegistroNacional nuevo;
    private RegistroNacional selected;
    private List<RegistroNacional> registrosNacional;
    private List<Motivo> motivos;
    private List<Motivo> selectionMotivos;

    @Inject
    private RegistroNacionalService registroNacionalService;
    @Inject
    private CausaService causaService;
    @Inject
    private VariedadService variedadService;
    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private MotivoService motivoServicce;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroNacional();
        this.nuevo.setUsername("usertest"); //usertest
        this.nuevo.setBarcode(this.generatedBarcode());
        this.motivos = this.motivoServicce.obtenerListFlag(1);
        this.registrosNacional = this.registroNacionalService.obtenerListFlag(1);
        if (this.registrosNacional == null) {
            this.registrosNacional = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo.getDetalle() != null && !this.nuevo.getDetalle().isEmpty()) {
            Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
            BodegaVirtual bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
            this.nuevo.setBodega(bodega);
            this.nuevo.setVariedad(variedad);
            Boolean exito = this.registroNacionalService.insert(this.nuevo);
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

    public void remove(ActionEvent evt, RegistroNacional select) {
        this.selected = select;
        if (this.selected != null) {
            Boolean exito = this.registroNacionalService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void addItemDetail(ActionEvent evt) {
        if (this.selectionMotivos != null && !this.selectionMotivos.isEmpty()) {
            for (Motivo mt : this.selectionMotivos) {
                this.nuevo.getDetalle().add(new DetalleNacional(mt.getCantidad(), mt));
            }
        }
        this.selectionMotivos = null;
        this.motivos = this.motivoServicce.obtenerListFlag(1);
    }

    public void removeItemDetail(ActionEvent evt, DetalleNacional det) {
        if (this.nuevo.getDetalle() != null && !this.nuevo.getDetalle().isEmpty()) {
            this.nuevo.getDetalle().remove(det);
        }
    }

    private String generatedBarcode() {
        GregorianCalendar calendario = new GregorianCalendar();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
        return "BETEL-RN" + RandomStringUtils.randomNumeric(4) + "-IMG-" + format.format(calendario.getTime());
    }

    public List<DetalleNacional> listBardodeInsideList(RegistroNacional barcodeItem) {
        List<DetalleNacional> list = new ArrayList<>();
        if (barcodeItem != null) {
            if (barcodeItem.getDetalle() != null && !barcodeItem.getDetalle().isEmpty()) {
                list = barcodeItem.getDetalle();
            }
        }
        return list;
    }

    public List<RegistroNacional> getRegistrosNacional() {
        return registrosNacional;
    }

    public void setRegistrosNacional(List<RegistroNacional> registrosNacional) {
        this.registrosNacional = registrosNacional;
    }

    public RegistroNacional getSelected() {
        return selected;
    }

    public void setSelected(RegistroNacional selected) {
        this.selected = selected;
    }

    public RegistroNacional getNuevo() {
        return nuevo;
    }

    public void setNuevo(RegistroNacional nuevo) {
        this.nuevo = nuevo;
    }

    public List<Motivo> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<Motivo> motivos) {
        this.motivos = motivos;
    }

    public List<Motivo> getSelectionMotivos() {
        return selectionMotivos;
    }

    public void setSelectionMotivos(List<Motivo> selectionMotivos) {
        this.selectionMotivos = selectionMotivos;
    }

}
