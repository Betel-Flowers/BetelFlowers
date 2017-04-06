/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;


import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.service.OpcionSistemaService;
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
@Named(value = "opcionSistemaBean")
@ViewScoped
public class OpcionSistemaBean implements Serializable{
    
    private static final long serialVersionUID = 2464916801436130583L;
    
    private OpcionSistema nuevo;
    private OpcionSistema selected;
    private List<OpcionSistema> opcionSistemas;

    @Inject
    private OpcionSistemaService opcionSistemaService;

    @PostConstruct
    public void init() {
        this.nuevo = new OpcionSistema();
        this.selected = null;
        this.opcionSistemas = this.opcionSistemaService.obtenerListFlag(1);
        if (this.opcionSistemas == null) {
            this.opcionSistemas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.opcionSistemaService.insert(this.nuevo);
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
            Boolean exito = this.opcionSistemaService.update(this.selected);
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
            Boolean exito = this.opcionSistemaService.deteleFlag(this.selected);
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

    public OpcionSistema getNuevo() {
        return nuevo;
    }

    public void setNuevo(OpcionSistema nuevo) {
        this.nuevo = nuevo;
    }

    public OpcionSistema getSelected() {
        return selected;
    }

    public void setSelected(OpcionSistema selected) {
        this.selected = selected;
    }

    public List<OpcionSistema> getOpcionSistemas() {
        return opcionSistemas;
    }

    public void setOpcionSistemas(List<OpcionSistema> opcionSistemas) {
        this.opcionSistemas = opcionSistemas;
    }
    
}
