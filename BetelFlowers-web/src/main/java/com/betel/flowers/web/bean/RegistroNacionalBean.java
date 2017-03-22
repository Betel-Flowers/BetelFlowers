/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.Causa;
import com.betel.flowers.model.DetalleNacional;
import com.betel.flowers.model.Motivo;
import com.betel.flowers.model.RegistroNacional;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.CausaService;
import com.betel.flowers.service.MotivoService;
import com.betel.flowers.service.RegistroNacionalService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.DetallesNacional;
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
@Named(value = "registroNacionalBean")
@ViewScoped
public class RegistroNacionalBean implements Serializable {

    private static final long serialVersionUID = 2110096779145174998L;

    private RegistroNacional nuevo;
    private RegistroNacional selected;
    private List<RegistroNacional> registrosNacional;
    private Causa causaSelected;
    private List<String> listmotivos;
    private List<String> selectedmotivos;
    private DetallesNacional detalle;

    @Inject
    private RegistroNacionalService registroNacionalService;
    @Inject
    private CausaService causaService;
    @Inject
    private VariedadService variedadService;
    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private MotivoService motivoServicce;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroNacional();
        this.nuevo.setUsername("usertest"); //usertest
        this.causaSelected = new Causa();
        this.listmotivos = new ArrayList<>();
        this.selectedmotivos = new ArrayList<>();
        this.detalle = new DetallesNacional();
        this.registrosNacional = this.registroNacionalService.obtenerLista();
        if (this.registrosNacional == null) {
            this.registrosNacional = new ArrayList<>();
        }
    }

    public void add(ActionEvent evt) {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
        BodegaVirtual bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setBodega(bodega);
        this.nuevo.setVariedad(variedad);
        this.nuevo.setDetalle(this.detalle.getDetalles());
        Boolean exito = this.registroNacionalService.insert(this.nuevo);
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
            Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad());
            BodegaVirtual bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setBodega(bodega);
            this.selected.setVariedad(variedad);
            this.selected.setDetalle(this.detalle.getDetalles());
            Boolean exito = this.registroNacionalService.update(this.selected);
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

    public void onSelectionCausa() {
        if (this.causaSelected != null) {
            this.listmotivos = new ArrayList<>();
            Causa mcausa = this.causaService.findByCodigo(this.causaSelected);
            List<Motivo> motivos = this.motivoServicce.obtenerListCausaFlag(mcausa, 1);
            for (Motivo m : motivos) {
                this.listmotivos.add(m.getDescripcion());
            }
        }
    }

    public void onSelectionCausaSelect() {
        if (this.detalle.getNuevo().getCausa() != null) {
            this.listmotivos = new ArrayList<>();
            Causa mcausa = this.causaService.findByCodigo(this.detalle.getNuevo().getCausa());
            List<Motivo> motivos = this.motivoServicce.obtenerListCausaFlag(mcausa, 1);
            for (Motivo m : motivos) {
                this.listmotivos.add(m.getDescripcion());
            }
            this.detalle.getNuevo().setCausa(mcausa);
        }
    }

    public void addCausaMotivos(ActionEvent evt) {
        if (this.causaSelected != null && !this.listmotivos.isEmpty()) {
            DetalleNacional item = new DetalleNacional();
            Causa mcausa = this.causaService.findByCodigo(this.causaSelected);
            item.setCausa(mcausa);
            item.setMotivos(this.selectedmotivos);
            this.detalle.setNuevo(item);
            this.detalle.add(evt);
            this.causaSelected = new Causa();
            this.selectedmotivos = new ArrayList<>();
            this.listmotivos = new ArrayList<>();
        }
    }

    public void sendSelectedClicDetail(ActionEvent evt, RegistroNacional select) {
        this.selected = select;
        this.nuevo = select;
        this.detalle = new DetallesNacional();
        this.detalle.setDetalles(this.selected.getDetalle());
    }

    public Causa getCausaSelected() {
        return causaSelected;
    }

    public void setCausaSelected(Causa causaSelected) {
        this.causaSelected = causaSelected;
    }

    public List<RegistroNacional> getRegistrosNacional() {
        return registrosNacional;
    }

    public void setRegistrosNacional(List<RegistroNacional> registrosNacional) {
        this.registrosNacional = registrosNacional;
    }

    public RegistroNacional getSelected() {
        return selected;
    }

    public void setSelected(RegistroNacional selected) {
        this.selected = selected;
    }

    public RegistroNacional getNuevo() {
        return nuevo;
    }

    public void setNuevo(RegistroNacional nuevo) {
        this.nuevo = nuevo;
    }

    public List<String> getListmotivos() {
        return listmotivos;
    }

    public void setListmotivos(List<String> listmotivos) {
        this.listmotivos = listmotivos;
    }

    public List<String> getSelectedmotivos() {
        return selectedmotivos;
    }

    public void setSelectedmotivos(List<String> selectedmotivos) {
        this.selectedmotivos = selectedmotivos;
    }

    public DetallesNacional getDetalle() {
        return detalle;
    }

    public void setDetalle(DetallesNacional detalle) {
        this.detalle = detalle;
    }

}
