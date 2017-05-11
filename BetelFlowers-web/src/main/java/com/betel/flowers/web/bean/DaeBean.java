/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Dae;
import com.betel.flowers.model.Pais;
import com.betel.flowers.service.DaeService;
import com.betel.flowers.service.PaisService;
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
@Named(value = "daeBean")
@ViewScoped
public class DaeBean implements Serializable {

    private static final long serialVersionUID = -3009525467369921956L;

    private Dae nuevo;
    private Dae selected;
    private List<Dae> daes;

    @Inject
    private DaeService daeService;
    @Inject
    private PaisService paisService;

    @PostConstruct
    public void init() {
        this.nuevo = new Dae();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.daes = this.daeService.obtenerListFlag(1);
        if (this.daes == null) {
            this.daes = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Pais mpais = this.paisService.findByCodigo(this.nuevo.getPais());
        this.nuevo.setPais(mpais);
        Boolean exito = this.daeService.insert(this.nuevo);
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
            Pais mpais = this.paisService.findByCodigo(this.nuevo.getPais());
            this.selected.setPais(mpais);
            Boolean exito = this.daeService.update(this.selected);
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
            Boolean exito = this.daeService.deteleFlag(this.selected);
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

    public Dae getNuevo() {
        return nuevo;
    }

    public void setNuevo(Dae nuevo) {
        this.nuevo = nuevo;
    }

    public Dae getSelected() {
        return selected;
    }

    public void setSelected(Dae selected) {
        this.selected = selected;
    }

    public List<Dae> getDaes() {
        return daes;
    }

    public void setDaes(List<Dae> daes) {
        this.daes = daes;
    }

}
