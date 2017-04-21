/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.Cliente;
import com.betel.flowers.model.Pais;
import com.betel.flowers.model.ZonaGeografica;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.ClienteService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.ZonaGeograficaService;
import com.betel.flowers.web.bean.util.Cajas;
import com.betel.flowers.web.bean.util.Correos;
import com.betel.flowers.web.bean.util.SubCliente;
import com.betel.flowers.web.bean.util.Telefonos;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
@Named(value = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

    private static final long serialVersionUID = 1862686713183898998L;

    private Cliente nuevo;
    private Cliente nuevoSelected;
    private Cliente selected;
    private Cliente removeSelected;
    private List<Cliente> clientes;
    private List<Cliente> subClientes;
    private List<Ciudad> ciudades;
    private Telefonos telefono;
    private Correos correo;
    private Cajas caja;
    private SubCliente subCliente;
    private Boolean activeSelectedCliente;

    @Inject
    private ClienteService clienteService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private ZonaGeograficaService zonaGeograficaService;

    @PostConstruct
    public void init() {
        this.nuevo = new Cliente();
        this.nuevo.setUsername("usertest"); //usertest
        this.nuevoSelected = new Cliente();
        this.selected = null;
        this.ciudades = new ArrayList<>();
        this.telefono = new Telefonos();
        this.correo = new Correos();
        this.caja = new Cajas();
        this.subCliente = new SubCliente();
        this.activeSelectedCliente = Boolean.TRUE;
        this.clientes = this.clienteService.obtenerListFlag(1);
        this.subClientes = this.clienteService.obtenerListFlag(1);
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
            this.subClientes = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (!this.telefono.getTelefonos().isEmpty()) {
            if (!this.correo.getCorreos().isEmpty()) {
                this.nuevo.setTelefonos(this.telefono.getTelefonos());
                this.nuevo.setCorreos(this.correo.getCorreos());
                this.nuevo.setCajas(this.caja.getCajas());
                Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevo.getCiudad());
                ZonaGeografica mzona = this.zonaGeograficaService.findByCodigo(this.nuevo.getZona());
                this.nuevo.setCiudad(mciudad);
                this.nuevo.setZona(mzona);
                Boolean exito = this.clienteService.insert(this.nuevo);
                if (exito) {
                    FacesUtil.addMessageInfo("Se ha guardado con exito.");
                    this.removeDuplicateSubClientes();
                    this.nuevo.setSubClientes(this.subCliente.getSubClientes());
                    this.clienteService.addSubCliente(this.nuevo);
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
                    this.selected.setCajas(this.caja.getCajas());
                    Ciudad mciudad = this.ciudadService.findByCodigo(this.nuevoSelected.getCiudad());
                    this.selected.setCiudad(mciudad);
                    this.removeDuplicateSubClientes();
                    this.selected.setSubClientes(this.subCliente.getSubClientes());
                    Boolean exito = this.clienteService.update(this.selected);
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
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.clienteService.deteleFlag(this.selected);
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

    private void removeDuplicateSubClientes() {
        if (this.subCliente.getSubClientes() != null
                && !this.subCliente.getSubClientes().isEmpty()) {
            HashSet hs = new HashSet();
            hs.addAll(this.subCliente.getSubClientes());
            this.subCliente.getSubClientes().clear();
            this.subCliente.getSubClientes().addAll(hs);
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Cliente) event.getObject();
        if (this.selected != null) {
            this.setNuevoSelected(this.selected);
            this.setRemoveSelected(this.selected);
            changePaisSelected();
            this.subCliente.setNuevo(this.selected);
            this.telefono.setTelefonos(this.selected.getTelefonos());
            this.correo.setCorreos(this.selected.getCorreos());
            this.caja.setCajas(this.selected.getCajas());
            this.subCliente.setSubClientes(this.selected.getSubClientes());
            this.activeSelectedCliente = Boolean.FALSE;
            this.removeEqualCliente();

        }
    }

    private void removeEqualCliente() {
        if (this.subClientes != null && !this.subClientes.isEmpty()) {
            this.subClientes.remove(this.getRemoveSelected());
        }
    }

    public void onRowSelectSubCliente(SelectEvent event) {
        this.subCliente.setSelected((Cliente) event.getObject());
        if (this.subCliente.getSelected() != null) {
            this.subCliente.setNuevo(this.subCliente.getSelected());
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

    public void onlyComerzializadora() {
        this.nuevo.setLocal(Boolean.FALSE);
        this.nuevo.setExterior(Boolean.FALSE);
    }

    public void onlyExterior() {
        this.nuevo.setComercializadora(Boolean.FALSE);
        this.nuevo.setLocal(Boolean.FALSE);
    }

    public void onlyLocal() {
        this.nuevo.setComercializadora(Boolean.FALSE);
        this.nuevo.setExterior(Boolean.FALSE);
    }

    public void onlySelectComerzializadora() {
        this.selected.setLocal(Boolean.FALSE);
        this.selected.setExterior(Boolean.FALSE);
    }

    public void onlySelectExterior() {
        this.selected.setComercializadora(Boolean.FALSE);
        this.selected.setLocal(Boolean.FALSE);
    }

    public void onlySelectLocal() {
        this.selected.setComercializadora(Boolean.FALSE);
        this.selected.setExterior(Boolean.FALSE);
    }

    public Cliente getNuevo() {
        return nuevo;
    }

    public void setNuevo(Cliente nuevo) {
        this.nuevo = nuevo;
    }

    public Cliente getNuevoSelected() {
        return nuevoSelected;
    }

    public void setNuevoSelected(Cliente nuevoSelected) {
        this.nuevoSelected = nuevoSelected;
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<Cliente> subClientes) {
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

    public Cajas getCaja() {
        return caja;
    }

    public void setCorreo(Correos correo) {
        this.correo = correo;
    }

    public void setCaja(Cajas caja) {
        this.caja = caja;
    }

    public SubCliente getSubCliente() {
        return subCliente;
    }

    public void setSubCliente(SubCliente subCliente) {
        this.subCliente = subCliente;
    }

    public Boolean getActiveSelectedCliente() {
        return activeSelectedCliente;
    }

    public void setActiveSelectedCliente(Boolean activeSelectedCliente) {
        this.activeSelectedCliente = activeSelectedCliente;
    }

    public Cliente getRemoveSelected() {
        return removeSelected;
    }

    public void setRemoveSelected(Cliente removeSelected) {
        this.removeSelected = removeSelected;
    }

}
