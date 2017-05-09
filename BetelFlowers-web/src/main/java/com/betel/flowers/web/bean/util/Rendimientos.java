/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Rendimiento;
import com.betel.flowers.model.TipoTrabajo;
import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.TipoTrabajoService;
import com.betel.flowers.service.UsuarioService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
public class Rendimientos implements Serializable {

    private static final long serialVersionUID = 7965997500154772649L;

    private Rendimiento nuevo;
    private Rendimiento selected;
    private List<Rendimiento> rendimientos;

    @Inject
    private TipoTrabajoService tipoTrbajoService;
    @Inject
    private UsuarioService usuarioService;

    public Rendimientos() {
        this.tipoTrbajoService = new TipoTrabajoService();
        this.usuarioService = new UsuarioService();
        this.nuevo = new Rendimiento();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        if (this.rendimientos == null) {
            this.rendimientos = new ArrayList<>();
        }
    }

    public Boolean add() {
        Boolean exito = Boolean.FALSE;
        if (this.nuevo != null && this.rendimientos != null) {
            TipoTrabajo tp = this.tipoTrbajoService.findByCodigo(this.nuevo.getTipoTrabajo());
            Usuario op = this.usuarioService.findByCodigo(this.nuevo.getOperario());
            this.nuevo.setTipoTrabajo(tp);
            this.nuevo.setOperario(op);
            exito = this.rendimientos.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new Rendimiento();
                this.nuevo.setUsername("usertest");
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
        return exito;
    }

    public void remove(ActionEvent evt, Rendimiento select) {
        this.selected = select;
        if (this.selected != null
                && this.rendimientos != null
                && !this.rendimientos.isEmpty()) {
            Boolean exito = this.rendimientos.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public Rendimiento getNuevo() {
        return nuevo;
    }

    public void setNuevo(Rendimiento nuevo) {
        this.nuevo = nuevo;
    }

    public Rendimiento getSelected() {
        return selected;
    }

    public void setSelected(Rendimiento selected) {
        this.selected = selected;
    }

    public List<Rendimiento> getRendimientos() {
        return rendimientos;
    }

    public void setRendimientos(List<Rendimiento> rendimientos) {
        this.rendimientos = rendimientos;
    }

}
