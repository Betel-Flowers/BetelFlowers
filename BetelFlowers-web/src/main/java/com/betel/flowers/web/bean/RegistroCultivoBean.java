/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Bloque;
import com.betel.flowers.model.RegistroCultivo;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BloqueService;
import com.betel.flowers.service.RegistroCultivoService;
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

/**
 *
 * @author luis
 */
@Named(value = "registroCultivoBean")
@ViewScoped
public class RegistroCultivoBean implements Serializable {

    private static final long serialVersionUID = 541497731252805986L;

    private RegistroCultivo nuevo;
    private RegistroCultivo selected;
    private List<RegistroCultivo> registrosCultivo;

    @Inject
    private RegistroCultivoService registroCultivoService;
    @Inject
    private VariedadService variedadService;
    @Inject
    private BloqueService bloqueService;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroCultivo();
        this.nuevo.setUsername("usertest");
        this.selected = new RegistroCultivo();
        this.registrosCultivo = this.registroCultivoService.obtenerLista();
        if (this.registrosCultivo == null) {
            this.registrosCultivo = new ArrayList<>();
        } else {
            Collections.reverse(this.registrosCultivo);
        }
    }

    public void add(ActionEvent evt) {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
        Bloque bloque = this.bloqueService.findByCodigo(this.nuevo.getBloque());
        this.nuevo.setBloque(bloque);
        this.nuevo.setVariedad(variedad);
        Boolean exito = this.registroCultivoService.insert(this.nuevo);
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
            Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad());
            Bloque bloque = this.bloqueService.findByCodigo(this.selected.getBloque());
            this.selected.setVariedad(variedad);
            this.selected.setBloque(bloque);
            Boolean exito = this.registroCultivoService.update(this.selected);
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
    
    public void changeVariedad() {
        if (this.nuevo.getVariedad().getCodigo() != null) {
            Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
            this.nuevo.setVariedad(variedad);
            
        }
    }

    public void onlyMalla() {
        this.nuevo.setTina(Boolean.FALSE);
    }

    public void onlyTina() {
        this.nuevo.setMalla(Boolean.FALSE);
    }

    public void onlyMallaSelect() {
        this.selected.setTina(Boolean.FALSE);
    }

    public void onlyTinaSelect() {
        this.selected.setMalla(Boolean.FALSE);
    }

    public RegistroCultivo getNuevo() {
        return nuevo;
    }

    public void setNuevo(RegistroCultivo nuevo) {
        this.nuevo = nuevo;
    }

    public RegistroCultivo getSelected() {
        return selected;
    }

    public void setSelected(RegistroCultivo selected) {
        this.selected = selected;
    }

    public List<RegistroCultivo> getRegistrosCultivo() {
        return registrosCultivo;
    }

    public void setRegistrosCultivo(List<RegistroCultivo> registrosCultivo) {
        this.registrosCultivo = registrosCultivo;
    }

}
