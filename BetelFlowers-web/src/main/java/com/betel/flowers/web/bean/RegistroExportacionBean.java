/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.RegistroExportacion;
import com.betel.flowers.model.Variedad;
import com.betel.flowers.service.BodegaVirtualService;
import com.betel.flowers.service.RegistroExportacionService;
import com.betel.flowers.service.RendimientoService;
import com.betel.flowers.service.VariedadService;
import com.betel.flowers.web.bean.util.BarcodeRegistroExportacion;
import com.betel.flowers.web.bean.util.GeneratedPDF;
import com.betel.flowers.web.bean.util.Rendimientos;
import com.betel.flowers.web.util.FacesUtil;
import com.betel.flowers.xml.service.EtiquetaRegExpoXML;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author luis
 */
@Named(value = "registroExportacionBean")
@ViewScoped
public class RegistroExportacionBean implements Serializable {

    private static final long serialVersionUID = -2241393232044677588L;

    private RegistroExportacion nuevo;
    private RegistroExportacion selected;
    private RegistroExportacion remove;
    private List<RegistroExportacion> registrosExportacion;
    private List<RegistroExportacion> registrosExportacionG;
    private List<BarcodeRegistroExportacion> barcodeList;
    private List<BarcodeRegistroExportacion> filteredbarcodeList;
    private Rendimientos rendiminetoServiceList;
    private Boolean gerated;
    private Boolean operarios;

    @Inject
    private RegistroExportacionService registroExportacionService;
    @Inject
    private VariedadService variedadService;
    @Inject
    private BodegaVirtualService bodegaService;
    @Inject
    private EtiquetaRegExpoXML etiquetaRegExpoXML;
    @Inject
    private RendimientoService rendimientoService;

    @PostConstruct
    public void init() {
        this.nuevo = new RegistroExportacion();
        this.nuevo.setUsername("usertest");//usertest
        this.selected = new RegistroExportacion();
        this.gerated = Boolean.TRUE;
        this.operarios = Boolean.TRUE;
        this.registrosExportacion = new ArrayList<>();
        this.barcodeList = new ArrayList<>();
        this.rendiminetoServiceList = new Rendimientos();
        this.registrosExportacionG = this.registroExportacionService.obtenerLista();
        if (this.registrosExportacionG == null) {
            this.registrosExportacionG = new ArrayList<>();
        } else {
            Collections.reverse(this.registrosExportacionG);
            this.loadBarcodeList();
        }
    }

