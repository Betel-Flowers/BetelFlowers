/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.BodegaVirtual;
import com.betel.flowers.model.RegistroExportacion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luis
 */
public class BarcodeRegistroExportacion {
    
    private Date creationDate;
    private String barcode;
    private BodegaVirtual bodega;
    private Integer totalTallosBarcode;
    private Integer stockBarcode;
    private String urlPdf;
    private String username;
    private List<RegistroExportacion> listBarcode;

    public BarcodeRegistroExportacion() {
        this.totalTallosBarcode  = 0;
        this.bodega = new BodegaVirtual();
        this.listBarcode = new ArrayList<>();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BodegaVirtual getBodega() {
        return bodega;
    }

    public void setBodega(BodegaVirtual bodega) {
        this.bodega = bodega;
    }

    public Integer getTotalTallosBarcode() {
        this.totalTallosBarcode = this.calularTotalTallosBarcode();
        return totalTallosBarcode;
    }

    public void setTotalTallosBarcode(Integer totalTallosBarcode) {
        this.totalTallosBarcode = totalTallosBarcode;
    }

    public Integer getStockBarcode() {
        this.stockBarcode = this.calularStockBarcode();
        return stockBarcode;
    }

    public void setStockBarcode(Integer stockBarcode) {
        this.stockBarcode = stockBarcode;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<RegistroExportacion> getListBarcode() {
        return listBarcode;
    }

    public void setListBarcode(List<RegistroExportacion> listBarcode) {
        this.listBarcode = listBarcode;
    }
    
    private Integer calularTotalTallosBarcode(){
        Integer total = 0;
        if(this.listBarcode != null && ! this.listBarcode.isEmpty()){
            for(RegistroExportacion registro : this.listBarcode){
                total = total + registro.getTotalTallos();
            }
        }
        return total;
    }
    
    private Integer calularStockBarcode(){
        Integer total = 0;
        if(this.listBarcode != null && ! this.listBarcode.isEmpty()){
            for(RegistroExportacion registro : this.listBarcode){
                total = total + registro.getStock();
            }
        }
        return total;
    }
    
}
