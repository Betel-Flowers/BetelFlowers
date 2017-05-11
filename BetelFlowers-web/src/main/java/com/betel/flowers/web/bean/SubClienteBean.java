/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.Pais;
import com.betel.flowers.model.SubCliente;
import com.betel.flowers.model.ZonaGeografica;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.SubClienteService;
import com.betel.flowers.service.ZonaGeograficaService;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "subClienteBean")
@ViewScoped
public class SubClienteBean implements Serializable {

    private static final long serialVersionUID = 6734346914715904301L;

    private SubCliente nuevo;
    private SubCliente nuevoSelected;
    private SubCliente selected;
    private SubCliente removeSelected;
    private List<SubCliente> subClientes;
    private List<Ciudad> ciudades;
    private Telefonos telefono;
    private Correos correo;
    private Boolean activeSelectedSubCliente;

    @Inject
    private SubClienteService subClienteService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private ZonaGeograficaService zonaGeograficaService;

    @PostConstruct
    public void init() {
        this.nuevo = new SubCliente();
        this.nuevo.setUsername("usertest"); //usertest
        this.nuevoSelected = new SubCliente();
        this.selected = null;
        this.ciudades = new ArrayList<>();
        this.telefono = new Telefonos();
        this.correo = new Correos();
        this.activeSelectedSubCliente = Boolean.TRUE;
        this.subClientes = this.subClienteService.obtenerListFlag(1);
        if (this.subClientes == null) {
            this.subClientes = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (!this.telefono.getTelefonos().isEmpty()) {
            if (!this.correo.getCorreos().isEmpty()) {
                this.nuevo.setTelefonos(this.telefono.getTelefonos());
                this.nuevo.setCorreos(this.correo.getCorreos());
                Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevo.getCiudad());
                ZonaGeografica mzona = this.zonaGeograficaService.findByCodigo(this.nuevo.getZona());
                this.nuevo.setCiudad(mciudad);
                this.nuevo.setZona(mzona);
                Boolean exito = this.subClienteService.insert(this.nuevo);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha guardado con exito.");
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "No se ha guardado.");
                    this.init();
                }
            } else {
                FacesUtil.addMessageError(null, "Ingrese un correo.");
            }
        } else {
            FacesUtil.addMessageError(null, "Ingrese un telefono.");
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            if (!this.telefono.getTelefonos().isEmpty()) {
                if (!this.correo.getCorreos().isEmpty()) {
                    this.selected.setTelefonos(this.telefono.getTelefonos());
                    this.selected.setCorreos(this.correo.getCorreos());
                    Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevoSelected.getCiudad());
                    this.selected.setCiudad(mciudad);
                    Boolean exito = this.subClienteService.update(this.selected);
                    if (exito) {
                        FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                        this.init();
                    } else {
                        FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                        this.init();
                    }
                } else {
                    FacesUtil.addMessageError(null, "Ingrese un correo.");
                }
            } else {
                FacesUtil.addMessageError(null, "Ingrese un telefono.");
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (SubCliente) event.getObject();
        if (this.selected != null) {
            this.setNuevoSelected(this.selected);
            this.setRemoveSelected(this.selected);
            changePaisSelected();
            this.telefono.setTelefonos(this.selected.getTelefonos());
            this.correo.setCorreos(this.selected.getCorreos());
            this.activeSelectedSubCliente = Boolean.FALSE;
        }
    }

    public void changePais() {
        if (this.nuevo.getCiudad().getPais().getCodigo() != null) {
            this.ciudades = new ArrayList<>();
            Pais mpais = this.paisService.findByCodigo(this.nuevo.getCiudad().getPais());
            this.ciudades = this.ciudadService.obtenerListPais(mpais);
        }
    }

    public void changePaisSelected() {
        if (this.nuevoSelected.getCiudad().getPais().getCodigo() != null) {
            this.ciudades = new ArrayList<>();
            Pais mpais = this.paisService.findByCodigo(this.nuevoSelected.getCiudad().getPais());
            this.ciudades = this.ciudadService.obtenerListPais(mpais);
        }
    }

    public SubCliente getNuevo() {
        return nuevo;
    }

    public void setNuevo(SubCliente nuevo) {
        this.nuevo = nuevo;
    }

    public SubCliente getNuevoSelected() {
        return nuevoSelected;
    }

    public void setNuevoSelected(SubCliente nuevoSelected) {
        this.nuevoSelected = nuevoSelected;
    }

    public SubCliente getSelected() {
        return selected;
    }

    public void setSelected(SubCliente selected) {
        this.selected = selected;
    }

    public SubCliente getRemoveSelected() {
        return removeSelected;
    }

    public void setRemoveSelected(SubCliente removeSelected) {
        this.removeSelected = removeSelected;
    }

    public List<SubCliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<SubCliente> subClientes) {
        this.subClientes = subClientes;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<Ciudad> ciudades) {
        this.ciudades = ciudades;
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

    public Boolean getActiveSelectedSubCliente() {
        return activeSelectedSubCliente;
    }

    public void setActiveSelectedSubCliente(Boolean activeSelectedSubCliente) {
        this.activeSelectedSubCliente = activeSelectedSubCliente;
    }

}
