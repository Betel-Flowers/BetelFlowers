/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Telefono;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class Telefonos implements Serializable {

    private static final long serialVersionUID = 4290198621965362901L;
    //agregar y eliminar
    private Telefono nuevo;
    private Telefono selected;
    private List<Telefono> telefonos;

    public Telefonos() {
        this.nuevo = new Telefono();
        this.selected = null;
        if (this.telefonos == null) {
            this.telefonos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.telefonos != null) {
            int index = this.telefonos.size();
            this.nuevo.setIndex(index);
            Boolean exito = this.telefonos.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new Telefono();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, Telefono select) {
        this.selected = select;
        if (this.selected != null
                && this.telefonos != null
                && !this.telefonos.isEmpty()) {
            Boolean exito = this.telefonos.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public Telefono getNuevo() {
        return nuevo;
    }

    public void setNuevo(Telefono nuevo) {
        this.nuevo = nuevo;
    }

    public Telefono getSelected() {
        return selected;
    }

    public void setSelected(Telefono selected) {
        this.selected = selected;
    }

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

}
