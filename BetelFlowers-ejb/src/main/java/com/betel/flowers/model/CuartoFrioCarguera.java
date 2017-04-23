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
@Entity(value = "CuartoFrioCarguera", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class CuartoFrioCarguera extends BaseEntity{
    
    private Integer codigo;
    private String descripcion;
    private String username;
    private Integer flag;
    
    @Reference
    private BodegaCarguera bodega;

    public CuartoFrioCarguera() {
        this.bodega = new BodegaCarguera();
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

    public BodegaCarguera getBodega() {
        return bodega;
    }

    public void setBodega(BodegaCarguera bodega) {
        this.bodega = bodega;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final CuartoFrioCarguera other = (CuartoFrioCarguera) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CuartoFrioCarguera{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", username=" + username + ", flag=" + flag + ", bodega=" + bodega + '}';
    }
    
}
