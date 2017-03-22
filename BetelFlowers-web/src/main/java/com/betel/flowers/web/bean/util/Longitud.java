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
public class Longitud extends StateBeanUtil implements Serializable {

    private static final long serialVersionUID = 8152746804551209002L;

    private Variedad variedad;
    private Integer nuevo;
    private Integer selected;
    private List<Integer> longitudes;

    private VariedadService variedadService;

    public Longitud(Variedad variedad) {
        this.variedad = variedad;
        this.nuevo = new Integer(40);
        this.longitudes = this.variedad.getLongitudes();
        this.variedadService = new VariedadService();
        this.addState();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != 0) {
            this.longitudes.add(this.nuevo);
            this.variedad.setLongitudes(this.longitudes);
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
            int index = this.longitudes.indexOf(this.selected);
            this.longitudes.remove(index);
            this.longitudes.add(index,this.nuevo);
            this.variedad.setLongitudes(this.longitudes);
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
            this.longitudes.remove(this.selected);
            this.variedad.setLongitudes(this.longitudes);
            Boolean exito = this.variedadService.updateList(this.variedad);
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
        this.nuevo = 40;
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

    public List<Integer> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<Integer> longitudes) {
        this.longitudes = longitudes;
    }
}
