/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.Objects;
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
    private Integer numeroCajas;
    private Integer numeroRamos;
    private Integer numeroTallosRamo;
    private Integer longitud;
    private String puntoCorte;
    private Integer totalTallos;
    private Double precio;
    private Double subtotal;
    private String barcode;
    private String username;
    private Integer flag;

    @Reference
    private Variedad variedad;
    @Reference
    private TipoCaja caja;

    public StockVenta() {
        this.precio = 0.0;
        this.numeroCajas = 1;
        this.numeroRamos = 1;
        this.numeroTallosRamo = 1;
        this.variedad = new Variedad();
        this.caja = new TipoCaja();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getNumeroCajas() {
        return numeroCajas;
    }

    public void setNumeroCajas(Integer numeroCajas) {
        this.numeroCajas = numeroCajas;
    }

    public Integer getTotalTallos() {
        return getNumeroRamos() * getNumeroTallosRamo() * getNumeroCajas();
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

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public TipoCaja getCaja() {
        return caja;
    }

    public void setCaja(TipoCaja caja) {
        this.caja = caja;
    }

    public Double getSubtotal() {
        return getPrecio() * (double) (getTotalTallos());
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
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
        return "StockVentas{" + "codigo=" + codigo + ", numeroCajas=" + numeroCajas + ", totalTallos=" + totalTallos + ", precio=" + precio + ", variedad=" + variedad + ", caja=" + caja + '}';
    }

    public Integer getNumeroRamos() {
        return numeroRamos;
    }

    public void setNumeroRamos(Integer numeroRamos) {
        this.numeroRamos = numeroRamos;
    }

    public Integer getNumeroTallosRamo() {
        return numeroTallosRamo;
    }

    public void setNumeroTallosRamo(Integer numeroTallosRamo) {
        this.numeroTallosRamo = numeroTallosRamo;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public String getPuntoCorte() {
        return puntoCorte;
    }

    public void setPuntoCorte(String puntoCorte) {
        this.puntoCorte = puntoCorte;
    }
}
