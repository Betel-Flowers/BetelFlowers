/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.Carguera;
import com.betel.flowers.model.Ciudad;
import com.betel.flowers.model.CuartoFrioCarguera;
import com.betel.flowers.model.Dae;
import com.betel.flowers.model.Pais;
import com.betel.flowers.model.RegistroVenta;
import com.betel.flowers.model.TerminoExportacion;
import com.betel.flowers.service.CargueraService;
import com.betel.flowers.service.CiudadService;
import com.betel.flowers.service.CuartoFrioCargueraService;
import com.betel.flowers.service.DaeService;
import com.betel.flowers.service.PaisService;
import com.betel.flowers.service.RegistroVentaService;
import com.betel.flowers.service.TerminoExportacionService;
import com.betel.flowers.web.bean.util.CalendarioBean;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "coordinacionBean")
@ViewScoped
public class CoordinacionBean implements Serializable {

    private static final long serialVersionUID = 7497232288272047093L;

    private List<RegistroVenta> registros;
    private List<RegistroVenta> filtered;
    private RegistroVenta selected;
    private Boolean selectedShow;
    private List<Ciudad> origen;
    private List<Ciudad> destino;
    private List<CuartoFrioCarguera> frios;

    @Inject
    private RegistroVentaService ventaService;
    @Inject
    private PaisService paisService;
    @Inject
    private CiudadService ciudadService;
    @Inject
    private DaeService daeService;
    @Inject
    private TerminoExportacionService terminoService;
    @Inject
    private CargueraService carqueraService;
    @Inject
    private CuartoFrioCargueraService frioService;

    @PostConstruct
    public void init() {
        this.registros = this.ventaService.obtenerListCoordinacionFlag(1, Boolean.FALSE);
        Collections.reverse(this.registros);
        this.selectedShow = Boolean.FALSE;
        this.origen = new ArrayList<>();
        this.destino = new ArrayList<>();
    }

    public void onRowSelect(SelectEvent event) {
        RegistroVenta registro = (RegistroVenta) event.getObject();
        if (this.selected != null) {
            this.selected = registro;
            this.selected.getData().setPuertoDestino(registro.getCliente().getCiudad());
            this.selectedShow = Boolean.TRUE;
            if (registro.getAddSubCli()) {
                this.selected.getData().setPuertoDestino(registro.getSubcli().getSubCliente().getCiudad());
            }else{
                this.selected.getSubcli().setSubCliente(null);
            }
            findDaeByPaisDestino();
        }
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }

    public void findDaeByPaisDestino() {
        Pais pais = this.paisService.findByCodigo(this.selected.getData().getPuertoDestino().getPais());
        Dae dae = this.daeService.findByPais(pais);
        this.selected.getData().setDae(dae);
        this.destino = this.ciudadService.obtenerListPais(pais);
        if (this.destino == null) {
            this.destino = new ArrayList<>();
        }
    }

    public void chancePaisOrigen() {
        Pais pais = this.paisService.findByCodigo(this.selected.getData().getPuertoEmbarque().getPais());
        this.origen = this.ciudadService.obtenerListPais(pais);
        if (this.origen == null) {
            this.origen = new ArrayList<>();
        }
    }

    public void findTerminoExportacion() {
        TerminoExportacion termino = this.terminoService.findByCodigo(this.selected.getData().getTermino());
        this.selected.getData().setTermino(termino);
    }

    public void changeAgenciaCarga() {
        Carguera carguera = this.carqueraService.findByCodigo(this.selected.getData().getAgenciaCarga());
        this.selected.getData().setAgenciaCarga(carguera);
        this.frios = this.frioService.obtenerListBodega(carguera.getBodega());
        if (this.frios == null) {
            this.frios = new ArrayList<>();
        }
    }

    public Boolean existSubCli(Boolean exist) {
        if (!exist) {
            exist = Boolean.TRUE;
        } else {
            exist = Boolean.FALSE;
        }
        return exist;
    }

    public void guardarCambios(ActionEvent evt) {
        Ciudad ori = this.ciudadService.findByCodigo(this.selected.getData().getPuertoEmbarque());
        this.selected.getData().setPuertoEmbarque(ori);
        Ciudad des = this.ciudadService.findByCodigo(this.selected.getData().getPuertoDestino());
        this.selected.getData().setPuertoDestino(des);
        Carguera carguera = this.carqueraService.findByCodigo(this.selected.getData().getAgenciaCarga());
        this.selected.getData().setAgenciaCarga(carguera);
        CuartoFrioCarguera frio = this.frioService.findByCodigo(this.selected.getData().getCuartoFrio());
        this.selected.getData().setCuartoFrio(frio);
        this.selected.setCoordinada(Boolean.TRUE);
        this.selected.setEmpacada(Boolean.FALSE);
        this.selected.getData().setUsername("usertest");
        this.selected.getData().setFechaCoordinacion(new Date());
        Boolean exito = this.ventaService.update(this.selected);
        if (exito) {
            this.init();
        }
    }

    public void updateLitsVentas(ActionEvent evt) {
        this.registros = this.ventaService.obtenerListCoordinacionFlag(1, Boolean.FALSE);
        Collections.reverse(this.registros);
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

    public Boolean getSelectedShow() {
        return selectedShow;
    }

    public void setSelectedShow(Boolean selectedShow) {
        this.selectedShow = selectedShow;
    }

    public List<Ciudad> getOrigen() {
        return origen;
    }

    public void setOrigen(List<Ciudad> origen) {
        this.origen = origen;
    }

    public List<Ciudad> getDestino() {
        return destino;
    }

    public void setDestino(List<Ciudad> destino) {
        this.destino = destino;
    }

    public List<CuartoFrioCarguera> getFrios() {
        return frios;
    }

    public void setFrios(List<CuartoFrioCarguera> frios) {
        this.frios = frios;
    }

}
