/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.model.TipoUsuario;
import com.betel.flowers.service.TipoUsuarioService;
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
@Named(value = "tipoUsuarioBean")
@ViewScoped
public class TipoUsuarioBean implements Serializable {

    private static final long serialVersionUID = -1803758222416450208L;

    private TipoUsuario nuevo;
    private TipoUsuario selected;
    private List<TipoUsuario> tipoUsuarios;
    private List<OpcionSistema> selectOpcionSistemas;

    @Inject
    private TipoUsuarioService tipoUsuarioService;

    @PostConstruct
    public void init() {
        this.nuevo = new TipoUsuario();
        this.nuevo.setUsername("usertest"); //usertest
        this.selected = null;
        this.selectOpcionSistemas = null;
        this.tipoUsuarios = this.tipoUsuarioService.obtenerListFlag(1);
        if (this.tipoUsuarios == null) {
            this.tipoUsuarios = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.selectOpcionSistemas != null && !this.selectOpcionSistemas.isEmpty() || this.nuevo.getAdmin()) {
            if (this.nuevo.getNombre() != null && !this.nuevo.getNombre().equals("")) {
                Boolean exito = this.tipoUsuarioService.insert(this.nuevo);
                if (exito) {
                    if (!this.nuevo.getAdmin()) {
                        this.nuevo.setOpcionesSistema(this.selectOpcionSistemas);
                        this.tipoUsuarioService.add(this.nuevo);
                    }
                    FacesUtil.addMessageInfo("Se ha guardado con exito.");
                    this.init();
                } else {
                    FacesUtil.addMessageError(null, "No se ha guardado.");
                    this.init();
                }
            } else {
                FacesUtil.addMessageInfo("Ingrese un nombre al tipo de usuario");
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione las opciones del sistema para el tipo de usuario a crear.");
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.tipoUsuarioService.update(this.selected);
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
            Boolean exito = this.tipoUsuarioService.deteleFlag(this.selected);
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

    public TipoUsuario getNuevo() {
        return nuevo;
    }

    public void setNuevo(TipoUsuario nuevo) {
        this.nuevo = nuevo;
    }

    public TipoUsuario getSelected() {
        return selected;
    }

    public void setSelected(TipoUsuario selected) {
        this.selected = selected;
    }

    public List<TipoUsuario> getTipoUsuarios() {
        return tipoUsuarios;
    }

    public void setTipoUsuarios(List<TipoUsuario> tipoUsuarios) {
        this.tipoUsuarios = tipoUsuarios;
    }

    public List<OpcionSistema> getSelectOpcionSistemas() {
        return selectOpcionSistemas;
    }

    public void setSelectOpcionSistemas(List<OpcionSistema> selectOpcionSistemas) {
        this.selectOpcionSistemas = selectOpcionSistemas;
    }

}
