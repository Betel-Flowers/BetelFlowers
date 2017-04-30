/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "StockVentas", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class StockVenta extends BaseEntity {

    private Integer codigo;
    private Integer cantidadCajas;
    private Integer totalTallos;
    private Double precio;
    private String barcode;
    private String message;
    private String urlhtml;
    private String username;
    private Integer flag;

    @Embedded
    private List<ItemCajaStock> detalleCajaStock;
    @Reference
    private TipoCaja caja;

    public StockVenta() {
        this.precio = 0.0;
        this.cantidadCajas = 1;
        this.detalleCajaStock = new ArrayList<>();
        this.caja = new TipoCaja();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidadCajas() {
        return cantidadCajas;
    }

    public void setCantidadCajas(Integer cantidadCajas) {
        this.cantidadCajas = cantidadCajas;
    }

    public Integer getTotalTallos() {
        return this.totalTallos;
    }

    public void setTotalTallos(Integer totalTallos) {
        this.totalTallos = totalTallos;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public List<ItemCajaStock> getDetalleCajaStock() {
        return detalleCajaStock;
    }

    public void setDetalleCajaStock(List<ItemCajaStock> detalleCajaStock) {
        this.detalleCajaStock = detalleCajaStock;
    }

    public TipoCaja getCaja() {
        return caja;
    }

    public void setCaja(TipoCaja caja) {
        this.caja = caja;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlhtml() {
        return urlhtml;
    }

    public void setUrlhtml(String urlhtml) {
        this.urlhtml = urlhtml;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StockVenta other = (StockVenta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockVenta{" + "codigo=" + codigo + ", cantidadCajas=" + cantidadCajas + ", totalTallos=" + totalTallos + ", precio=" + precio + ", barcode=" + barcode + ", message=" + message + ", urlhtml=" + urlhtml + ", username=" + username + ", flag=" + flag + ", detalleCajaStock=" + detalleCajaStock + ", caja=" + caja + '}';
    }
}
