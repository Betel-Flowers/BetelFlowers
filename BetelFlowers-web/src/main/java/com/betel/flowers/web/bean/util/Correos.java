/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Correo;
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
public class Correos implements Serializable {

    private static final long serialVersionUID = -6770360630441000549L;
    //agregar y eliminar
    private Correo nuevo;
    private Correo selected;
    private List<Correo> correos;

    public Correos() {
        this.nuevo = new Correo();
        this.selected = null;
        if (this.correos == null) {
            this.correos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.correos != null) {
            int index = this.correos.size();
            this.nuevo.setIndex(index);
            Boolean exito = this.correos.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new Correo();
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, Correo select) {
        this.selected = select;
        if (this.selected != null
                && this.correos != null
                && !this.correos.isEmpty()) {
            Boolean exito = this.correos.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public Correo getNuevo() {
        return nuevo;
    }

    public void setNuevo(Correo nuevo) {
        this.nuevo = nuevo;
    }

    public Correo getSelected() {
        return selected;
    }

    public void setSelected(Correo selected) {
        this.selected = selected;
    }

    public List<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(List<Correo> correos) {
        this.correos = correos;
    }

}
