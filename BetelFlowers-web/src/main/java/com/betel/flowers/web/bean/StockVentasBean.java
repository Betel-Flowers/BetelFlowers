/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean;

import com.betel.flowers.model.ItemVariedadStock;
import com.betel.flowers.model.StockVenta;
import com.betel.flowers.model.TipoCaja;
import com.betel.flowers.service.StockVentasService;
import com.betel.flowers.service.TipoCajaService;
import com.betel.flowers.web.bean.util.BarcodeStockVenta;
import com.betel.flowers.web.bean.util.DetalleCajaStock;
import com.betel.flowers.web.bean.util.GeneratedPDF;
import com.betel.flowers.web.util.FacesUtil;
import com.betel.flowers.xml.service.MailStockVentaXML;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author luis
 */
@Named(value = "stockVentasBean")
@ViewScoped
public class StockVentasBean implements Serializable {

    private static final long serialVersionUID = -6549694294186350254L;

    private StockVenta nuevo;
    private StockVenta selected;
    private StockVenta remove;
    private List<StockVenta> stockVentas;
    private List<StockVenta> stockVentasG;
    private List<BarcodeStockVenta> barcodeList;
    private Boolean gerated;
    private DetalleCajaStock detalle;
    private String message;

    @Inject
    private StockVentasService stockVentaService;
    @Inject
    private TipoCajaService tipoCajaService;
    @Inject
    private MailStockVentaXML mailStockVentaXML;

    @PostConstruct
    public void init() {
        this.nuevo = new StockVenta();
        this.nuevo.setUsername("usertest");
        this.selected = new StockVenta();
        this.stockVentas = new ArrayList<>();
        this.gerated = Boolean.TRUE;
        this.detalle = new DetalleCajaStock();
        this.barcodeList = new ArrayList<>();
        this.message = "";
        this.stockVentasG = this.stockVentaService.obtenerListFlag(1);
        if (this.stockVentasG == null) {
            this.stockVentasG = new ArrayList<>();
        } else {
            Collections.reverse(this.stockVentasG);
            this.loadBarcodeList();
        }
    }

    public void generateContainer(ActionEvent evt) {
        TipoCaja caja = this.tipoCajaService.findByCodigo(this.nuevo.getCaja());
        this.nuevo.setCaja(caja);
        this.nuevo.setCodigo(this.generatedTempCode());
        this.detalle.add(evt);
        this.nuevo.setDetalleCajaStock(this.detalle.getDetalleCajaStock());
        Boolean exito = this.stockVentas.add(this.nuevo);
        if (exito) {
            FacesUtil.addMessageInfo("Exito.");
            this.nuevo = new StockVenta();
            this.nuevo.setUsername("usertest");//usertest
            this.detalle = new DetalleCajaStock();
            this.stateGenetated();
        } else {
            FacesUtil.addMessageError(null, "Falló.");
        }
    }

    public void generateContainerMix(ActionEvent evt) {
        TipoCaja caja = this.tipoCajaService.findByCodigo(this.nuevo.getCaja());
        this.nuevo.setCaja(caja);
        this.nuevo.setCodigo(this.generatedTempCode());
        if (this.detalle.getDetalleCajaStock() != null && !this.detalle.getDetalleCajaStock().isEmpty()) {
            this.nuevo.setDetalleCajaStock(this.detalle.getDetalleCajaStock());
            Boolean exito = this.stockVentas.add(this.nuevo);
            if (exito) {
                FacesUtil.addMessageInfo("Exito.");
                this.nuevo = new StockVenta();
                this.nuevo.setUsername("usertest");//usertest
                this.detalle = new DetalleCajaStock();
                this.stateGenetated();
            } else {
                FacesUtil.addMessageError(null, "Falló.");
            }
        } else {
            FacesUtil.addMessageInfo("Agregar variedades.");
        }
    }

