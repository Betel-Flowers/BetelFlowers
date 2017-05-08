/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.SubCliente;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class SubClient implements Serializable {

    private static final long serialVersionUID = 4932414605677507000L;
    //Agregar y Eliminar SubClient
    private SubCliente nuevo;
    private SubCliente selected;
    private List<SubCliente> subClientes;

    public SubClient() {
        this.nuevo = new SubCliente();
        this.selected = null;
        if (this.subClientes == null) {
            this.subClientes = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && this.subClientes != null) {
            Boolean exito = this.subClientes.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
                this.nuevo = new SubCliente();
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, SubCliente select) {
        this.selected = select;
        if (this.selected != null
                && this.subClientes != null
                && !this.subClientes.isEmpty()) {
            Boolean exito = this.subClientes.remove(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }

    public SubCliente getNuevo() {
        return nuevo;
    }

    public void setNuevo(SubCliente nuevo) {
        this.nuevo = nuevo;
    }

    public SubCliente getSelected() {
        return selected;
    }

    public void setSelected(SubCliente selected) {
        this.selected = selected;
    }

    public List<SubCliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<SubCliente> subClientes) {
        this.subClientes = subClientes;
    }

}
