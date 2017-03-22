/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.util.FacesUtil;
import com.betel.flowers.web.util.StateBeanUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.event.ActionEvent;

/**
 *
 * @author luis
 */
public class Ramo extends StateBeanUtil implements Serializable {

    private static final long serialVersionUID = 2093833497109987317L;

    private Variedad variedad;
    private Integer nuevo;
    private Integer selected;
    private List<Integer> ramos;

    private VariedadService variedadService;

    public Ramo(Variedad variedad) {
        this.variedad = variedad;
        this.nuevo = new Integer(1);
        this.ramos = this.variedad.getRamos();
        this.variedadService = new VariedadService();
        this.addState();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != 0) {
            this.ramos.add(this.nuevo);
            this.variedad.setRamos(this.ramos);
            Boolean exito = this.variedadService.add(this.variedad);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != 0 && this.nuevo != 0) {
            int index = this.ramos.indexOf(this.selected);
            this.ramos.remove(index);
            this.ramos.add(index, this.nuevo);
            this.variedad.setRamos(this.ramos);
            Boolean exito = this.variedadService.updateList(this.variedad);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modificado.");
                this.addState();
            } else {
                FacesUtil.addMessageError(null, "No se ha modificado.");
            }
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != 0) {
            this.ramos.remove(this.selected);
            this.variedad.setRamos(this.ramos);
            Boolean exito = this.variedadService.updateList(this.variedad);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.modifyState();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }
    
    public void close(ActionEvent evt) {
        this.addState();
        this.nuevo = 1;
    }

    public Integer getNuevo() {
        return nuevo;
    }

    public void setNuevo(Integer nuevo) {
        this.nuevo = nuevo;
    }

    public Integer getSelected() {
        return selected;
    }

    public void setSelected(Integer selected) {
        this.selected = selected;
    }

    public List<Integer> getRamos() {
        return ramos;
    }

    public void setRamos(List<Integer> ramos) {
        this.ramos = ramos;
    }
}
