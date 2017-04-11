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
@Entity(value = "TipoUsuario", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class TipoUsuario extends BaseEntity {

    private Integer codigo;
    private String nombre;
    private Boolean admin;
    private String username;
    private Integer flag;

    @Reference
    private List<OpcionSistema> opcionesSistema;

    public TipoUsuario() {
        this.admin = Boolean.FALSE;
        this.opcionesSistema = new ArrayList<>();
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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

    public List<OpcionSistema> getOpcionesSistema() {
        return opcionesSistema;
    }

    public void setOpcionesSistema(List<OpcionSistema> opcionesSistema) {
        this.opcionesSistema = opcionesSistema;
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
        final TipoUsuario other = (TipoUsuario) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoUsuario{" + "codigo=" + codigo + ", nombre=" + nombre + ", admin=" + admin + ", username=" + username + ", flag=" + flag + ", opcionesSistema=" + opcionesSistema + '}';
    }

}
