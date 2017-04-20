/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Causa;
import com.betel.flowers.model.Motivo;
import com.betel.flowers.service.CausaService;
import com.betel.flowers.service.MotivoService;
import com.betel.flowers.web.util.FacesUtil;
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
@Named(value = "motivoBean")
@ViewScoped
public class MotivoBean implements Serializable {

    private static final long serialVersionUID = 8876295135647440581L;

    private Motivo nuevo;
    private Motivo selected;
    private List<Motivo> motivos;

    @Inject
    private MotivoService motivoService;
    @Inject
    private CausaService causaService;

    @PostConstruct
    public void init() {
        this.nuevo = new Motivo();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.motivos = this.motivoService.obtenerListFlag(1);
        if (this.motivos == null) {
            this.motivos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Causa causa = this.causaService.findByCodigo(this.nuevo.getCausa());
        this.nuevo.setCausa(causa);
        Boolean exito = this.motivoService.insert(this.nuevo);
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
            Causa causa = this.causaService.findByCodigo(this.nuevo.getCausa());
            this.selected.setCausa(causa);
            Boolean exito = this.motivoService.update(this.selected);
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
            Boolean exito = this.motivoService.deteleFlag(this.selected);
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

    public Motivo getNuevo() {
        return nuevo;
    }

    public void setNuevo(Motivo nuevo) {
        this.nuevo = nuevo;
    }

    public Motivo getSelected() {
        return selected;
    }

    public void setSelected(Motivo selected) {
        this.selected = selected;
    }

    public List<Motivo> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<Motivo> motivos) {
        this.motivos = motivos;
    }
}
