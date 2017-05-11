/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.CuartoFrio;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.CuartoFrioService;
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
@Named(value = "bodegaBean")
@ViewScoped
public class BodegaBean implements Serializable {

    private static final long serialVersionUID = -1259098435773217619L;

    private BodegaVirtual nuevo;
    private BodegaVirtual selected;
    private List<BodegaVirtual> bodegas;

    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private CuartoFrioService cuartoFrioService;

    @PostConstruct
    public void init() {
        this.nuevo = new BodegaVirtual();
        this.nuevo.setUsername("usertest");//usuertest
        this.selected = null;
        this.bodegas = this.bodegaService.obtenerListFlag(1);
        if (this.bodegas == null) {
            this.bodegas = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        CuartoFrio cuartoFrio = this.cuartoFrioService.findByCodigo(this.nuevo.getCuartoFrio());
        this.nuevo.setCuartoFrio(cuartoFrio);
        Boolean exito = this.bodegaService.insert(this.nuevo);
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
            CuartoFrio cuartoFrio = this.cuartoFrioService.findByCodigo(this.selected.getCuartoFrio());
            this.selected.setCuartoFrio(cuartoFrio);
            Boolean exito = this.bodegaService.update(this.selected);
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
            Boolean exito = this.bodegaService.deteleFlag(this.selected);
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

    public BodegaVirtual getNuevo() {
        return nuevo;
    }

    public void setNuevo(BodegaVirtual nuevo) {
        this.nuevo = nuevo;
    }

    public BodegaVirtual getSelected() {
        return selected;
    }

    public void setSelected(BodegaVirtual selected) {
        this.selected = selected;
    }

    public List<BodegaVirtual> getBodegas() {
        return bodegas;
    }

    public void setBodegas(List<BodegaVirtual> bodegas) {
        this.bodegas = bodegas;
    }

}
