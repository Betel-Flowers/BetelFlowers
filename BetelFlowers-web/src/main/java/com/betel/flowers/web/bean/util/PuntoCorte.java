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
public class PuntoCorte extends StateBeanUtil implements Serializable {

    private static final long serialVersionUID = 1447864703470722735L;

    private Variedad variedad;
    private String nuevo;
    private String selected;
    private List<String> puntosCorte;

    private VariedadService variedadService;

    public PuntoCorte(Variedad variedad) {
        this.variedad = variedad;
        this.nuevo = "";
        this.puntosCorte = this.variedad.getPuntosCorte();
        this.variedadService = new VariedadService();
        this.addState();
    }

    public void add(ActionEvent evt) {
        if (this.nuevo != null && !this.nuevo.equals("")) {
            this.puntosCorte.add(this.nuevo);
            this.variedad.setPuntosCorte(this.puntosCorte);
            Boolean exito = this.variedadService.add(this.variedad);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha agregado.");
            } else {
                FacesUtil.addMessageError(null, "No se ha agregado.");
            }
        }
    }

    public void modify(ActionEvent evt) {
        if ((this.selected != null && !this.selected.equals("")) && (this.nuevo != null && !this.nuevo.equals(""))) {
            int index = this.puntosCorte.indexOf(this.selected);
            this.puntosCorte.remove(index);
            this.puntosCorte.add(index,this.nuevo);
            this.variedad.setPuntosCorte(this.puntosCorte);
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
        if (this.selected != null && !this.selected.equals("")) {
            this.puntosCorte.remove(this.selected);
            this.variedad.setPuntosCorte(this.puntosCorte);
            Boolean exito = this.variedadService.updateList(this.variedad);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado.");
                this.addState();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado.");
            }
        }
    }
    
    public void close(ActionEvent evt){
        this.addState();
        this.nuevo = "";
    }

    public String getNuevo() {
        return nuevo;
    }

    public void setNuevo(String nuevo) {
        this.nuevo = nuevo;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public List<String> getPuntosCorte() {
        return puntosCorte;
    }

    public void setPuntosCorte(List<String> puntosCorte) {
        this.puntosCorte = puntosCorte;
    }
}
