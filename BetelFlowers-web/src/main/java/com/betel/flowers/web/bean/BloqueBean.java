/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Bloque;
import com.betel.flowers.service.BloqueService;
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
@Named(value = "bloqueBean")
@ViewScoped
public class BloqueBean implements Serializable {

    private static final long serialVersionUID = 5805188781707188507L;

    private Bloque nuevo;
    private Bloque selected;
    private List<Bloque> bloques;

    @Inject
    private BloqueService bloqueService;

    @PostConstruct
    public void init() {
        this.nuevo = new Bloque();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.bloques = this.bloqueService.obtenerListFlag(1);
        if (this.bloques == null) {
            this.bloques = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.bloqueService.insert(this.nuevo);
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
            Boolean exito = this.bloqueService.update(this.selected);
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
            Boolean exito = this.bloqueService.deteleFlag(this.selected);
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

    public Bloque getNuevo() {
        return nuevo;
    }

    public void setNuevo(Bloque nuevo) {
        this.nuevo = nuevo;
    }

    public Bloque getSelected() {
        return selected;
    }

    public void setSelected(Bloque selected) {
        this.selected = selected;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public BloqueService getBloqueService() {
        return bloqueService;
    }

    public void setBloqueService(BloqueService bloqueService) {
        this.bloqueService = bloqueService;
    }
}
