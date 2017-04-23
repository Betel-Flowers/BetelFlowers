/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaCarguera;
import com.betel.flowers.model.Carguera;
import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.CuartoFrioCarguera;
import com.betel.flowers.model.Pais;
import com.betel.flowers.service.BodegaCargueraService;
import com.betel.flowers.service.CargueraService;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.CuartoFrioCargueraService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.web.bean.util.Correos;
import com.betel.flowers.web.bean.util.Telefonos;
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
    private Telefonos telefono;
    private Correos correo;
    private List<Ciudad> ciudades;
    private List<CuartoFrioCarguera> cuartosFrio;

    @Inject
    private CargueraService cargueraService;
    @Inject
    private BodegaCargueraService bodegaService;
    @Inject
    private CuartoFrioCargueraService cuartoFrioService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;

    @PostConstruct
    public void init() {
        this.nuevo = new Carguera();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.telefono = new Telefonos();
        this.correo = new Correos();
        this.ciudades = new ArrayList<>();
        this.cuartosFrio = new ArrayList<>();
        this.cargueras = this.cargueraService.obtenerListFlag(1);
        if (this.cargueras == null) {
            this.cargueras = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
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

    public void changePais() {
        if (this.nuevo.getCiudad().getPais().getCodigo() != null) {
            this.ciudades = new ArrayList<>();
            Pais mpais = this.paisService.findByCodigo(this.nuevo.getCiudad().getPais());
            this.setCiudades(this.ciudadService.obtenerListPais(mpais));
        }
    }

    public void changeBodega() {
        if (this.nuevo.getCuartoFrio().getBodega().getCodigo() != null) {
            this.cuartosFrio = new ArrayList<>();
            BodegaCarguera mbodega = this.bodegaService.findByCodigo(this.nuevo.getCuartoFrio().getBodega());
            this.setCuartosFrio(this.cuartoFrioService.obtenerListBodega(mbodega));
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

    public Telefonos getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefonos telefono) {
        this.telefono = telefono;
    }

    public Correos getCorreo() {
        return correo;
    }

    public void setCorreo(Correos correo) {
        this.correo = correo;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public List<CuartoFrioCarguera> getCuartosFrio() {
        return cuartosFrio;
    }

    public void setCuartosFrio(List<CuartoFrioCarguera> cuartosFrio) {
        this.cuartosFrio = cuartosFrio;
    }

}
