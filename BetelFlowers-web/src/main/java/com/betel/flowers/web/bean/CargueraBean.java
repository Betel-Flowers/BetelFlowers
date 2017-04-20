/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.Carguera;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.CargueraService;
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
@Named(value = "cargueraBean")
@ViewScoped
public class CargueraBean implements Serializable {

    private static final long serialVersionUID = 8503479427821744033L;

    private Carguera nuevo;
    private Carguera selected;
    private List<Carguera> cargueras;

    @Inject
    private CargueraService cargueraService;
    @Inject
    private BodegaVirtualService bodegaService;

    @PostConstruct
    public void init() {
        this.nuevo = new Carguera();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.cargueras = this.cargueraService.obtenerListFlag(1);
        if (this.cargueras == null) {
            this.cargueras = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        BodegaVirtual mbodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setBodega(mbodega);
        Boolean exito = this.cargueraService.insert(this.nuevo);
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
            BodegaVirtual mbodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
            this.selected.setBodega(mbodega);
            Boolean exito = this.cargueraService.update(this.selected);
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
            Boolean exito = this.cargueraService.deteleFlag(this.selected);
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
    
    public Carguera getNuevo() {
        return nuevo;
    }

    public void setNuevo(Carguera nuevo) {
        this.nuevo = nuevo;
    }

    public Carguera getSelected() {
        return selected;
    }

    public void setSelected(Carguera selected) {
        this.selected = selected;
    }

    public List<Carguera> getCargueras() {
        return cargueras;
    }

    public void setCargueras(List<Carguera> cargueras) {
        this.cargueras = cargueras;
    }

}
