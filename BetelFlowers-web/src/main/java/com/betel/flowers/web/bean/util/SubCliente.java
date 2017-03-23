/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Cliente;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class SubCliente implements Serializable {

    private static final long serialVersionUID = 4932414605677507000L;
    //Agregar y Eliminar SubCliente
    private Cliente nuevo;
    private Cliente selected;
    private List<Cliente> subClientes;

    public SubCliente() {
        this.nuevo = new Cliente();
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
                this.nuevo = new Cliente();
                this.selected = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void remove(ActionEvent evt, Cliente select) {
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

    public Cliente getNuevo() {
        return nuevo;
    }

    public void setNuevo(Cliente nuevo) {
        this.nuevo = nuevo;
    }

    public Cliente getSelected() {
        return selected;
    }

    public void setSelected(Cliente selected) {
        this.selected = selected;
    }

    public List<Cliente> getSubClientes() {
        return subClientes;
    }

    public void setSubClientes(List<Cliente> subClientes) {
        this.subClientes = subClientes;
    }

}
