/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Causa;
import com.betel.flowers.service.CausaService;
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
@Named(value = "causaBean")
@ViewScoped
public class CausaBean implements Serializable {

    private static final long serialVersionUID = -1764935876352186475L;

    private Causa nuevo;
    private Causa selected;
    private List<Causa> causas;

    @Inject
    private CausaService causaService;

    @PostConstruct
    public void init() {
        this.nuevo = new Causa();
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

    public Causa getNuevo() {
        return nuevo;
    }

    public void setNuevo(Causa nuevo) {
        this.nuevo = nuevo;
    }

    public Causa getSelected() {
        return selected;
    }

    public void setSelected(Causa selected) {
        this.selected = selected;
    }

    public List<Causa> getCausas() {
        return causas;
    }

    public void setCausas(List<Causa> causas) {
        this.causas = causas;
    }
}
