/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.TipoTrabajo;
import com.betel.flowers.service.TipoTrabajoService;
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
@Named(value = "tipoTrabajoBean")
@ViewScoped
public class TipoTrabajoBean implements Serializable{

    private static final long serialVersionUID = 2049266426226198660L;
    
    private TipoTrabajo nuevo;
    private TipoTrabajo selected;
    private List<TipoTrabajo> tipoTrabajos;

    @Inject
    private TipoTrabajoService tipoTrabajoService;

    @PostConstruct
    public void init() {
        this.nuevo = new TipoTrabajo();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.tipoTrabajos = this.tipoTrabajoService.obtenerListFlag(1);
        if (this.tipoTrabajos == null) {
            this.tipoTrabajos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.tipoTrabajoService.insert(this.nuevo);
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
            Boolean exito = this.tipoTrabajoService.update(this.selected);
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
            Boolean exito = this.tipoTrabajoService.deteleFlag(this.selected);
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

    public TipoTrabajo getNuevo() {
        return nuevo;
    }

    public void setNuevo(TipoTrabajo nuevo) {
        this.nuevo = nuevo;
    }

    public TipoTrabajo getSelected() {
        return selected;
    }

    public void setSelected(TipoTrabajo selected) {
        this.selected = selected;
    }

    public List<TipoTrabajo> getTipoTrabajos() {
        return tipoTrabajos;
    }

    public void setTipoTrabajos(List<TipoTrabajo> tipoTrabajos) {
        this.tipoTrabajos = tipoTrabajos;
    }
    
}