    public void updateContainer(ActionEvent evt) {
        if (this.selected != null && this.remove != null) {
            TipoCaja caja = this.tipoCajaService.findByCodigo(this.selected.getCaja());
            this.selected.setCaja(caja);
            int index = this.stockVentas.indexOf(this.selected);
            Boolean exito = this.stockVentas.remove(this.remove);
            this.stockVentas.add(index, this.selected);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha modifcado con exito.");
                this.stateGenetated();
                this.selected = new StockVenta();
                this.remove = null;
            } else {
                FacesUtil.addMessageError(null, "No se ha modifcado con exito..");
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    public void removeContainer(ActionEvent evt, StockVenta select) {
        this.remove = select;
        if (this.remove != null) {
            Boolean exito = this.stockVentas.remove(this.remove);
            if (exito) {
                FacesUtil.addMessageInfo("Se ha eliminado con exito.");
                this.stateGenetated();
            } else {
                FacesUtil.addMessageError(null, "No se ha eliminado con exito..");
            }
        } else {
            FacesUtil.addMessageInfo("Seleccione un registro.");
        }
    }

    private Integer generatedTempCode() {
        Integer number = 0;
        number = new Integer(RandomStringUtils.randomNumeric(4));
        return number;
    }

    private void stateGenetated() {
        if (this.stockVentas == null || this.stockVentas.isEmpty()) {
            this.gerated = Boolean.TRUE;
        } else {
            this.gerated = Boolean.FALSE;
        }
    }

    public void enviarOriginalRegister(ActionEvent evt, StockVenta select) {
        this.remove = select;
    }

    private Boolean allInserts() {
        Boolean exito = Boolean.FALSE;
        if (this.stockVentas != null && !this.stockVentas.isEmpty()) {
            for (int i = 0; i < this.stockVentas.size(); i++) {
                exito = this.stockVentaService.insert(this.stockVentas.get(i));
                if (!exito) {
                    exito = Boolean.FALSE;
                    break;
                }
            }
        }
        return exito;
    }

    private void generatedBarcode() {
        if (this.stockVentas != null && !this.stockVentas.isEmpty()) {
            int size = this.stockVentas.size();
            int length = this.stockVentasG.size();
            String code = RandomStringUtils.randomNumeric(2);
            String barcode = "BETEL-SV" + code + "" + size + "" + length;
            String url = "/var/www/html/mail/" + barcode + "/";
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String ipAdress = request.getLocalAddr();
            String filepath = "http://" + ipAdress + "/mail/" + barcode + "/" + barcode + ".pdf";
            for (int i = 0; i < size; i++) {
                Integer total = this.stockVentas.get(i).getTotalTallos();
                this.stockVentas.get(i).setTotalTallos(total);
                this.stockVentas.get(i).setBarcode(barcode);
                this.stockVentas.get(i).setMessage(this.message);
                this.stockVentas.get(i).setXml(url + barcode + ".xml");
                this.stockVentas.get(i).setHtml(url + barcode + ".html");
                this.stockVentas.get(i).setPdf(url + barcode + ".pdf");
                this.stockVentas.get(i).setUrlPdf(filepath);
            }
            this.mailStockVentaXML.generatedXML(barcode, url, barcode, this.message, this.stockVentas);
            GeneratedPDF runPDF = new GeneratedPDF(url, url + barcode + ".xml", url + barcode + ".html", url + barcode + ".pdf", barcode, 1);
            runPDF.run();
            Boolean exito = runPDF.getExito();
            if (exito) {
                FacesUtil.addMessageInfo("Se ha generado con exito.");
            }
        }
    }

    public void add(ActionEvent evt) {
        this.message = this.nuevo.getMessage().trim();
        if (this.message != null && !this.message.equals("")) {
            this.generatedBarcode();
            Boolean exito = this.allInserts();
            if (exito) {
                FacesUtil.addMessageInfo("Se ha guardado con exito.");
                this.init();
            } else {
                FacesUtil.addMessageError(null, "No se ha guardado.");
                this.init();
            }
        } else {
            FacesUtil.addMessageInfo("Por favor ingrese un mensaje para el Stock de Ventas.");
        }
    }

    public void modify(ActionEvent evt) {
        if (this.selected != null) {
            Boolean exito = this.stockVentaService.update(this.selected);
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
            Boolean exito = this.stockVentaService.deteleFlag(this.selected);
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

    public List<ItemVariedadStock> listBardodeInsideList(StockVenta barcodeItem) {
        List<ItemVariedadStock> list = new ArrayList<>();
        if (barcodeItem != null) {
            if (barcodeItem.getDetalleCajaStock() != null && !barcodeItem.getDetalleCajaStock().isEmpty()) {
                list = barcodeItem.getDetalleCajaStock();
            }
        }
        return list;
    }

    private void loadBarcodeList() {
        if (this.stockVentasG != null && !this.stockVentasG.isEmpty()) {
            List<StockVenta> unique = this.selectBarcode(this.stockVentasG);
            for (StockVenta registro : unique) {
                BarcodeStockVenta barcodes = new BarcodeStockVenta();
                barcodes.setCreationDate(registro.getCreationDate());
                barcodes.setBarcode(registro.getBarcode());
                barcodes.setUsername(registro.getUsername());
                barcodes.setUrlHtml(registro.getUrlPdf().replace(".pdf", ".html"));
                for (int i = 0; i < this.stockVentasG.size(); i++) {
                    if (this.stockVentasG.get(i).getBarcode().equals(registro.getBarcode())) {
                        barcodes.getListBarcode().add(this.stockVentasG.get(i));
                    }
                }
                this.barcodeList.add(barcodes);
            }
        }
    }

    private List<StockVenta> selectBarcode(List<StockVenta> barcode) {

        List<StockVenta> unique = new ArrayList<>();
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

    public List<StockVenta> listBardodeInsideListStockVenta(BarcodeStockVenta barcodeItem) {
        List<StockVenta> list = new ArrayList<>();
        if (barcodeItem != null) {
            if (barcodeItem.getListBarcode() != null && !barcodeItem.getListBarcode().isEmpty()) {
                list = barcodeItem.getListBarcode();
            }
        }
        return list;
    }

    public StockVenta getNuevo() {
        return nuevo;
    }

    public void setNuevo(StockVenta nuevo) {
        this.nuevo = nuevo;
    }

    public StockVenta getSelected() {
        return selected;
    }

    public void setSelected(StockVenta selected) {
        this.selected = selected;
    }

    public List<StockVenta> getStockVentas() {
        return stockVentas;
    }

    public void setStockVentas(List<StockVenta> stockVentas) {
        this.stockVentas = stockVentas;
    }

    public List<StockVenta> getStockVentasG() {
        return stockVentasG;
    }

    public void setStockVentasG(List<StockVenta> stockVentasG) {
        this.stockVentasG = stockVentasG;
    }

    public Boolean getGerated() {
        return gerated;
    }

    public void setGerated(Boolean gerated) {
        this.gerated = gerated;
    }

    public StockVenta getRemove() {
        return remove;
    }

    public void setRemove(StockVenta remove) {
        this.remove = remove;
    }

    public DetalleCajaStock getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCajaStock detalle) {
        this.detalle = detalle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BarcodeStockVenta> getBarcodeList() {
        return barcodeList;
    }

    public void setBarcodeList(List<BarcodeStockVenta> barcodeList) {
        this.barcodeList = barcodeList;
    }
}
