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

/**
 *
 * @author luis
 */
@Entity(value = "Especie", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Especie extends BaseEntity {

    private String codigo;
    private String nombre;
    private String codAduana;
    private String codNandina;
    private String username;
    private Integer flag;

    public Especie() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodAduana() {
        return codAduana;
    }

    public void setCodAduana(String codAduana) {
        this.codAduana = codAduana;
    }

    public String getCodNandina() {
        return codNandina;
    }

    public void setCodNandina(String codNandina) {
        this.codNandina = codNandina;
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
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Especie other = (Especie) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Especie{" + "codigo=" + codigo + ", nombre=" + nombre + ", codAduana=" + codAduana + ", codNandina=" + codNandina + ", username=" + username + ", flag=" + flag + '}';
    }

}
