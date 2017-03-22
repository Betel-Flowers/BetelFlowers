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
@Entity(value = "Motivo", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Motivo extends BaseEntity{

    private Integer codigo;
    private String descripcion;
    private Integer flag;
    
    @Reference
    private Causa causa;

    public Motivo() {
        this.causa = new Causa();
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

    public Causa getCausa() {
        return causa;
    }

    public void setCausa(Causa causa) {
        this.causa = causa;
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
        hash = 79 * hash + Objects.hashCode(this.codigo);
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
        final Motivo other = (Motivo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Motivo{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", flag=" + flag + ", causa=" + causa + '}';
    }
}
