/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.PointMatrix;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.model.RegistroVenta;
import com.betel.flowers.service.RegistroVentaService;
import com.betel.flowers.web.util.FacesUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "ordenEmpaqueBean")
@ViewScoped
public class OrdenEmpaqueBean implements Serializable {

    private List<RegistroVenta> registros;
    private List<RegistroVenta> filtered;
    private RegistroVenta selected;
    
    private PointMatrix selectedPoint;
    private List<RegistroExportacion> registrosNode;
    private RegistroExportacion registroBodega;
    
    private Boolean showPoint;
    private Boolean selectedShow;

    @Inject
    private RegistroVentaService ventaService;

    @PostConstruct
    public void init() {
        this.registros = this.ventaService.obtenerListEmpaqueFlag(1, Boolean.FALSE);
        Collections.reverse(this.registros);
        this.selectedShow = Boolean.FALSE;
    }

    public void onRowSelect(SelectEvent event) {
        RegistroVenta registro = (RegistroVenta) event.getObject();
        if (this.selected != null) {
            this.selected = registro;
            this.selectedShow = Boolean.TRUE;
            if (!registro.getAddSubCli()) {
                this.selected.getSubcli().setSubCliente(null);
            }
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

    public void updateLitsVentas(ActionEvent evt) {
        this.registros = this.ventaService.obtenerListEmpaqueFlag(1, Boolean.FALSE);
        Collections.reverse(this.registros);
    }

    public void guardarCambios(ActionEvent evt) {
        Boolean exito = Boolean.FALSE;
        for (int i = 0; i < this.selected.getMatrixVenta().size(); i++) {
            if (this.selected.getMatrixVenta().get(i).getEstadoItemEmpaque()) {
                Integer numeroTallosRamo = this.selected.getMatrixVenta().get(i).getNumeroTallosRamo();
                Integer cantidad = this.selected.getMatrixVenta().get(i).getValue();
                Integer numeroRamosCaja = this.selected.getMatrixVenta().get(i).getNumeroRamosCaja();
                Integer calculo = numeroTallosRamo * numeroRamosCaja;
                if (calculo <= cantidad) {
                    this.selected.getMatrixVenta().get(i).setFechaItemEmpaque(new Date());
                    exito = Boolean.TRUE;
                } else {
                    FacesUtil.addMessageWarn(null, "Verifique el numero de ramos por caja.");
                    break;
                }
            } else {
                break;
            }

        }
        if (exito) {
            this.selected.setEmpacada(Boolean.TRUE);
            exito = this.ventaService.update(this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado exitosamente.");
                this.init();
            }
        }
    }

    public List<RegistroVenta> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroVenta> registros) {
        this.registros = registros;
    }

    public List<RegistroVenta> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<RegistroVenta> filtered) {
        this.filtered = filtered;
    }

    public RegistroVenta getSelected() {
        return selected;
    }

    public void setSelected(RegistroVenta selected) {
        this.selected = selected;
    }

    public Boolean getSelectedShow() {
        return selectedShow;
    }

    public void setSelectedShow(Boolean selectedShow) {
        this.selectedShow = selectedShow;
    }

    public RegistroExportacion getRegistroBodega() {
        return registroBodega;
    }

    public void setRegistroBodega(RegistroExportacion registroBodega) {
        this.registroBodega = registroBodega;
    }

    public PointMatrix getSelectedPoint() {
        return selectedPoint;
    }

    public void setSelectedPoint(PointMatrix selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public List<RegistroExportacion> getRegistrosNode() {
        return registrosNode;
    }

    public void setRegistrosNode(List<RegistroExportacion> registrosNode) {
        this.registrosNode = registrosNode;
    }

    public Boolean getShowPoint() {
        return showPoint;
    }

    public void setShowPoint(Boolean showPoint) {
        this.showPoint = showPoint;
    }

}
