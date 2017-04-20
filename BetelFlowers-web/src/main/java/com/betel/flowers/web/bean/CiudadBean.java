/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.Pais;
import com.betel.flowers.service.CiudadService;
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
@Named(value = "ciudadBean")
@ViewScoped
public class CiudadBean implements Serializable {
    
    private static final long serialVersionUID = -7640574319405715516L;
    
    private Ciudad nuevo;
    private Ciudad selected;
    private List<Ciudad> ciudades;
    
    @Inject
    private CiudadService ciudadService;
    @Inject
    private PaisService paisService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new Ciudad();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.ciudades = this.ciudadService.obtenerListFlag(1);
        if (this.ciudades == null) {
            this.ciudades = new ArrayList<>();
        }
    }
    
    public void add(ActionEvent evt) {
        Pais mpais = this.paisService.findByCodigo(this.nuevo.getPais());
        this.nuevo.setPais(mpais);
        Boolean exito = this.ciudadService.insert(this.nuevo);
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
            Boolean exito = this.ciudadService.update(this.selected);
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
            Boolean exito = this.ciudadService.deteleFlag(this.selected);
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
    
    public Ciudad getNuevo() {
        return nuevo;
    }
    
    public void setNuevo(Ciudad nuevo) {
        this.nuevo = nuevo;
    }
    
    public Ciudad getSelected() {
        return selected;
    }
    
    public void setSelected(Ciudad selected) {
        this.selected = selected;
    }
    
    public List<Ciudad> getCiudades() {
        return ciudades;
    }
    
    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
}
