/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.CausaEmpaque;
import com.betel.flowers.service.CausaEmpaqueService;
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
@Named(value = "causaEmpaqueBean")
@ViewScoped
public class CausaEmpaqueBean implements Serializable {

    private static final long serialVersionUID = -4681432932216523925L;

    private CausaEmpaque nuevo;
    private CausaEmpaque selected;
    private List<CausaEmpaque> causas;

    @Inject
    private CausaEmpaqueService causaService;

    @PostConstruct
    public void init() {
        this.nuevo = new CausaEmpaque();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.causas = this.causaService.obtenerListFlag(1);
        if (this.causas == null) {
            this.causas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.causaService.insert(this.nuevo);
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
            Boolean exito = this.causaService.update(this.selected);
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
            Boolean exito = this.causaService.deteleFlag(this.selected);
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

    public CausaEmpaque getNuevo() {
        return nuevo;
    }

    public void setNuevo(CausaEmpaque nuevo) {
        this.nuevo = nuevo;
    }

    public CausaEmpaque getSelected() {
        return selected;
    }

    public void setSelected(CausaEmpaque selected) {
        this.selected = selected;
    }

    public List<CausaEmpaque> getCausas() {
        return causas;
    }

    public void setCausas(List<CausaEmpaque> causas) {
        this.causas = causas;
    }

}
