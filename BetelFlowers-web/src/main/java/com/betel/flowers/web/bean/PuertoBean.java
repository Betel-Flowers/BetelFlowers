/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.Pais;
import com.betel.flowers.model.Puerto;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.PuertoService;
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
@Named(value = "puertoBean")
@ViewScoped
public class PuertoBean implements Serializable {

    private static final long serialVersionUID = -3769501746002427676L;

    private Puerto nuevo;
    private Puerto selected;
    private List<Puerto> puertos;
    private List<Ciudad> ciudades;

    @Inject
    private PuertoService puertoService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;

    @PostConstruct
    public void init() {
        this.nuevo = new Puerto();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.puertos = this.puertoService.obtenerListFlag(1);
        if (this.puertos == null) {
            this.puertos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Pais mpais = this.paisService.findByCodigo(this.nuevo.getCiudad().getPais());
        Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevo.getCiudad());
        this.nuevo.setCiudad(mciudad);
        this.nuevo.getCiudad().setPais(mpais);
        Boolean exito = this.puertoService.insert(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null && this.nuevo != null) {
            Pais mpais = this.paisService.findByCodigo(this.nuevo.getCiudad().getPais());
            Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevo.getCiudad());
            this.selected.setCiudad(mciudad);
            this.selected.getCiudad().setPais(mpais);
            Boolean exito = this.puertoService.update(this.selected);
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
            Boolean exito = this.puertoService.deteleFlag(this.selected);
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

    public void changePais() {
        if (this.nuevo.getCiudad().getPais().getCodigo() != null) {
            this.ciudades = new ArrayList<>();
            Pais mpais = this.paisService.findByCodigo(this.nuevo.getCiudad().getPais());
            this.ciudades = this.ciudadService.obtenerListPais(mpais);
        }
    }

    public Puerto getNuevo() {
        return nuevo;
    }

    public void setNuevo(Puerto nuevo) {
        this.nuevo = nuevo;
    }

    public Puerto getSelected() {
        return selected;
    }

    public void setSelected(Puerto selected) {
        this.selected = selected;
    }

    public List<Puerto> getPuertos() {
        return puertos;
    }

    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
}
