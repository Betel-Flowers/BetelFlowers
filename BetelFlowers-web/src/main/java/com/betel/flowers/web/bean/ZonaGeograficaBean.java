/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.ZonaGeografica;
import com.betel.flowers.service.ZonaGeograficaService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
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
@Named(value = "zonaGeograficaBean")
@ViewScoped
public class ZonaGeograficaBean implements Serializable {

    private static final long serialVersionUID = 7622018069412573367L;

    private ZonaGeografica nuevo;
    private ZonaGeografica selected;
    private List<ZonaGeografica> zonasGeograficas;

    @Inject
    private ZonaGeograficaService zonaGeograficaService;

    @PostConstruct
    public void init() {
        this.nuevo = new ZonaGeografica();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.zonasGeograficas = this.zonaGeograficaService.obtenerListFlag(1);
    }

    public void add(ActionEvent evt) {
        Boolean exito = this.zonaGeograficaService.insert(this.nuevo);
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
            Boolean exito = this.zonaGeograficaService.update(this.selected);
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
            Boolean exito = this.zonaGeograficaService.deteleFlag(this.selected);
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

    public ZonaGeografica getNuevo() {
        return nuevo;
    }

    public void setNuevo(ZonaGeografica nuevo) {
        this.nuevo = nuevo;
    }

    public ZonaGeografica getSelected() {
        return selected;
    }

    public void setSelected(ZonaGeografica selected) {
        this.selected = selected;
    }

    public List<ZonaGeografica> getZonasGeograficas() {
        return zonasGeograficas;
    }

    public void setZonasGeograficas(List<ZonaGeografica> zonasGeograficas) {
        this.zonasGeograficas = zonasGeograficas;
    }

}