    public void generateContainer(ActionEvent evt) {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad());
        BodegaVirtual bodega = this.bodegaService.findByCodigo(this.nuevo.getBodega());
        this.nuevo.setVariedad(variedad);
        this.nuevo.setBodega(bodega);
        this.nuevo.setCodigo(this.generatedTemCode());
        Boolean exito = this.registrosExportacion.add(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Se ha guardado con exito.");
            this.nuevo = new RegistroExportacion();
            this.nuevo.setUsername("usertest");//usertest
            this.nuevo.setVariedad(new Variedad());
            this.stateOperarios();
        } else {
            FacesUtil.addMessageError(null, "No se ha guardado.");
        }
    }

    private Integer generatedTemCode() {
        Integer number = 0;
        number = new Integer(RandomStringUtils.randomNumeric(4));
        return number;
    }

    public void add(ActionEvent evt) {
        this.generatedBarcode();
        Boolean rendi = this.allInsertsRendimiento();
        if (rendi) {
            Boolean exito = this.allInserts();
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
                this.init();
            }
        }
    }

    private void generatedBarcode() {
        if (this.registrosExportacion != null && !this.registrosExportacion.isEmpty()) {
            int size = this.registrosExportacion.size();
            int length = this.registrosExportacionG.size();
            String code = RandomStringUtils.randomNumeric(2);
            String barcode = "BETEL-RE" + code + "" + size + "" + length;
            String url = "/var/www/html/pdf/" + barcode + "/";
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAdress = request.getLocalAddr();
            String filepath = "http://" + ipAdress + "/pdf/" + barcode + "/" + barcode + ".pdf";
            for (int i = 0; i < size; i++) {
                Integer total = this.registrosExportacion.get(i).getTotalTallos();
                this.registrosExportacion.get(i).setTotalTallos(total);
                this.registrosExportacion.get(i).setBarcode(barcode);
                this.registrosExportacion.get(i).setXml(url + barcode + ".xml");
                this.registrosExportacion.get(i).setHtml(url + barcode + ".html");
                this.registrosExportacion.get(i).setPdf(url + barcode + ".pdf");
                this.registrosExportacion.get(i).setUrlPdf(filepath);
            }
            if (this.rendiminetoServiceList.getRendimientos() != null && !this.rendiminetoServiceList.getRendimientos().isEmpty()) {
                for (int j = 0; j < this.rendiminetoServiceList.getRendimientos().size(); j++) {
                    this.rendiminetoServiceList.getRendimientos().get(j).setBarcode(barcode);
                }
            }
            this.etiquetaRegExpoXML.generatedXML(barcode, url, barcode, this.registrosExportacion);
            GeneratedPDF runPDF = new GeneratedPDF(url, url + barcode + ".xml", url + barcode + ".html", url + barcode + ".pdf", barcode, 0);
            runPDF.run();
            Boolean exito = runPDF.getExito();
            if (exito) {
                FacesUtil.addMessageInfo("Se ha genarado con exito.");
            }
        }
    }

    private Boolean allInserts() {
        Boolean exito = Boolean.FALSE;
        if (this.registrosExportacion != null && !this.registrosExportacion.isEmpty()
                && this.rendiminetoServiceList.getRendimientos() != null && !this.rendiminetoServiceList.getRendimientos().isEmpty()) {
            for (int i = 0; i < this.registrosExportacion.size(); i++) {
                this.registrosExportacion.get(i).setRedimientos(this.rendiminetoServiceList.getRendimientos());
                exito = this.registroExportacionService.insert(this.registrosExportacion.get(i));
                if (!exito) {
                    exito = Boolean.FALSE;
                    break;
                }
            }
        }
        return exito;
    }

    private Boolean allInsertsRendimiento() {
        Boolean exito = Boolean.FALSE;
        if (this.rendiminetoServiceList.getRendimientos() != null && !this.rendiminetoServiceList.getRendimientos().isEmpty()) {
            for (int i = 0; i < this.rendiminetoServiceList.getRendimientos().size(); i++) {
                exito = this.rendimientoService.insert(this.rendiminetoServiceList.getRendimientos().get(i));
                if (!exito) {
                    exito = Boolean.FALSE;
                    break;
                }
            }
        }
        return exito;
    }

    public void enviarOriginalRegister(ActionEvent evt, RegistroExportacion select) {
        this.remove = select;
    }

    public void updateContainer(ActionEvent evt) {
        if (this.selected != null) {
            Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad());
            BodegaVirtual bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setVariedad(variedad);
            this.selected.setBodega(bodega);
            int index = this.registrosExportacion.indexOf(this.selected);
            Boolean exito = this.registrosExportacion.remove(this.remove);
            this.registrosExportacion.add(index, this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.stateGenetated();
                this.selected = new RegistroExportacion();
                this.remove = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void removeContainer(ActionEvent evt, RegistroExportacion select) {
        this.remove = select;
        if (this.remove != null) {
            Boolean exito = this.registrosExportacion.remove(this.remove);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.stateGenetated();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
            }
        } else {
            FacesUtil.addMessageWarn(null, "Seleccione un registro.");
        }
    }

    public void addRendimiento(ActionEvent evt) {
        Boolean exito = this.rendiminetoServiceList.add();
        if (exito) {
            this.stateGenetated();
        }
    }

    private void stateGenetated() {
        if (this.rendiminetoServiceList.getRendimientos() == null || this.rendiminetoServiceList.getRendimientos().isEmpty()) {
            this.gerated = Boolean.TRUE;
        } else {
            this.gerated = Boolean.FALSE;
        }
    }

    private void stateOperarios() {
        if (this.registrosExportacion == null || this.registrosExportacion.isEmpty()) {
            this.operarios = Boolean.TRUE;
        } else {
            this.operarios = Boolean.FALSE;
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null && this.nuevo.getBodega() != null
                && this.nuevo.getVariedad().getCodigo() != null) {
            setSelected(this.nuevo);
            Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad());
            BodegaVirtual bodega = this.bodegaService.findByCodigo(this.selected.getBodega());
            this.selected.setVariedad(variedad);
            this.selected.setBodega(bodega);
            Boolean exito = this.registroExportacionService.update(this.selected);
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

    public void loadVariedad() {
        Variedad variedad = this.variedadService.findByCodigo(this.nuevo.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.nuevo.setVariedad(variedad);
        }
    }

    public void loadVariedadSelected() {
        Variedad variedad = this.variedadService.findByCodigo(this.selected.getVariedad().getCodigo());
        if (variedad.getCodigo() != null) {
            this.selected.setVariedad(variedad);
        }
    }

    private void loadBarcodeList() {
        if (this.registrosExportacionG != null && !this.registrosExportacionG.isEmpty()) {
            List<RegistroExportacion> unique = this.selectBarcode(this.registrosExportacionG);
            for (RegistroExportacion registro : unique) {
                BarcodeRegistroExportacion barcodes = new BarcodeRegistroExportacion();
                barcodes.setCreationDate(registro.getCreationDate());
                barcodes.setBodega(registro.getBodega());
                barcodes.setBarcode(registro.getBarcode());
                barcodes.setUsername(registro.getUsername());
                barcodes.setUrlPdf(registro.getUrlPdf());
                for (int i = 0; i < this.registrosExportacionG.size(); i++) {
                    if (this.registrosExportacionG.get(i).getBarcode().equals(registro.getBarcode())) {
                        barcodes.getListBarcode().add(this.registrosExportacionG.get(i));
                    }
                }
                this.barcodeList.add(barcodes);
            }
        }
    }

    private List<RegistroExportacion> selectBarcode(List<RegistroExportacion> barcode) {

        List<RegistroExportacion> unique = new ArrayList<>();
        if (barcode != null && !barcode.isEmpty()) {
            unique.add(barcode.get(0));
            for (int i = 0; i < barcode.size(); i++) {
                if (!(barcode.get(i).getBarcode().equals(unique.get(unique.size() - 1).getBarcode()))) {
                    unique.add(barcode.get(i));
                }
            }
        }
        return unique;
    }

    public List<RegistroExportacion> listBardodeInsideList(BarcodeRegistroExportacion barcodeItem) {
        List<RegistroExportacion> list = new ArrayList<>();
        if (barcodeItem != null) {
            if (barcodeItem.getListBarcode() != null && !barcodeItem.getListBarcode().isEmpty()) {
                list = barcodeItem.getListBarcode();
            }
        }
        return list;
    }

    public RegistroExportacion getNuevo() {
        return nuevo;
    }

    public void setNuevo(RegistroExportacion nuevo) {
        this.nuevo = nuevo;
    }

    public RegistroExportacion getSelected() {
        return selected;
    }

    public void setSelected(RegistroExportacion selected) {
        this.selected = selected;
    }

    public RegistroExportacion getRemove() {
        return remove;
    }

    public void setRemove(RegistroExportacion remove) {
        this.remove = remove;
    }

    public Boolean getGerated() {
        return gerated;
    }

    public void setGerated(Boolean gerated) {
        this.gerated = gerated;
    }

    public Boolean getOperarios() {
        return operarios;
    }

    public void setOperarios(Boolean operarios) {
        this.operarios = operarios;
    }

    public List<RegistroExportacion> getRegistrosExportacion() {
        return registrosExportacion;
    }

    public void setRegistrosExportacion(List<RegistroExportacion> registrosExportacion) {
        this.registrosExportacion = registrosExportacion;
    }

    public List<RegistroExportacion> getRegistrosExportacionG() {
        return registrosExportacionG;
    }

    public void setRegistrosExportacionG(List<RegistroExportacion> registrosExportacionG) {
        this.registrosExportacionG = registrosExportacionG;
    }

    public List<BarcodeRegistroExportacion> getBarcodeList() {
        return barcodeList;
    }

    public void setBarcodeList(List<BarcodeRegistroExportacion> barcodeList) {
        this.barcodeList = barcodeList;
    }

    public List<BarcodeRegistroExportacion> getFilteredbarcodeList() {
        return filteredbarcodeList;
    }

    public void setFilteredbarcodeList(List<BarcodeRegistroExportacion> filteredbarcodeList) {
        this.filteredbarcodeList = filteredbarcodeList;
    }

    public Rendimientos getRendiminetoServiceList() {
        return rendiminetoServiceList;
    }

    public void setRendiminetoServiceList(Rendimientos rendiminetoServiceList) {
        this.rendiminetoServiceList = rendiminetoServiceList;
    }

}
