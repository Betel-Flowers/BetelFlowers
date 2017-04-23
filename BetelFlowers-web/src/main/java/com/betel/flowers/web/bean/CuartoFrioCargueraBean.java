/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaCarguera;
import com.betel.flowers.model.CuartoFrioCarguera;
import com.betel.flowers.service.BodegaCargueraService;
import com.betel.flowers.service.CuartoFrioCargueraService;
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
@Named(value = "cuartoFrioCargueraBean")
@ViewScoped
public class CuartoFrioCargueraBean implements Serializable {

    private static final long serialVersionUID = 3435491625700430271L;

    private CuartoFrioCarguera nuevo;
    private CuartoFrioCarguera selected;
    private List<CuartoFrioCarguera> cuartosFrio;

    @Inject
    private CuartoFrioCargueraService cuartoService;
    @Inject
    private BodegaCargueraService bodegaCargueraService;

    @PostConstruct
    public void init() {
        this.nuevo = new CuartoFrioCarguera();
        this.nuevo.setUsername("usertest");//usertest
        this.selected = null;
        this.cuartosFrio = this.cuartoService.obtenerListFlag(1);
        if (this.cuartosFrio == null) {
            this.cuartosFrio = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
         BodegaCarguera bodega = this.bodegaCargueraService.findByCodigo(this.nuevo.getBodega());
         this.nuevo.setBodega(bodega);
        Boolean exito = this.cuartoService.insert(this.nuevo);
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
            BodegaCarguera bodega = this.bodegaCargueraService.findByCodigo(this.nuevo.getBodega());
            this.selected.setBodega(bodega);
            Boolean exito = this.cuartoService.update(this.selected);
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
            Boolean exito = this.cuartoService.deteleFlag(this.selected);
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

    public CuartoFrioCarguera getNuevo() {
        return nuevo;
    }

    public void setNuevo(CuartoFrioCarguera nuevo) {
        this.nuevo = nuevo;
    }

    public CuartoFrioCarguera getSelected() {
        return selected;
    }

    public void setSelected(CuartoFrioCarguera selected) {
        this.selected = selected;
    }

    public List<CuartoFrioCarguera> getCuartosFrio() {
        return cuartosFrio;
    }

    public void setCuartosFrio(List<CuartoFrioCarguera> cuartosFrio) {
        this.cuartosFrio = cuartosFrio;
    }

}
