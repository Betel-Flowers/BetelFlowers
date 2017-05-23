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
@Entity(value = "DetalleVenta", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class DetalleVenta extends BaseEntity{

    private Integer codigo;
    private Integer cantidadCajas;
    private Integer totalTallos;
    private String barcode;
    private Integer totalTallosCaja;
    private Double subTotalCaja;
    private String username;
    private Integer flag;

    @Reference
    private TipoCaja cajaTipo;
    @Reference
    private MarcaCaja marcaCaja;
    @Embedded
    private List<ItemVariedadVentaEmpaque> detalleCajaVenta;

    public DetalleVenta() {
        this.cajaTipo = new TipoCaja();
        this.marcaCaja = new MarcaCaja();
        this.detalleCajaVenta = new ArrayList<>();
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
        return totalTallos;
    }

    public void setTotalTallos(Integer totalTallos) {
        this.totalTallos = totalTallos;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getTotalTallosCaja() {
        return totalTallosCaja;
    }

    public void setTotalTallosCaja(Integer totalTallosCaja) {
        this.totalTallosCaja = totalTallosCaja;
    }

    public Double getSubTotalCaja() {
        return subTotalCaja;
    }

    public void setSubTotalCaja(Double subTotalCaja) {
        this.subTotalCaja = subTotalCaja;
    }

    public TipoCaja getCajaTipo() {
        return cajaTipo;
    }

    public void setCajaTipo(TipoCaja cajaTipo) {
        this.cajaTipo = cajaTipo;
    }

    public MarcaCaja getMarcaCaja() {
        return marcaCaja;
    }

    public void setMarcaCaja(MarcaCaja marcaCaja) {
        this.marcaCaja = marcaCaja;
    }

    public List<ItemVariedadVentaEmpaque> getDetalleCajaVenta() {
        return detalleCajaVenta;
    }

    public void setDetalleCajaVenta(List<ItemVariedadVentaEmpaque> detalleCajaVenta) {
        this.detalleCajaVenta = detalleCajaVenta;
    }

    public String getUsername() {
        return username;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.codigo);
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
        final DetalleVenta other = (DetalleVenta) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "codigo=" + codigo + ", cantidadCajas=" + cantidadCajas + ", totalTallos=" + totalTallos + ", barcode=" + barcode + ", totalTallosCaja=" + totalTallosCaja + ", subTotalCaja=" + subTotalCaja + ", cajaTipo=" + cajaTipo + ", marcaCaja=" + marcaCaja + ", detalleCajaVenta=" + detalleCajaVenta + '}';
    }
}
