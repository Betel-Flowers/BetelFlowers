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
@Entity(value = "BodegaVirtual", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class BodegaVirtual extends BaseEntity {

    private Integer codigo;
    private String nombre;
    private String username;
    private Integer flag;

    @Reference
    private CuartoFrio cuartoFrio;

    public BodegaVirtual() {
        this.cuartoFrio = new CuartoFrio();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public CuartoFrio getCuartoFrio() {
        return cuartoFrio;
    }

    public void setCuartoFrio(CuartoFrio cuartoFrio) {
        this.cuartoFrio = cuartoFrio;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final BodegaVirtual other = (BodegaVirtual) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BodegaVirtual{" + "codigo=" + codigo + ", nombre=" + nombre + ", username=" + username + ", flag=" + flag + ", cuartoFrio=" + cuartoFrio + '}';
    }

}
