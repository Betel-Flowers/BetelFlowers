/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Rendimiento;
import com.betel.flowers.model.TipoTrabajo;
import com.betel.flowers.model.Usuario;
import com.betel.flowers.service.RendimientoService;
import com.betel.flowers.service.TipoTrabajoService;
import com.betel.flowers.service.UsuarioService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author luis
 */
@Named(value = "rendimientoBean")
@ViewScoped
public class RendimientoBean implements Serializable {

    private static final long serialVersionUID = 7585045655790522443L;

    private Rendimiento nuevo;
    private Rendimiento selected;
    private List<Rendimiento> rendimientos;

    @Inject
    private RendimientoService rendimientoService;
    @Inject
    private UsuarioService usuarioService;
    @Inject
    private TipoTrabajoService tipoTrabajoService;

    @PostConstruct
    public void init() {
        this.nuevo = new Rendimiento();
        this.nuevo.setUsername("usertest");
        this.selected = null;
        this.rendimientos = this.rendimientoService.obtenerListFlag(1);
        if (this.rendimientos == null) {
            this.rendimientos = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Usuario user = this.usuarioService.findByCodigo(this.nuevo.getOperario());
        TipoTrabajo tipoTrabajo = this.tipoTrabajoService.findByCodigo(this.nuevo.getTipoTrabajo());
        this.nuevo.setOperario(user);
        this.nuevo.setTipoTrabajo(tipoTrabajo);
        this.nuevo.setBarcode(this.generatedBarcode());
        Boolean exito = this.rendimientoService.insert(this.nuevo);
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
            Usuario user = this.usuarioService.findByCodigo(this.nuevo.getOperario());
            TipoTrabajo tipoTrabajo = this.tipoTrabajoService.findByCodigo(this.nuevo.getTipoTrabajo());
            this.selected.setOperario(user);
            this.selected.setTipoTrabajo(tipoTrabajo);
            Boolean exito = this.rendimientoService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void remove(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.rendimientoService.deteleFlag(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    private String generatedBarcode() {
        int size = 0;
        if (this.rendimientos != null) {
            size = this.rendimientos.size();
        }
        String code = RandomStringUtils.randomNumeric(4);
        String barcode = "BETEL-REM" + code + "" + size;
        return barcode;
    }

    public Rendimiento getNuevo() {
        return nuevo;
    }

    public void setNuevo(Rendimiento nuevo) {
        this.nuevo = nuevo;
    }

    public Rendimiento getSelected() {
        return selected;
    }

    public void setSelected(Rendimiento selected) {
        this.selected = selected;
    }

    public List<Rendimiento> getRendimientos() {
        return rendimientos;
    }

    public void setRendimientos(List<Rendimiento> rendimientos) {
        this.rendimientos = rendimientos;
    }

}
