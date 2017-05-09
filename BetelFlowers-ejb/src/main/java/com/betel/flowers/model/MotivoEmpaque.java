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
@Entity(value = "MotivoEmpaque", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class MotivoEmpaque extends BaseEntity{
    
    private Integer codigo;
    private String descripcion;
    private Integer cantidad;
    private String username;
    private Integer flag;

    @Reference
    private CausaEmpaque causa;

    public MotivoEmpaque() {
        this.cantidad = 0;
        this.causa = new CausaEmpaque();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public CausaEmpaque getCausa() {
        return causa;
    }

    public void setCausa(CausaEmpaque causa) {
        this.causa = causa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.codigo);
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
        final MotivoEmpaque other = (MotivoEmpaque) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MotivoEmpaque{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", username=" + username + ", flag=" + flag + ", causa=" + causa + '}';
    }
    
    
}
