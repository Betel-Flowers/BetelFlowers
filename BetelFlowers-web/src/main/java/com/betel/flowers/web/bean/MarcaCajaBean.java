/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.MarcaCaja;
import com.betel.flowers.service.MarcaCajaService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
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
@Named(value = "marcaCajaBean")
@ViewScoped
public class MarcaCajaBean implements Serializable {

    private static final long serialVersionUID = 1240479047930374158L;

    private MarcaCaja nuevo;
    private MarcaCaja selected;
    private List<MarcaCaja> marcasCaja;

    @Inject
    private MarcaCajaService marcaCajaService;

    @PostConstruct
    public void init() {
        this.nuevo = new MarcaCaja();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.marcasCaja = this.marcaCajaService.obtenerListFlag(1);
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.marcaCajaService.insert(this.nuevo);
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
            Boolean exito = this.marcaCajaService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.marcaCajaService.deteleFlag(this.selected);
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

    public MarcaCaja getNuevo() {
        return nuevo;
    }

    public void setNuevo(MarcaCaja nuevo) {
        this.nuevo = nuevo;
    }

    public MarcaCaja getSelected() {
        return selected;
    }

    public void setSelected(MarcaCaja selected) {
        this.selected = selected;
    }

    public List<MarcaCaja> getMarcasCaja() {
        return marcasCaja;
    }

    public void setMarcasCaja(List<MarcaCaja> marcasCaja) {
        this.marcasCaja = marcasCaja;
    }

}
