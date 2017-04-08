/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.OpcionSistema;
import com.betel.flowers.model.TipoUsuario;
import com.betel.flowers.service.TipoUsuarioService;
import com.betel.flowers.web.util.FacesUtil;
import com.betel.flowers.web.util.StateBeanUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class OpcionesSistema extends StateBeanUtil implements Serializable {

    private static final long serialVersionUID = -3825233192250404616L;

    private TipoUsuario tipoUsuario;
    private OpcionSistema nuevo;
    private OpcionSistema selected;
    private List<OpcionSistema> opcionesSistema;

    private TipoUsuarioService tipoUsuarioService;

    public OpcionesSistema(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
        this.nuevo = new OpcionSistema();
        this.opcionesSistema = this.tipoUsuario.getOpcionesSistema();
        this.tipoUsuarioService = new TipoUsuarioService();
        this.addState();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null) {
            this.opcionesSistema.add(this.nuevo);
            this.tipoUsuario.setOpcionesSistema(this.opcionesSistema);
            Boolean exito = this.tipoUsuarioService.add(this.tipoUsuario);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null && this.nuevo != null) {
            int index = this.opcionesSistema.indexOf(this.selected);
            this.opcionesSistema.remove(index);
            this.opcionesSistema.add(index, this.nuevo);
            this.tipoUsuario.setOpcionesSistema(this.opcionesSistema);
            Boolean exito = this.tipoUsuarioService.updateList(this.tipoUsuario);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modificado.");
                this.addState();
            } else {
                FacesUtil.addMessageError(null, "No se ha modificado.");
            }
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            this.opcionesSistema.remove(this.selected);
            this.tipoUsuario.setOpcionesSistema(this.opcionesSistema);
            Boolean exito = this.tipoUsuarioService.updateList(this.tipoUsuario);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.addState();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public void close(ActionEvent evt) {
        this.addState();
        this.nuevo = new OpcionSistema();
    }

    public OpcionSistema getNuevo() {
        return nuevo;
    }

    public void setNuevo(OpcionSistema nuevo) {
        this.nuevo = nuevo;
    }

    public OpcionSistema getSelected() {
        return selected;
    }

    public void setSelected(OpcionSistema selected) {
        this.selected = selected;
    }

    public List<OpcionSistema> getOpcionesSistema() {
        return opcionesSistema;
    }

    public void setOpcionesSistema(List<OpcionSistema> opcionesSistema) {
        this.opcionesSistema = opcionesSistema;
    }

}
