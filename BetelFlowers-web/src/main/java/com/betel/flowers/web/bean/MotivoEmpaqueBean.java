/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Causa;
import com.betel.flowers.model.CausaEmpaque;
import com.betel.flowers.model.MotivoEmpaque;
import com.betel.flowers.service.CausaEmpaqueService;
import com.betel.flowers.service.MotivoEmpaqueService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "motivoEmpaqueBean")
@ViewScoped
public class MotivoEmpaqueBean implements Serializable{

    private static final long serialVersionUID = 4561806746252764576L;

    private MotivoEmpaque nuevo;
    private MotivoEmpaque selected;
    private List<MotivoEmpaque> motivos;

    @Inject
    private MotivoEmpaqueService motivoService;
    @Inject
    private CausaEmpaqueService causaService;
    
    @PostConstruct
    public void init() {
        this.nuevo = new MotivoEmpaque();
        this.nuevo.setUsername("usertest"); //testuser
        this.selected = null;
        this.motivos = this.motivoService.obtenerListFlag(1);
        if (this.motivos == null) {
            this.motivos = new ArrayList<>();
        }
    }
    
    public void add(ActionEvent evt) {
        CausaEmpaque causa = this.causaService.findByCodigo(this.nuevo.getCausa());
        this.nuevo.setCausa(causa);
        Boolean exito = this.motivoService.insert(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.init();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
            this.init();
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            CausaEmpaque causa = this.causaService.findByCodigo(this.nuevo.getCausa());
            this.selected.setCausa(causa);
            Boolean exito = this.motivoService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.motivoService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public MotivoEmpaque getNuevo() {
        return nuevo;
    }

    public void setNuevo(MotivoEmpaque nuevo) {
        this.nuevo = nuevo;
    }

    public MotivoEmpaque getSelected() {
        return selected;
    }

    public void setSelected(MotivoEmpaque selected) {
        this.selected = selected;
    }

    public List<MotivoEmpaque> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<MotivoEmpaque> motivos) {
        this.motivos = motivos;
    }
    
}
