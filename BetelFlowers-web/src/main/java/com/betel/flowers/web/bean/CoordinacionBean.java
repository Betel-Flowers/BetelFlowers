/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.RegistroVenta;
import com.betel.flowers.service.RegistroVentaService;
import java.io.Serializable;
import java.util.Collections;
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
@Named(value = "coordinacionBean")
@ViewScoped
public class CoordinacionBean implements Serializable{

    private static final long serialVersionUID = 7497232288272047093L;

    private List<RegistroVenta> registros;
    private List<RegistroVenta> filtered;
    private RegistroVenta selected;
    @Inject
    private RegistroVentaService ventaService; 
    
    @PostConstruct
    public void init(){
        this.registros = this.ventaService.obtenerListFlag(1);
        Collections.reverse(this.registros);
    }
    
    public void updateLitsVentas(ActionEvent evt){
        this.registros = this.ventaService.obtenerListFlag(1);
    }

    public List<RegistroVenta> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroVenta> registros) {
        this.registros = registros;
    }

    public RegistroVenta getSelected() {
        return selected;
    }

    public void setSelected(RegistroVenta selected) {
        this.selected = selected;
    }

    public List<RegistroVenta> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<RegistroVenta> filtered) {
        this.filtered = filtered;
    }
    
    
}
