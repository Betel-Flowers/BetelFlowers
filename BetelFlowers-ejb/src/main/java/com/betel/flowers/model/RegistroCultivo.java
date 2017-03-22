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
@Entity(value = "RegistroCultivo", noClassnameStored = true)
@Indexes({
    @Index(fields = @Field("codigo"))})
public class RegistroCultivo extends BaseEntity {

    private String codigo;
    private Boolean malla;
    private Boolean tina;
    private Integer numeroRecipientes;
    private Integer numeroTallosRecipiente;
    private String username;
    private Integer flag;

    @Reference
    private Bloque bloque;
    @Reference
    private Variedad variedad;

    public RegistroCultivo() {
        this.malla = Boolean.FALSE;
        this.tina = Boolean.FALSE;
        this.variedad = new Variedad();
        this.bloque = new Bloque();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getMalla() {
        return malla;
    }

    public void setMalla(Boolean malla) {
        this.malla = malla;
    }

    public Boolean getTina() {
        return tina;
    }

    public void setTina(Boolean tina) {
        this.tina = tina;
    }

    public Integer getNumeroRecipientes() {
        return numeroRecipientes;
    }

    public void setNumeroRecipientes(Integer numeroRecipientes) {
        this.numeroRecipientes = numeroRecipientes;
    }

    public Integer getNumeroTallosRecipiente() {
        return numeroTallosRecipiente;
    }

    public void setNumeroTallosRecipiente(Integer numeroTallosRecipiente) {
        this.numeroTallosRecipiente = numeroTallosRecipiente;
    }

    public Integer getTotalNumeroTallos() {
        return this.numeroRecipientes * this.numeroTallosRecipiente;
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

    public Bloque getBloque() {
        return bloque;
    }

    public void setBloque(Bloque bloque) {
        this.bloque = bloque;
    }

    public Variedad getVariedad() {
        return variedad;
    }

    public void setVariedad(Variedad variedad) {
        this.variedad = variedad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.codigo);
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
        final RegistroCultivo other = (RegistroCultivo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RegistroCultivo{" + "codigo=" + codigo + ", malla=" + malla + ", tina=" + tina + ", numeroRecipientes=" + numeroRecipientes + ", numeroTallosRecipiente=" + numeroTallosRecipiente + ", username=" + username + ", bloque=" + bloque + ", variedad=" + variedad + '}';
    }

}
