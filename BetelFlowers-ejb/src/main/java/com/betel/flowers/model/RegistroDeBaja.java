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
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author luis
 */
@Entity(value = "RegistroDeBaja", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroDeBaja extends BaseEntity{

    private Integer codigo;
    private String barcode;
    private String username;
    private Integer flag;
    
    @Reference
    private MotivoEmpaque motivo;
    @Reference
    private List<RegistroExportacion> contenedor;

    public RegistroDeBaja() {
        this.motivo = new MotivoEmpaque();
        this.contenedor = new ArrayList<>();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public MotivoEmpaque getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoEmpaque motivo) {
        this.motivo = motivo;
    }

    public List<RegistroExportacion> getContenedor() {
        return contenedor;
    }

    public void setContenedor(List<RegistroExportacion> contenedor) {
        this.contenedor = contenedor;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
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
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codigo);
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
        final RegistroDeBaja other = (RegistroDeBaja) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroDeBaja{" + "codigo=" + codigo + ", barcode=" + barcode + ", username=" + username + ", flag=" + flag + ", motivo=" + motivo + ", contenedor=" + contenedor + '}';
    }
    
}
