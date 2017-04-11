/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.betel.flowers.model;

import com.mongo.persistance.BaseEntity;
import java.util.ArrayList;
import java.util.Date;
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
@Entity(value = "RegistroNacional", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroNacional extends BaseEntity {

    private Integer codigo;
    private Date fechaClasificacion;
    private Integer numeroTallos;
    private String username;
    private Integer flag;

    @Reference
    private BodegaVirtual bodega;
    @Reference
    private Variedad variedad;
    @Embedded
    private List<DetalleNacional> detalle;

    public RegistroNacional() {
        this.variedad = new Variedad();
        this.bodega = new BodegaVirtual();
        this.detalle = new ArrayList<>();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaClasificacion() {
        return fechaClasificacion;
    }

    public void setFechaClasificacion(Date fechaClasificacion) {
        this.fechaClasificacion = fechaClasificacion;
    }

    public Integer getNumeroTallos() {
        return numeroTallos;
    }

    public void setNumeroTallos(Integer numeroTallos) {
        this.numeroTallos = numeroTallos;
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

    public BodegaVirtual getBodega() {
        return bodega;
    }

    public void setBodega(BodegaVirtual bodega) {
        this.bodega = bodega;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    public List<DetalleNacional> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleNacional> detalle) {
        this.detalle = detalle;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.codigo);
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
        final RegistroNacional other = (RegistroNacional) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroNacional{" + "codigo=" + codigo + ", fechaClasificacion=" + fechaClasificacion + ", numeroTallos=" + numeroTallos + ", username=" + username + ", flag=" + flag + ", bodega=" + bodega + ", variedad=" + variedad + ", detalle=" + detalle + '}';
    }

}
