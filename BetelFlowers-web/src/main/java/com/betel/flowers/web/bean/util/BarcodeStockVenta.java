/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.web.bean.util;

import com.betel.flowers.model.StockVenta;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author luis
 */
public class BarcodeStockVenta {
 
    private Date creationDate;
    private String barcode;
    private Integer totalTallosBarcode;
    private Integer totalCajasBarcode;
    private String urlPdf;
    private Double precio;
    private String username;
    private List<StockVenta> listBarcode;

    public BarcodeStockVenta() {
        this.totalTallosBarcode = 0;
        this.precio = 0.0;
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

    public Integer getTotalTallosBarcode() {
        return this.calularTotalTallosBarcode();
    }

    public void setTotalTallosBarcode(Integer totalTallosBarcode) {
        this.totalTallosBarcode = totalTallosBarcode;
    }

    public Integer getTotalCajasBarcode() {
        return this.calularTotalCajasBarcode();
    }

    public void setTotalCajasBarcode(Integer totalCajasBarcode) {
        this.totalCajasBarcode = totalCajasBarcode;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<StockVenta> getListBarcode() {
        return listBarcode;
    }

    public void setListBarcode(List<StockVenta> listBarcode) {
        this.listBarcode = listBarcode;
    }
    
    private Integer calularTotalTallosBarcode(){
        Integer total = 0;
        if(this.listBarcode != null && ! this.listBarcode.isEmpty()){
            for(StockVenta registro : this.listBarcode){
                total = total + registro.getTotalTallos();
            }
        }
        return total;
    }
    
    private Integer calularTotalCajasBarcode(){
        Integer total = 0;
        if(this.listBarcode != null && ! this.listBarcode.isEmpty()){
            for(StockVenta registro : this.listBarcode){
                total = total + registro.getCantidadCajas();
            }
        }
        return total;
    }
}
