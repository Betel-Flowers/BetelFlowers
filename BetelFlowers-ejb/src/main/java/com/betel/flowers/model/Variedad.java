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
@Entity(value = "Variedad", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class Variedad extends BaseEntity {

    private String codigo;
    private String nombre;
    private String color;
    private String urlFoto;
    private String codigoFoto;
    private Integer tiempoVida;
    private List<Integer> ramos;
    private List<Integer> longitudes;
    private List<String> glongitudes;
    private List<String> puntosCorte;
    private Boolean girasol;
    private String username;
    private Integer flag;

    @Reference
    private Especie especie;
    @Reference
    private List<Bloque> bloques;
    @Embedded
    private List<ItemPrecio> precios;

    public Variedad() {
        this.urlFoto = "/resources/img/flor.png";
        this.ramos = new ArrayList<>();
        this.longitudes = new ArrayList<>();
        this.glongitudes = new ArrayList<>();
        this.puntosCorte = new ArrayList<>();
        this.especie = new Especie();
        this.precios = new ArrayList<>();
        this.girasol = Boolean.FALSE;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getCodigoFoto() {
        return codigoFoto;
    }

    public void setCodigoFoto(String codigoFoto) {
        this.codigoFoto = codigoFoto;
    }

    public Integer getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(Integer tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public Boolean getGirasol() {
        return girasol;
    }

    public void setGirasol(Boolean girasol) {
        this.girasol = girasol;
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

    public List<Integer> getRamos() {
        return ramos;
    }

    public void setRamos(List<Integer> ramos) {
        this.ramos = ramos;
    }

    public List<Integer> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<Integer> longitudes) {
        this.longitudes = longitudes;
    }

    public List<String> getGlongitudes() {
        return glongitudes;
    }

    public void setGlongitudes(List<String> glongitudes) {
        this.glongitudes = glongitudes;
    }

    public List<String> getPuntosCorte() {
        return puntosCorte;
    }

    public void setPuntosCorte(List<String> puntosCorte) {
        this.puntosCorte = puntosCorte;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public List<ItemPrecio> getPrecios() {
        return precios;
    }

    public void setPrecios(List<ItemPrecio> precios) {
        this.precios = precios;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
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
        final Variedad other = (Variedad) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Variedad{" + "codigo=" + codigo + ", nombre=" + nombre + ", color=" + color + ", urlFoto=" + urlFoto + ", codigoFoto=" + codigoFoto + ", tiempoVida=" + getTiempoVida() + ", ramos=" + ramos + ", longitudes=" + longitudes + ", puntosCorte=" + puntosCorte + ", username=" + username + ", flag=" + flag + ", especie=" + especie + '}';
    }

}
