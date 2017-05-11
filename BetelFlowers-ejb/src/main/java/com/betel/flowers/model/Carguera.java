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
@Entity(value = "Carguera", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Carguera extends BaseEntity {

    private Integer codigo;
    private String nombre;
    private String ruc;
    private String username;
    private Integer flag;

    @Embedded
    private List<Telefono> telefonos;
    @Embedded
    private List<Correo> correos;
    @Reference
    private BodegaCarguera bodega;
    @Reference
    private Ciudad ciudad;

    public Carguera() {
        this.telefonos = new ArrayList<>();
        this.correos = new ArrayList<>();
        this.bodega = new BodegaCarguera();
        this.ciudad = new Ciudad();
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

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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

    public List<Telefono> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public List<Correo> getCorreos() {
        return correos;
    }

    public void setCorreos(List<Correo> correos) {
        this.correos = correos;
    }

    public BodegaCarguera getBodega() {
        return bodega;
    }

    public void setBodega(BodegaCarguera bodega) {
        this.bodega = bodega;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.codigo);
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
        final Carguera other = (Carguera) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Carguera{" + "codigo=" + codigo + ", nombre=" + nombre + ", ruc=" + ruc + ", username=" + username + ", flag=" + flag + ", telefonos=" + telefonos + ", correos=" + correos + ", bodega=" + bodega + ", ciudad=" + ciudad + '}';
    }
    
}
